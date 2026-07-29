package org.example.model;

import java.time.LocalDate;
import java.util.List;

public class OrderData {
	public record Customer(String id, String name, CustomerType type, List<Order> orders) {
	}

	public enum CustomerType {
		REGULAR,
		VIP,
		CORPORATE
	}

	public record Order(String id, LocalDate date, List<OrderItem> items, OrderStatus status) {
	}

	public enum OrderStatus {
		PENDING,
		DELIVERED,
		CANCELLED
	}

	public record OrderItem(String product, int quantity, double price) {
	}

	// Tipos de pago para Pattern Matching
	public sealed interface PaymentMethod permits CreditCard, Crypto, BankTransfer {
	}

	public record CreditCard(String cardNumber, String holder) implements PaymentMethod {
	}

	public record Crypto(String walletAddress, String coin) implements PaymentMethod {
	}

	public record BankTransfer(String iban) implements PaymentMethod {
	}
}
