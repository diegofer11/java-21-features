package dev.playground.streams.collectors;

import static dev.playground.utils.OrderUtils.GSON;
import static dev.playground.utils.OrderUtils.buildOrders;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dev.playground.model.OrderData;
import dev.playground.utils.LoggerUtils;

public class AmountByStatus {

	/**
	 * Recibe una lista de objetos Order y obtén un mapa (Map<OrderStatus, Double>) donde la llave sea el estado del
	 * pedido y el valor sea la suma total del costo de todos sus productos (quantity * price).
	 */
	public static void main(String[] args) {
		final List<OrderData.Order> orders = buildOrders(4);

		LoggerUtils.info("AmountByStatus", "main", GSON.toJson(orders));

		final Map<OrderData.OrderStatus, Double> pricePerStatus = orders.stream().collect(Collectors.groupingBy(
				OrderData.Order::status,
				Collectors.summingDouble(order -> order
						.items()
						.stream()
						.mapToDouble(m -> m.price() * m.quantity())
						.sum())));

		LoggerUtils.info("AmountByStatus", "main", GSON.toJson(pricePerStatus));
	}
}
