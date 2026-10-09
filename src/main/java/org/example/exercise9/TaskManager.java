package org.example.exercise9;

import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;

import org.example.utils.LoggerUtils;

public class TaskManager {

	public static void main(String[] args) {
		TreeSet<Task> tasks = new TreeSet<>(Comparator
				.comparingInt(Task::priority)
				.thenComparing(Task::name)
				.reversed());
		tasks.add(new Task("backup", 2));
		tasks.add(new Task("deploy", 5));
		tasks.add(new Task("email", 1));
		tasks.add(new Task("deploy", 5));

		while (!tasks.isEmpty()) {
			LoggerUtils.info("TaskManager", "main", "elemento leído: " + tasks.pollFirst());
		}

		Task remainingTask = tasks.pollFirst();
		assert remainingTask == null;

		TreeSet<Integer> set = new TreeSet<>();
		set.add(1);
		set.add(2);

		while (!set.isEmpty()) {
			int item = set.pollFirst();
			LoggerUtils.info("TaskManager", "main", "elemento leído: " + item);
		}

		TreeMap<Integer, String> map = new TreeMap<>();
		map.put(1, "uno");
		map.put(2, "dos");
		map.put(3, "tres");

		int iteracion = 1;
		while (!map.isEmpty()) {
			LoggerUtils.info("TaskManager", "main", "iteración: " + iteracion);
			LoggerUtils.info("TaskManager", "main", "usando pollFirstEntry: " + map.pollFirstEntry());
			LoggerUtils.info("TaskManager", "main", "usando pollLastEntry: " + map.pollLastEntry());
			iteracion++;
		}

		LoggerUtils.info("TaskManager", "main", "treeMap" + map);
		assert map.pollFirstEntry() == null;
	}

	private record Task(String name, int priority) {
	}
}
