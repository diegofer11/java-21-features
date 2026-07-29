package org.example.excercise3;

import static org.example.utils.OrderUtils.GSON;
import static org.example.utils.OrderUtils.LOG;
import static org.example.utils.OrderUtils.buildCustomersForExercise3;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.example.model.OrderData;

public class CustomerSegmentation {

	/**
	 * A partir de la lista de clientes, genera un mapa de tipo: Map<CustomerType, List<Customer Map<Boolean,>>> Regla:
	 * La clave externa es el tipo de cliente (REGULAR, VIP, etc.). El mapa interno separa en dos grupos (true / false):
	 * true: Clientes que tienen al menos una orden en estado CANCELLED. false: Clientes que no tienen ninguna orden
	 * cancelada. Pista: Tendrás que combinar Collectors.groupingBy con Collectors.partitioningBy. Para saber si un
	 * cliente tiene órdenes canceladas, te servirá el método anyMatch(...) dentro del predicado.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		List<OrderData.Customer> customers = buildCustomersForExercise3();

		Map<OrderData.CustomerType, Map<Boolean, List<OrderData.Customer>>> result = customers
				.stream()
				.collect(Collectors.groupingBy(
						OrderData.Customer::type,
						Collectors.partitioningBy(c -> c
								.orders()
								.stream()
								.anyMatch(o -> o.status() == OrderData.OrderStatus.CANCELLED))));

		LOG.info(GSON.toJson("Resultado:::" + result));

	}
}
