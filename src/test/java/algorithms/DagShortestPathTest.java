
package algorithms;

import graph.Graph;
import graph.GraphReader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DagShortestPathTest {

    @Test
    void testDistances() throws Exception {
        Graph graph = GraphReader.read("data/dag.txt");

        DagShortestPath result = DagShortestPath.findPaths(graph);

        assertEquals(0, result.getDistance(0));
        assertEquals(0, result.getDistance(1));
        assertEquals(3, result.getDistance(2));
        assertEquals(-2, result.getDistance(3));
        assertEquals(5, result.getDistance(4));
        assertEquals(4, result.getDistance(5));
    }

    @Test
    void testPathRestoration() throws Exception {
        Graph graph = GraphReader.read("data/dag.txt");

        DagShortestPath result = DagShortestPath.findPaths(graph);

        assertEquals(List.of(1, 3, 4, 5), result.getPath(5));
        assertEquals(List.of(1, 2), result.getPath(2));
        assertEquals(List.of(0), result.getPath(0));
    }

    @Test
    void testCyclicGraph() {
        Graph graph = new Graph(3, true);

        graph.addEdge(0, 1, 2);
        graph.addEdge(1, 2, 3);
        graph.addEdge(2, 0, 4);

        assertThrows(IllegalStateException.class,
                () -> DagShortestPath.findPaths(graph));
    }
}
