package org.example.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.example.model.OrderData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OrderUtils {
	public static final Gson GSON = new GsonBuilder().registerTypeAdapter(
			LocalDate.class,
			(com.google.gson.JsonSerializer<LocalDate>) (src, typeOfSrc, context) -> new com.google.gson.JsonPrimitive(
					src.format(DateTimeFormatter.ISO_LOCAL_DATE))).registerTypeAdapter(
			LocalDate.class,
			(com.google.gson.JsonDeserializer<LocalDate>) (json, typeOfT, context) -> LocalDate.parse(
					json.getAsString(),
					DateTimeFormatter.ISO_LOCAL_DATE)).create();

	private static final Random RANDOM = new Random();

	public static List<OrderData.Order> buildOrders(final int ordersToCreate) {
		List<OrderData.Order> orderList = new ArrayList<>();
		for (int i = 1; i <= ordersToCreate; i++) {
			List<OrderData.OrderItem> items = new ArrayList<>();
			OrderData.OrderItem orderItem = new OrderData.OrderItem("Producto" + i, 5 + i, 1500 * i);
			items.add(orderItem);

			OrderData.Order order = new OrderData.Order(String.valueOf(i), LocalDate.now(), items, getOrderStatus());
			orderList.add(order);
		}
		return orderList;
	}

	public static OrderData.OrderStatus getOrderStatus() {
		OrderData.OrderStatus[] orderStatuses = OrderData.OrderStatus.values();
		return orderStatuses[RANDOM.nextInt(orderStatuses.length)];
	}

	public static List<OrderData.Customer> buildCustomersForExercise3() {
		// Items de prueba
		OrderData.OrderItem laptop = new OrderData.OrderItem("Laptop", 1, 1200.0);
		OrderData.OrderItem mouse = new OrderData.OrderItem("Mouse", 2, 25.0);
		OrderData.OrderItem monitor = new OrderData.OrderItem("Monitor", 1, 300.0);

		// Órdenes con diferentes estados
		OrderData.Order orderDelivered1 = new OrderData.Order(
				"O1",
				LocalDate.now(),
				List.of(laptop),
				OrderData.OrderStatus.DELIVERED);
		OrderData.Order orderDelivered2 = new OrderData.Order(
				"O2",
				LocalDate.now(),
				List.of(mouse),
				OrderData.OrderStatus.DELIVERED);
		OrderData.Order orderCancelled = new OrderData.Order(
				"O3",
				LocalDate.now(),
				List.of(monitor),
				OrderData.OrderStatus.CANCELLED);
		OrderData.Order orderPending = new OrderData.Order("O4", LocalDate.now(), List.of(mouse), OrderData.OrderStatus.PENDING);

		// 1. VIP con al menos una orden CANCELLED
		OrderData.Customer vipConCancelacion = new OrderData.Customer(
				"C1",
				"Ana (VIP con cancelada)",
				OrderData.CustomerType.VIP,
				List.of(orderDelivered1, orderCancelled));

		// 2. VIP sin ordenes CANCELLED
		OrderData.Customer vipSinCancelacion = new OrderData.Customer(
				"C2",
				"Carlos (VIP limpia)",
				OrderData.CustomerType.VIP,
				List.of(orderDelivered2, orderPending));

		// 3. REGULAR con orden CANCELLED
		OrderData.Customer regularConCancelacion = new OrderData.Customer(
				"C3",
				"Beatriz (REGULAR con cancelada)",
				OrderData.CustomerType.REGULAR,
				List.of(orderCancelled));

		// 4. REGULAR sin ordenes CANCELLED
		OrderData.Customer regularSinCancelacion = new OrderData.Customer(
				"C4",
				"David (REGULAR limpia)",
				OrderData.CustomerType.REGULAR,
				List.of(orderDelivered1));

		// 5. CORPORATE sin ordenes CANCELLED
		OrderData.Customer corporateLimpio = new OrderData.Customer(
				"C5",
				"Acme Corp (CORPORATE limpia)",
				OrderData.CustomerType.CORPORATE,
				List.of(orderDelivered2));

		return List.of(
				vipConCancelacion,
				vipSinCancelacion,
				regularConCancelacion,
				regularSinCancelacion,
				corporateLimpio);
	}
}
