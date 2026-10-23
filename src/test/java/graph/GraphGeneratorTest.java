package graph;

import algorithms.Kahn;
import algorithms.Kruskal;
import graph.Edge;
import graph.Graph;
import graph.GraphGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GraphGeneratorTest {

    @Test
    void testGraphSizes() {
        Graph undirected = GraphGenerator.undirected(30, 120);
        Graph directed = GraphGenerator.directed(30, 120);
        Graph dag = GraphGenerator.dag(30, 120);

        assertEquals(30, undirected.vertexCount());
        assertEquals(120, undirected.getEdges().size());
        assertEquals(120, directed.getEdges().size());
        assertEquals(120, dag.getEdges().size());

        Assertions.assertEquals(1, Kruskal.findMST(undirected).getComponents());
        Assertions.assertEquals(30, Kahn.topologicalSort(dag).size());
    }

    @Test
    void testSameSeed() {
        Graph first = GraphGenerator.directed(30, 120);
        Graph second = GraphGenerator.directed(30, 120);

        for (int i = 0; i < 120; i++) {
            Edge a = first.getEdges().get(i);
            Edge b = second.getEdges().get(i);

            assertEquals(a.from, b.from);
            assertEquals(a.to, b.to);
            assertEquals(a.weight, b.weight);
        }
    }
}
