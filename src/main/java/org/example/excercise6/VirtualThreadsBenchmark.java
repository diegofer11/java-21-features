package org.example.excercise6;

import static org.example.utils.OrderUtils.LOG;
import static org.example.utils.ThreadUtils.generateTasks;
import static org.example.utils.ThreadUtils.processTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.example.model.ThreadData;

import com.google.common.base.Stopwatch;

public class VirtualThreadsBenchmark {

	/**
	 * Implementa un programa que simule la ejecución masiva de procesamiento de datos en paralelo para comparar el
	 * rendimiento de los Hilos Virtuales de Java 21 frente a un pool de hilos tradicional:
	 * <br>
	 * <ul><li>Define una meta de 10,000 tareas. Cada tarea debe simular una latencia o trabajo bloqueante de I/O mediante un Thread.sleep(10) sintético.</li>
	 * <li>Ejecuta las 10,000 tareas utilizando la nueva API Executors.newVirtualThreadPerTaskExecutor() dentro de un bloque try-with-resources.</li>
	 * <li>Ejecuta las mismas 10,000 tareas utilizando un Executors.newFixedThreadPool(10) tradicional.</li>
	 * <li>Mide el tiempo de inicio y fin de cada bloque y muestra por consola la comparativa en milisegundos.</li>
	 * </ul>
	 *
	 * @param args arguments
	 */
	public static void main(String[] args) {
		List<ThreadData.TaskData> tasks = generateTasks(10_000);
		List<Future<ThreadData.ProcessResult>> futures = new ArrayList<>();

		Stopwatch watch = Stopwatch.createStarted();

		try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
			for (ThreadData.TaskData task : tasks) {
				futures.add(executor.submit(() -> processTask(task)));
			}
		}

		watch.stop();
		LOG.info("Time using virtual threads: " + watch); //  Time: 218.3 ms

		List<ThreadData.ProcessResult> results = futures.stream().map(Future::resultNow).toList();
		LOG.info("Total de tareas ejecutadas: " + results.size());
		LOG.info("Primer elemento: " + results.getFirst());
		LOG.info("Último elemento: " + results.getLast());

		watch.reset();
		watch.start();

		try (var executor = Executors.newFixedThreadPool(10)) {
			for (ThreadData.TaskData task : tasks) {
				executor.submit(() -> processTask(task));
			}
		}

		watch.stop();
		LOG.info("Time using fixed thread pool using 10 threads: " + watch); //  Time: 15.65 s
	}
}
