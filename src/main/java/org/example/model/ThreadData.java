package org.example.model;

public class ThreadData {
	// Datos de entrada de la tarea
	public record TaskData(int id, String payload, long duration) {
	}

	// Resultado tras procesar la tarea
	public record ProcessResult(int taskId, boolean success, String summary) {
	}
}
