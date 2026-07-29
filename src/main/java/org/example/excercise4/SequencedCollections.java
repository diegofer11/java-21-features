package org.example.excercise4;

import static org.example.utils.OrderUtils.LOG;
import static org.example.utils.OrderUtils.buildCustomersForExercise3;

import java.util.List;
import java.util.SequencedCollection;
import java.util.stream.Collectors;

import org.example.model.OrderData;

public class SequencedCollections {

	/**
	 * Java 21 introdujo las interfaces SequencedCollection, SequencedSet y SequencedMap para resolver la inconsistencia
	 * histórica que había al acceder al primer y último elemento de diferentes colecciones (List, Deque, LinkedHashSet,
	 * etc.).
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		List<OrderData.Customer> customers = buildCustomersForExercise3();

		SequencedCollection<String> nombres = customers
				.stream()
				.map(OrderData.Customer::name)
				.collect(Collectors.toList());

		LOG.info("Colección conservando el orden:::" + nombres);

		LOG.info("primer cliente:::" + nombres.getFirst());
		LOG.info("último cliente:::" + nombres.getLast());

		LOG.info("lista invertida:::" + nombres.reversed());

		nombres.addFirst("Diego");
		nombres.addLast("Valentina");

		LOG.info("Colección conservando el orden:::" + nombres);
	}
}
