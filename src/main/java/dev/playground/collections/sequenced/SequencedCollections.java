package dev.playground.collections.sequenced;

import static dev.playground.utils.OrderUtils.buildSampleCustomers;

import java.util.List;
import java.util.SequencedCollection;
import java.util.stream.Collectors;

import dev.playground.model.OrderData;
import dev.playground.utils.LoggerUtils;

public class SequencedCollections {

	/**
	 * Java 21 introdujo las interfaces SequencedCollection, SequencedSet y SequencedMap para resolver la inconsistencia
	 * histórica que había al acceder al primer y último elemento de diferentes colecciones (List, Deque, LinkedHashSet,
	 * etc.).
	 *
	 * @param args
	 * 		arguments
	 */
	public static void main(String[] args) {
		List<OrderData.Customer> customers = buildSampleCustomers();

		SequencedCollection<String> nombres = customers
				.stream()
				.map(OrderData.Customer::name)
				.collect(Collectors.toList());

		LoggerUtils.info(
				"SequencedCollections",
				"main",
				"Resultado:::" + "Colección conservando el orden:::" + nombres);
		LoggerUtils.info("SequencedCollections", "main", "primer cliente:::" + nombres.getFirst());
		LoggerUtils.info("SequencedCollections", "main", "último cliente:::" + nombres.getLast());
		LoggerUtils.info("SequencedCollections", "main", "lista invertida:::" + nombres.reversed());

		nombres.addFirst("Diego");
		nombres.addLast("Valentina");

		LoggerUtils.info("SequencedCollections", "main", "Colección conservando el orden:::" + nombres);
	}
}
