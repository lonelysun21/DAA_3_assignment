
package algorithms;

import graph.Edge;
import graph.Graph;

import java.util.Arrays;
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
            if (result.distance[v] == Long.MAX_VALUE) {
                continue;
            }

            for (Edge edge : graph.neighbors(v)) {
                long newDistance = result.distance[v] + edge.weight;

                if (newDistance < result.distance[edge.to]) {
                    result.distance[edge.to] = newDistance;
                    result.parent[edge.to] = v;
                }
            }
        }

        return result;
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
