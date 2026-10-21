
package algorithms;

import graph.Graph;
import graph.GraphReader;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class KosarajuTest {

    @Test
    void testCyclicGraph() throws Exception {
        Graph graph = GraphReader.read("data/cyclic.txt");

        List<List<Integer>> components = Kosaraju.findSCC(graph);
        Set<Set<Integer>> groups = new HashSet<>();

        for (List<Integer> component : components) {
            groups.add(new HashSet<>(component));
        }

        assertEquals(2, components.size());
        assertEquals(
                Set.of(Set.of(0, 1, 2), Set.of(3, 4)),
                groups
        );
    }

    @Test
    void testAcyclicGraph() {
        Graph graph = new Graph(5, true);

        graph.addEdge(0, 1, 4);
        graph.addEdge(1, 2, 7);

        List<List<Integer>> components = Kosaraju.findSCC(graph);

        assertEquals(5, components.size());
    }

    @Test
    void testUndirectedGraph() {
        Graph graph = new Graph(3, false);
        graph.addEdge(0, 1, 5);

        assertThrows(IllegalArgumentException.class,
                () -> Kosaraju.findSCC(graph));
    }
}
