package org.example.excercise7;

import static org.example.utils.OrderUtils.LOG;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import org.example.model.HedgedRequestData;

public class HedgedRequests {

	/**
	 * Imagina un sistema financiero que consulta la cotización de Bitcoin en tres réplicas o servidores distintos para
	 * obtener la respuesta lo más rápido posible:
	 * <br>Servidor A (Latencia normal, pero a veces se cuelga).
	 * <br>Servidor B (Suele responder rápido).
	 * <br>Servidor C (A veces falla con excepción).
	 * <br>El objetivo es lanzar la consulta a los 3 servidores en paralelo usando Virtual Threads y tomar únicamente
	 * el primer resultado exitoso, cancelando de forma automática las otras dos peticiones pendientes para no malgastar
	 * recursos.
	 *
	 * @param args
	 * 		arguments
	 */
	public static void main(String[] args) {
		List<HedgedRequestData.ServerNode> nodes = HedgedRequestData.getSimulatedNodes();

		List<Callable<HedgedRequestData.QuoteResult>> tasks = nodes
				.stream()
				.map(node -> (Callable<HedgedRequestData.QuoteResult>) () -> fetchPrice(node))
				.toList();

		try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
			LOG.info("🚀 Disparando peticiones concurrentes a los nodos..." + nodes.size());

			HedgedRequestData.QuoteResult fastestResponse = executor.invokeAny(tasks);

			LOG.info("Ganador: " + fastestResponse);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			LOG.log(Level.SEVERE, "El hilo principal fue interrumpido", e);
		} catch (ExecutionException e) {
			LOG.log(Level.SEVERE, "Todos los nodos fallaron al responder", e);
		}
	}

	private static HedgedRequestData.QuoteResult fetchPrice(HedgedRequestData.ServerNode node) throws InterruptedException {
		LOG.info("Iniciando consulta en: " + node.name());

		try {
			if (node.shouldFail()) {
				throw new RuntimeException("Error de conexión en el servidor " + node.name());
			}

			Thread.sleep(node.latencyMs());

			double simulatedPrice = 65_000.0 + (Math.random() * 500);
			return new HedgedRequestData.QuoteResult(node.name(), simulatedPrice, node.latencyMs());

		} catch (InterruptedException e) {
			LOG.log(Level.WARNING, "🛑 Petición cancelada/interrumpida en: " + node.name());
			throw e;
		}
	}
}
