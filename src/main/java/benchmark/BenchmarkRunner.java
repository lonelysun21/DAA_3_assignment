package benchmark;

import algorithms.*;
import graph.Graph;
import metrics.Metrics;

import java.util.Arrays;

public class BenchmarkRunner {

    public static BenchmarkRow measure(String algorithm, String graphName,
                                       Graph graph) {
        long[] times = new long[5];
        Metrics savedMetrics = null;
        long result = 0;

        for (int i = 0; i < 5; i++) {
            Metrics metrics = new Metrics();
            long start = System.nanoTime();
            long value;

            switch (algorithm) {
                case "Kruskal":
                    value = Kruskal.findMST(graph, metrics).getTotalWeight();
                    break;
                case "Prim":
                    value = Prim.findMST(graph, metrics).getTotalWeight();
                    break;
                case "Kosaraju":
                    value = Kosaraju.findSCC(graph, metrics).size();
                    break;
                case "Kahn":
                    value = Kahn.topologicalSort(graph, metrics).size();
                    break;
                case "DagShortestPath":
                    DagShortestPath paths = DagShortestPath.findPaths(graph, metrics);
                    long sum = 0;

                    for (long distance : paths.getDistances()) {
                        if (distance == Long.MAX_VALUE) {
                            throw new IllegalStateException("Unreachable vertex");
                        }
                        sum += distance;
                    }

                    value = sum;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown algorithm");
            }

            times[i] = System.nanoTime() - start;

            if (i > 0 && result != value) {
                throw new IllegalStateException("Different results");
            }

            result = value;
            savedMetrics = metrics;
        }

        Arrays.sort(times);

        return new BenchmarkRow(
                algorithm,
                graphName,
                graph.vertexCount(),
                graph.getEdges().size(),
                times[2] / 1_000_000.0,
                savedMetrics,
                result
        );
    }
}
