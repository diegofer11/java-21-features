package org.example.utils;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.example.model.ThreadData;

public class ThreadUtils {
	public static ThreadData.ProcessResult processTask(ThreadData.TaskData task) {
		try {
			// Simula la latencia de red / base de datos / I/O
			Thread.sleep(task.duration());

			// Simula una pequeña lógica de negocio
			String summary = "Processed [%s] for task #%d".formatted(task.payload(), task.id());
			return new ThreadData.ProcessResult(task.id(), true, summary);

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return new ThreadData.ProcessResult(task.id(), false, "Interrupted");
		}
	}

	public static List<ThreadData.TaskData> generateTasks(int count) {
		var random = new Random();
		var services = List.of("UserService", "PaymentGateway", "InventoryAPI", "NotificationService");

		return IntStream
				.range(1, count + 1)
				.mapToObj(i -> new ThreadData.TaskData(
						i,
						"Payload from " + services.get(random.nextInt(services.size())),
						10))
				.toList();
	}
}
