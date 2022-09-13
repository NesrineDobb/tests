package com.export.be.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.export.be.exception.ResourceNotFoundException;
import com.export.be.model.Order;
import com.export.be.repository.OrderRepository;
import com.export.be.repository.RecipientRepository;

@RestController
public class OrderController {

	
	private OrderRepository orderRepository;
	private RecipientRepository recipientRepository;
	
	
    @Autowired
	public OrderController(OrderRepository orderRepository, RecipientRepository recipientRepository) {
		this.orderRepository = orderRepository;
		this.recipientRepository = recipientRepository;
	}

	@GetMapping("/recipients/{recipientId}/orders")
	public Page<Order> getAllOrdersByrecipientId(@PathVariable(value = "recipientId") int recipientId,
			Pageable pageable) {
		return orderRepository.findByRecipientId(recipientId, pageable);
	}

	@PostMapping("/recipients/{recipientId}/orders")
	public Order createOrder(@PathVariable(value = "recipientId") int recipientId, @Valid @RequestBody Order order) {
		return recipientRepository.findById(recipientId).map(recipient -> {
			order.setRecipient(recipient);
			return orderRepository.save(order);
		}).orElseThrow(() -> new ResourceNotFoundException("RecipientId " + recipientId + " not found"));
	}

	@PutMapping("/recipients/{recipientId}/orders/{orderId}")
	public Order updateOrder(@PathVariable(value = "recipientId") int recipientId,
			@PathVariable(value = "orderId") int orderId, @Valid @RequestBody Order orderRequest) {
		if (!recipientRepository.existsById(recipientId)) {
			throw new ResourceNotFoundException("RecipientId " + recipientId + " not found");
		}

		return orderRepository.findById(orderId).map(order -> {
			order.setDateLivraison(orderRequest.getDateLivraison());
			order.setQuantiteBanane(orderRequest.getQuantiteBanane());
			order.setPrix(orderRequest.getPrix());
			return orderRepository.save(order);
		}).orElseThrow(() -> new ResourceNotFoundException("OrderId " + orderId + "not found"));
	}

	@DeleteMapping("/recipients/{recipientId}/orders/{orderId}")
	public ResponseEntity<?> deleteOrder(@PathVariable(value = "recipientId") int recipientId,
			@PathVariable(value = "orderId") int orderId) {
		return orderRepository.findByIdAndRecipientId(orderId, recipientId).map(order -> {
			orderRepository.delete(order);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException(
				"Order not found with id " + orderId + " and RecipientId " + recipientId));
	}
}
