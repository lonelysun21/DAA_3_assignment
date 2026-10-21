
package algorithms;

import graph.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

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
}
