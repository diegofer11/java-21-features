package org.example.excercise4;

import static org.example.utils.OrderUtils.buildCustomersForExercise3;

import java.util.List;
import java.util.SequencedCollection;
import java.util.stream.Collectors;

import org.example.model.OrderData;
import org.example.utils.LoggerUtils;

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
		List<OrderData.Customer> customers = buildCustomersForExercise3();

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
