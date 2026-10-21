package algorithms;

import graph.Edge;
import java.util.List;

public class MSTResult {
    private final long totalWeight;
    private final int components;
    private final List<Edge> edges;

    public MSTResult(long totalWeight, int components, List<Edge> edges) {
        this.totalWeight = totalWeight;
        this.components = components;
        this.edges = edges;
    }

    public long getTotalWeight() {
        return totalWeight;
    }

    public int getComponents() {
        return components;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
