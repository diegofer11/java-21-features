package org.example.excercise1;

import static org.example.utils.OrderUtils.GSON;
import static org.example.utils.OrderUtils.LOG;
import static org.example.utils.OrderUtils.buildOrders;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.example.model.OrderData;

public class AmountByStatus {

	/**
	 * Recibe una lista de objetos Order y obtén un mapa (Map<OrderStatus, Double>) donde la llave sea el estado del
	 * pedido y el valor sea la suma total del costo de todos sus productos (quantity * price).
	 */
	public static void main(String[] args) {
		final List<OrderData.Order> orders = buildOrders(4);

		LOG.info(GSON.toJson(orders));

		final Map<OrderData.OrderStatus, Double> pricePerStatus = orders.stream().collect(Collectors.groupingBy(
				OrderData.Order::status,
				Collectors.summingDouble(order -> order
						.items()
						.stream()
						.mapToDouble(m -> m.price() * m.quantity())
						.sum())));

		LOG.info(GSON.toJson(pricePerStatus));
	}
}
