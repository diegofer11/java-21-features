package org.example.utils;

import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

public class LoggerUtils {
	private LoggerUtils() {
	}

	public static void warning(final String className, final String methodName, final String message) {
		getLogBuilder(Level.WARN, className, methodName, message);
	}

	public static void severe(final String className, final String methodName, final String message) {
		getLogBuilder(Level.ERROR, className, methodName, message);
	}

	public static void info(final String className, final String methodName, final String message) {
		getLogBuilder(Level.INFO, className, methodName, message);
	}

	private static void getLogBuilder(
			final Level level,
			final String className,
			final String methodName,
			final String message) {
		final String formattedMessage = String.format("[%s] %s", methodName, message);
		LoggerFactory.getLogger(className).atLevel(level).setMessage(formattedMessage).log();
	}
}
