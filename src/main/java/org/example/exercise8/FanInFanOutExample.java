package org.example.exercise8;

import java.util.concurrent.*;
import java.util.*;

public class FanInFanOutExample {
    
    // FAN-OUT: Distribuir trabajo a múltiples hilos
    static class FanOutExample {
        private final ExecutorService executor = Executors.newFixedThreadPool(4);
        
        public CompletableFuture<Void> processItems(List<String> items) {
            // FAN-OUT: Crear múltiples tareas paralelas
            List<CompletableFuture<Void>> futures = items.stream()
                .map(item -> CompletableFuture.runAsync(() -> processItem(item), executor))
                .toList();
                
            // FAN-IN: Esperar que todas las tareas completen
            return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        }
        
        private void processItem(String item) {
            try {
                Thread.sleep(100); // Simular trabajo
                System.out.println("Procesado: " + item + " por " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        public void shutdown() {
            executor.shutdown();
        }
    }
    
    // FAN-IN: Agregar resultados de múltiples fuentes
    static class FanInExample {
        private final ExecutorService executor = Executors.newFixedThreadPool(3);
        
        public CompletableFuture<List<Integer>> aggregateResults() {
            // Crear múltiples fuentes de datos concurrentes
            CompletableFuture<List<Integer>> source1 = CompletableFuture.supplyAsync(this::fetchData1, executor);
            CompletableFuture<List<Integer>> source2 = CompletableFuture.supplyAsync(this::fetchData2, executor);
            CompletableFuture<List<Integer>> source3 = CompletableFuture.supplyAsync(this::fetchData3, executor);
            
            // FAN-IN: Combinar todos los resultados
            return source1.thenCombine(source2, (list1, list2) -> {
                List<Integer> combined = new ArrayList<>(list1);
                combined.addAll(list2);
                return combined;
            }).thenCombine(source3, (list1, list3) -> {
                list1.addAll(list3);
                return list1;
            });
        }
        
        private List<Integer> fetchData1() {
            sleep(200);
            return Arrays.asList(1, 2, 3);
        }
        
        private List<Integer> fetchData2() {
            sleep(150);
            return Arrays.asList(4, 5, 6);
        }
        
        private List<Integer> fetchData3() {
            sleep(100);
            return Arrays.asList(7, 8, 9);
        }
        
        private void sleep(long ms) {
            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        public void shutdown() {
            executor.shutdown();
        }
    }
    
    // Patrón completo con Virtual Threads (Java 21)
    static class CompleteFanPatternExample {
        
        public CompletableFuture<Map<String, Double>> analyzeData(List<String> dataSources) {
            try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
                
                // FAN-OUT: Análisis paralelo con virtual threads
                List<CompletableFuture<Map.Entry<String, Double>>> analysisFutures = dataSources.stream()
                    .map(source -> CompletableFuture.supplyAsync(() -> 
                        Map.entry(source, analyzeDataSource(source)), executor))
                    .toList();
                
                // FAN-IN: Agregar resultados
                CompletableFuture<Void> allAnalyses = CompletableFuture.allOf(
                    analysisFutures.toArray(new CompletableFuture[0]));
                
                return allAnalyses.thenApply(v -> {
                    Map<String, Double> results = new ConcurrentHashMap<>();
                    analysisFutures.forEach(future -> {
                        Map.Entry<String, Double> entry = future.join();
                        results.put(entry.getKey(), entry.getValue());
                    });
                    return results;
                });
            }
        }
        
        private double analyzeDataSource(String source) {
            try {
                Thread.sleep(50); // Simular I/O-bound trabajo
                return ThreadLocalRandom.current().nextDouble() * 100;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return 0.0;
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println("=== FAN-OUT Example ===");
        FanOutExample fanOut = new FanOutExample();
        List<String> items = Arrays.asList("Item1", "Item2", "Item3", "Item4", "Item5", "Item6");
        fanOut.processItems(items).join();
        fanOut.shutdown();
        
        System.out.println("\n=== FAN-IN Example ===");
        FanInExample fanIn = new FanInExample();
        List<Integer> aggregated = fanIn.aggregateResults().get();
        System.out.println("Resultado agregado: " + aggregated);
        fanIn.shutdown();
        
        System.out.println("\n=== Complete Fan Pattern with Virtual Threads ===");
        CompleteFanPatternExample completePattern = new CompleteFanPatternExample();
        Map<String, Double> results = completePattern.analyzeData(
            Arrays.asList("DB1", "API1", "Cache1", "File1", "Queue1")
        ).get();
        results.forEach((source, value) -> 
            System.out.println(source + ": " + String.format("%.2f", value)));
    }
}