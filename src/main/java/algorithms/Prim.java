
package algorithms;

import graph.Edge;
import graph.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Prim {

    public static MSTResult findMST(Graph graph) {
        if (graph.isDirected()) {
            throw new IllegalArgumentException("Graph must be undirected");
        }

        int n = graph.vertexCount();
        boolean[] visited = new boolean[n];
        PriorityQueue<Edge> pq = new PriorityQueue<>(
                Comparator.comparingInt(e -> e.weight));

        List<Edge> mstEdges = new ArrayList<>();
        long totalWeight = 0;
        int components = 0;

        for (int start = 0; start < n; start++) {
            if (visited[start]) {
                continue;
            }

            components++;
            visited[start] = true;
            pq.addAll(graph.neighbors(start));

            while (!pq.isEmpty()) {
                Edge edge = pq.poll();

                if (visited[edge.to]) {
                    continue;
                }

                visited[edge.to] = true;
                mstEdges.add(edge);
                totalWeight += edge.weight;

                for (Edge next : graph.neighbors(edge.to)) {
                    if (!visited[next.to]) {
                        pq.add(next);
                    }
                }
            }
        }

        return new MSTResult(totalWeight, components, mstEdges);
    }
}
