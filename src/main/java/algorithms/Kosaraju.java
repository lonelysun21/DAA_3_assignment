package algorithms;

import graph.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import graph.Edge;

public class Kosaraju {

    public static List<Integer> finishOrder(Graph graph) {
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

            while (!stack.isEmpty()) {
                int v = stack.peek();

                if (next[v] < graph.neighbors(v).size()) {
                    int to = graph.neighbors(v).get(next[v]++).to;

                    if (!visited[to]) {
                        visited[to] = true;
                        stack.push(to);
                    }
                } else {
                    order.add(stack.pop());
                }
            }
        }

        return order;
    }

    public static List<List<Integer>> findSCC(Graph graph) {
        int n = graph.vertexCount();
        List<Integer> order = finishOrder(graph);

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

            while (!stack.isEmpty()) {
                int v = stack.pop();
                component.add(v);

                for (Edge edge : reversed.neighbors(v)) {
                    int to = edge.to;

                    if (!visited[to]) {
                        visited[to] = true;
                        stack.push(to);
                    }
                }
            }

            components.add(component);
        }

        return components;
    }

}
