package dev.playground.streams.collectors;

import static dev.playground.utils.OrderUtils.buildSampleCustomers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dev.playground.model.OrderData;
import dev.playground.utils.LoggerUtils;

public class CustomerSegmentation {

	/**
	 * A partir de la lista de clientes, genera un mapa de tipo: Map<CustomerType, List<Customer Map<Boolean,>>> Regla:
	 * La clave externa es el tipo de cliente (REGULAR, VIP, etc.). El mapa interno separa en dos grupos (true / false):
	 * true: Clientes que tienen al menos una orden en estado CANCELLED. false: Clientes que no tienen ninguna orden
	 * cancelada. Pista: Tendrás que combinar Collectors.groupingBy con Collectors.partitioningBy. Para saber si un
	 * cliente tiene órdenes canceladas, te servirá el método anyMatch(...) dentro del predicado.
	 *
	 * @param args
	 * 		arguments
	 */
	public static void main(String[] args) {
		List<OrderData.Customer> customers = buildSampleCustomers();

		Map<OrderData.CustomerType, Map<Boolean, List<OrderData.Customer>>> result = customers
				.stream()
				.collect(Collectors.groupingBy(
						OrderData.Customer::type,
						Collectors.partitioningBy(c -> c
								.orders()
								.stream()
								.anyMatch(o -> o.status() == OrderData.OrderStatus.CANCELLED))));

		LoggerUtils.info("CustomerSegmentation", "main", "Resultado:::" + result);
	}
}
