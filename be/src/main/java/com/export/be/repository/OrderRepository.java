package com.export.be.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.export.be.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	Page<Order> findByRecipientId(Integer recipientId, Pageable pageable);

	Optional<Order> findByIdAndRecipientId(Integer id, Integer recipientId);

}
