
package algorithms;

import graph.Graph;
import graph.GraphReader;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KahnTest {

    @Test
    void testDAG() throws Exception {
        Graph graph = GraphReader.read("data/dag.txt");

        List<Integer> order = Kahn.topologicalSort(graph);

        assertEquals(6, order.size());
        assertEquals(6, new HashSet<>(order).size());
    }

    @Test
    void testCyclicGraph() throws Exception {
        Graph graph = GraphReader.read("data/cyclic.txt");

        assertThrows(IllegalStateException.class,
                () -> Kahn.topologicalSort(graph));
    }

    @Test
    void testUndirectedGraph() {
        Graph graph = new Graph(4, false);
        graph.addEdge(0, 1, 5);

        assertThrows(IllegalArgumentException.class,
                () -> Kahn.topologicalSort(graph));
    }
}
