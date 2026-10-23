package algorithms;

import graph.Edge;
import graph.Graph;
import metrics.Metrics;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Kosaraju {

    public static List<Integer> finishOrder(Graph graph) {
        return finishOrder(graph, new Metrics());
    }

    public static List<Integer> finishOrder(Graph graph, Metrics metrics) {
        if (!graph.isDirected()) {
            throw new IllegalArgumentException("Graph must be directed");
        }

        int n = graph.vertexCount();
        boolean[] visited = new boolean[n];
        int[] next = new int[n];
        List<Integer> order = new ArrayList<>();
        ArrayDeque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            if (visited[i]) {
                continue;
            }

            visited[i] = true;
            stack.push(i);
            metrics.updateMaxDepth(stack.size());

            while (!stack.isEmpty()) {
                int v = stack.peek();

                if (next[v] < graph.neighbors(v).size()) {
                    Edge edge = graph.neighbors(v).get(next[v]++);
                    metrics.addEdgeScan();

                    if (!visited[edge.to]) {
                        visited[edge.to] = true;
                        stack.push(edge.to);
                        metrics.updateMaxDepth(stack.size());
                    }
                } else {
                    order.add(stack.pop());
                }
            }
        }

        return order;
    }

    public static List<List<Integer>> findSCC(Graph graph) {
        return findSCC(graph, new Metrics());
    }

    public static List<List<Integer>> findSCC(Graph graph, Metrics metrics) {
        int n = graph.vertexCount();
        List<Integer> order = finishOrder(graph, metrics);
        Graph reversed = new Graph(n, true);

        for (Edge edge : graph.getEdges()) {
            reversed.addEdge(edge.to, edge.from, edge.weight);
        }

        boolean[] visited = new boolean[n];
        List<List<Integer>> components = new ArrayList<>();
        ArrayDeque<Integer> stack = new ArrayDeque<>();

        for (int i = order.size() - 1; i >= 0; i--) {
            int start = order.get(i);

            if (visited[start]) {
                continue;
            }

            List<Integer> component = new ArrayList<>();
            visited[start] = true;
            stack.push(start);
            metrics.updateMaxDepth(stack.size());

            while (!stack.isEmpty()) {
                int v = stack.pop();
                component.add(v);

                for (Edge edge : reversed.neighbors(v)) {
                    metrics.addEdgeScan();

                    if (!visited[edge.to]) {
                        visited[edge.to] = true;
                        stack.push(edge.to);
                        metrics.updateMaxDepth(stack.size());
                    }
                }
            }

            components.add(component);
        }

        return components;
    }
}
