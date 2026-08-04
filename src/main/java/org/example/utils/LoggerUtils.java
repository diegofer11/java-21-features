package org.example.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerUtils {
	public static final Logger LOG = Logger.getLogger("global");

	private LoggerUtils() {
	}

	public static void warning(final String className, final String methodName, final String message) {
		LOG.logp(Level.WARNING, className, methodName, message);
	}

	public static void severe(final String className, final String methodName, final String message) {
		LOG.logp(Level.SEVERE, className, methodName, message);
	}

	public static void info(final String className, final String methodName, final String message) {
		LOG.logp(Level.INFO, className, methodName, message);
	}
}
