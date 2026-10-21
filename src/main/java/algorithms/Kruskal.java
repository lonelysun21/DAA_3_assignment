package algorithms;

import graph.Edge;
import graph.Graph;
import metrics.Metrics;

import java.util.ArrayList;
import java.util.List;

public class Kruskal {

    public static MSTResult findMST(Graph graph) {
        return findMST(graph, new Metrics());
    }

    public static MSTResult findMST(Graph graph, Metrics metrics) {
        if (graph.isDirected()) {
            throw new IllegalArgumentException("Graph must be undirected");
        }

        List<Edge> edges = new ArrayList<>(graph.getEdges());

        edges.sort((a, b) -> {
            metrics.addComparison();
            return Integer.compare(a.weight, b.weight);
        });

        DSU dsu = new DSU(graph.vertexCount());
        List<Edge> mstEdges = new ArrayList<>();
        long totalWeight = 0;

        for (Edge edge : edges) {
            metrics.addEdgeScan();

            if (dsu.union(edge.from, edge.to)) {
                mstEdges.add(edge);
                totalWeight += edge.weight;
            }

            if (dsu.components() == 1) {
                break;
            }
        }

        metrics.addDsuOps(dsu.getDsuOps());

        return new MSTResult(totalWeight, dsu.components(), mstEdges);
    }
}
