package org.example.exercise9;

import org.example.utils.LoggerUtils;

import java.util.TreeSet;

public class TreeSetExample {

    public static void main(String[] args) {
        final TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("a");
        treeSet.add("b");
        treeSet.add("c");
        treeSet.add("d");
        treeSet.add("e");
        treeSet.add("f");
        treeSet.add("g");
        treeSet.add("h");
        treeSet.add("a1");

        LoggerUtils.info("TreeSetExample", "main", "=== TREE-SET Example ===");
        treeSet.forEach(System.out::println);

        LoggerUtils.info("TreeSetExample", "main", "=== ceiling, floor, higher, lower ===");
        LoggerUtils.info("Ceiling", "main", "ceiling " + treeSet.ceiling("b"));
        LoggerUtils.info("Floor", "main", "floor " + treeSet.floor("f"));
        LoggerUtils.info("Higher", "main", "higher " + treeSet.higher("g"));
        LoggerUtils.info("Lower", "main", "lower " + treeSet.lower("a1"));
        LoggerUtils.info("Get first", "main", "get first " + treeSet.getFirst());
        LoggerUtils.info("Get last", "main", "get last " + treeSet.getLast());
    }
}
