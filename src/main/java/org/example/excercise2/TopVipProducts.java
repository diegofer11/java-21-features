package org.example.excercise2;

import static org.example.utils.OrderUtils.GSON;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.example.model.OrderData;
import org.example.utils.LoggerUtils;

public class TopVipProducts {

	/**
	 * Dada una lista de objetos Customer: Filtra únicamente a los clientes de tipo VIP. De sus órdenes, toma solo las
	 * que estén en estado DELIVERED. Extrae los productos e incrementa sus cantidades para obtener los 3 productos más
	 * vendidos en total (por cantidad acumulada). Devuelve el resultado como un List<String> con los nombres de los
	 * productos o un Map<String, Integer> con el nombre y la cantidad total.
	 */
	public static void main(String[] args) {

		final List<OrderData.Customer> customers = buildCustomers();

		final Map<String, Integer> result = customers
				.stream()
				.filter(s -> s.type() == OrderData.CustomerType.VIP)
				.flatMap(o -> o.orders().stream())
				.filter(f -> f.status() == OrderData.OrderStatus.DELIVERED)
				.flatMap(oi -> oi.items().stream())
				.collect(Collectors.groupingBy(
						OrderData.OrderItem::product,
						Collectors.summingInt(OrderData.OrderItem::quantity)));

		LoggerUtils.info("TopVipProducts", "main", GSON.toJson(result));

		final List<String> masVendidos = result
				.entrySet()
				.stream()
				.sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
				.map(Map.Entry::getKey)
				.limit(3)
				.toList();

		LoggerUtils.info("TopVipProducts", "main", "top 3 productos más comprados por clientes VIP: " + masVendidos);
	}

	private static List<OrderData.Customer> buildCustomers() {
		OrderData.OrderItem laptop = new OrderData.OrderItem("Laptop", 1, 1200.0);
		OrderData.OrderItem mouse = new OrderData.OrderItem("Mouse", 3, 25.0);
		OrderData.OrderItem teclado = new OrderData.OrderItem("Teclado", 2, 45.0);
		OrderData.OrderItem monitor = new OrderData.OrderItem("Monitor", 2, 300.0);
		OrderData.OrderItem usb = new OrderData.OrderItem("Memoria USB", 10, 10.0);

		OrderData.Order order1 = new OrderData.Order(
				"O1",
				LocalDate.now(),
				List.of(laptop, mouse),
				OrderData.OrderStatus.DELIVERED);
		OrderData.Order order2 = new OrderData.Order(
				"O2",
				LocalDate.now(),
				List.of(teclado, mouse, usb),
				OrderData.OrderStatus.DELIVERED);
		OrderData.Order order3 = new OrderData.Order(
				"O3",
				LocalDate.now(),
				List.of(monitor, usb),
				OrderData.OrderStatus.CANCELLED); // No debe contar (CANCELLED)
		OrderData.Order order4 = new OrderData.Order(
				"O4",
				LocalDate.now(),
				List.of(monitor, laptop),
				OrderData.OrderStatus.DELIVERED);

		OrderData.Customer vip1 = new OrderData.Customer(
				"C1",
				"Ana",
				OrderData.CustomerType.VIP,
				List.of(order1, order2));
		OrderData.Customer vip2 = new OrderData.Customer("C2", "Carlos", OrderData.CustomerType.VIP, List.of(order4));
		OrderData.Customer regular = new OrderData.Customer(
				"C3",
				"Beatriz",
				OrderData.CustomerType.REGULAR,
				List.of(order3, order1)); // No debe contar (REGULAR)

		return List.of(vip1, vip2, regular);
	}
}
