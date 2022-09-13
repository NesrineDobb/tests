package com.export.be.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.export.be.annotations.BananaQuantityConstraint;
import com.export.be.annotations.DeliveryDateConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "recipient_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Recipient recipient;

	@DeliveryDateConstraint
	@Temporal(TemporalType.DATE)
	private Date dateLivraison;

	@Min(0)
	@Max(10000)
	@BananaQuantityConstraint
	private int quantiteBanane;

	private float prix;

	public Order() {

	}

	public Order(Recipient recipient, Date dateLivraison, int quantiteBanane, float prix) {
		this.recipient = recipient;
		this.dateLivraison = dateLivraison;
		this.quantiteBanane = quantiteBanane;
		this.prix = prix;
	}

	

	public int getId() {
		return id;
	}

	public Recipient getRecipient() {
		return recipient;
	}

	public void setRecipient(Recipient recipient) {
		this.recipient = recipient;
	}

	public Date getDateLivraison() {
		return dateLivraison;
	}

	public void setDateLivraison(Date dateLivraison) {
		this.dateLivraison = dateLivraison;
	}

	public int getQuantiteBanane() {
		return quantiteBanane;
	}

	public void setQuantiteBanane(int quantiteBanane) {
		this.quantiteBanane = quantiteBanane;
	}

	public float getPrix() {
		return (float) (quantiteBanane * 2.50);
	}

	public void setPrix(float prix) {
		this.prix = prix;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", recipient=" + recipient + ", dateLivraison=" + dateLivraison + ", quantiteBanane="
				+ quantiteBanane + ", prix=" + prix + "]";
	}

}
