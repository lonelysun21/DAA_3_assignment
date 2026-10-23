package algorithms;

import graph.Edge;
import graph.Graph;
import metrics.Metrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DagShortestPath {
    private final long[] distance;
    private final int[] parent;

    private DagShortestPath(int n) {
        distance = new long[n];
        parent = new int[n];

        Arrays.fill(distance, Long.MAX_VALUE);
        Arrays.fill(parent, -1);
    }

    public static DagShortestPath findPaths(Graph graph) {
        return findPaths(graph, new Metrics());
    }

    public static DagShortestPath findPaths(Graph graph, Metrics metrics) {
        List<Integer> order = Kahn.topologicalSort(graph);
        int n = graph.vertexCount();
        int[] indegree = new int[n];

        for (Edge edge : graph.getEdges()) {
            indegree[edge.to]++;
        }

        DagShortestPath result = new DagShortestPath(n);

        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                result.distance[i] = 0;
            }
        }

        for (int v : order) {
            for (Edge edge : graph.neighbors(v)) {
                metrics.addEdgeScan();

                if (result.distance[v] == Long.MAX_VALUE) {
                    continue;
                }

                long newDistance = result.distance[v] + edge.weight;

                if (newDistance < result.distance[edge.to]) {
                    result.distance[edge.to] = newDistance;
                    result.parent[edge.to] = v;
                }
            }
        }

        return result;
    }

    public List<Integer> getPath(int vertex) {
        List<Integer> path = new ArrayList<>();

        if (distance[vertex] == Long.MAX_VALUE) {
            return path;
        }

        int current = vertex;

        while (current != -1) {
            path.add(current);
            current = parent[current];
        }

        Collections.reverse(path);
        return path;
    }

    public long getDistance(int vertex) {
        return distance[vertex];
    }

    public long[] getDistances() {
        return distance;
    }

    public int[] getParents() {
        return parent;
    }
}
