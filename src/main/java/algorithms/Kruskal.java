
package algorithms;

import graph.Edge;
import graph.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Kruskal {

    public static MSTResult findMST(Graph graph) {
        if (graph.isDirected()) {
            throw new IllegalArgumentException("Graph must be undirected");
        }

        List<Edge> edges = new ArrayList<>(graph.getEdges());
        edges.sort(Comparator.comparingInt(e -> e.weight));

        DSU dsu = new DSU(graph.vertexCount());
        List<Edge> mstEdges = new ArrayList<>();
        long totalWeight = 0;

        for (Edge edge : edges) {
            if (dsu.union(edge.from, edge.to)) {
                mstEdges.add(edge);
                totalWeight += edge.weight;
            }

            if (dsu.components() == 1) {
                break;
            }
        }

        return new MSTResult(totalWeight, dsu.components(), mstEdges);
    }
}
