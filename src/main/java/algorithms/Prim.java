package algorithms;

import graph.Edge;
import graph.Graph;
import metrics.Metrics;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Prim {

    public static MSTResult findMST(Graph graph) {
        return findMST(graph, new Metrics());
    }

    public static MSTResult findMST(Graph graph, Metrics metrics) {
        if (graph.isDirected()) {
            throw new IllegalArgumentException("Graph must be undirected");
        }

        int n = graph.vertexCount();
        boolean[] visited = new boolean[n];

        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> {
            metrics.addComparison();
            return Integer.compare(a.weight, b.weight);
        });

        List<Edge> mstEdges = new ArrayList<>();
        long totalWeight = 0;
        int components = 0;

        for (int start = 0; start < n; start++) {
            if (visited[start]) {
                continue;
            }

            components++;
            visited[start] = true;

            for (Edge edge : graph.neighbors(start)) {
                metrics.addEdgeScan();
                pq.add(edge);
            }

            while (!pq.isEmpty()) {
                Edge edge = pq.poll();

                if (visited[edge.to]) {
                    continue;
                }

                visited[edge.to] = true;
                mstEdges.add(edge);
                totalWeight += edge.weight;

                for (Edge next : graph.neighbors(edge.to)) {
                    metrics.addEdgeScan();

                    if (!visited[next.to]) {
                        pq.add(next);
                    }
                }
            }
        }

        return new MSTResult(totalWeight, components, mstEdges);
    }
}
