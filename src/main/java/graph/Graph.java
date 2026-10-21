
package graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final int n;
    private final boolean directed;
    private final List<List<Edge>> adjacency;
    private final List<Edge> edges;

    public Graph(int n, boolean directed) {
        this.n = n;
        this.directed = directed;
        adjacency = new ArrayList<>();
        edges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adjacency.add(new ArrayList<>());
        }
    }

    public void addEdge(int from, int to, int weight) {
        Edge edge = new Edge(from, to, weight);
        edges.add(edge);
        adjacency.get(from).add(edge);

        if (!directed) {
            adjacency.get(to).add(new Edge(to, from, weight));
        }
    }

    public int vertexCount() {
        return n;
    }

    public boolean isDirected() {
        return directed;
    }

    public List<Edge> neighbors(int vertex) {
        return adjacency.get(vertex);
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
