
package algorithms;

import graph.Edge;
import graph.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Kahn {

    public static List<Integer> topologicalSort(Graph graph) {
        if (!graph.isDirected()) {
            throw new IllegalArgumentException("Graph must be directed");
        }

        int n = graph.vertexCount();
        int[] indegree = new int[n];

        for (Edge edge : graph.getEdges()) {
            indegree[edge.to]++;
        }

        ArrayDeque<Integer> queue = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }

        List<Integer> order = new ArrayList<>();

        while (!queue.isEmpty()) {
            int v = queue.poll();
            order.add(v);

            for (Edge edge : graph.neighbors(v)) {
                indegree[edge.to]--;

                if (indegree[edge.to] == 0) {
                    queue.add(edge.to);
                }
            }
        }

        if (order.size() != n) {
            throw new IllegalStateException("Graph contains a cycle");
        }

        return order;
    }
}
