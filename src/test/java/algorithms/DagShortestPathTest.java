
package algorithms;

import graph.Graph;
import graph.GraphReader;
import org.junit.jupiter.api.Test;

import java.util.List;

import graph.Edge;
import java.util.Arrays;
import java.util.Random;


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

    @Test
    void testRandomDagAgainstBellmanFord() {
        Random random = new Random(42);

        for (int t = 0; t < 50; t++) {
            int n = 2 + random.nextInt(20);
            Graph graph = new Graph(n, true);

            for (int i = 0; i < n * 3; i++) {
                int a = random.nextInt(n);
                int b = random.nextInt(n);

                if (a == b) {
                    continue;
                }

                if (a > b) {
                    int temp = a;
                    a = b;
                    b = temp;
                }

                int weight = random.nextInt(41) - 20;
                graph.addEdge(a, b, weight);
            }

            int[] indegree = new int[n];
            long[] expected = new long[n];
            Arrays.fill(expected, Long.MAX_VALUE);

            for (Edge edge : graph.getEdges()) {
                indegree[edge.to]++;
            }

            for (int i = 0; i < n; i++) {
                if (indegree[i] == 0) {
                    expected[i] = 0;
                }
            }

            for (int i = 0; i < n - 1; i++) {
                for (Edge edge : graph.getEdges()) {
                    if (expected[edge.from] == Long.MAX_VALUE) {
                        continue;
                    }

                    long distance = expected[edge.from] + edge.weight;

                    if (distance < expected[edge.to]) {
                        expected[edge.to] = distance;
                    }
                }
            }

            DagShortestPath result = DagShortestPath.findPaths(graph);

            assertArrayEquals(expected, result.getDistances());
        }
    }

    @Test
    void testMultipleSourcesAndNegativeWeights() {
        Graph graph = new Graph(6, true);

        graph.addEdge(0, 2, 5);
        graph.addEdge(1, 2, -4);
        graph.addEdge(2, 3, -2);
        graph.addEdge(0, 3, 10);

        DagShortestPath result = DagShortestPath.findPaths(graph);

        assertEquals(0, result.getDistance(0));
        assertEquals(0, result.getDistance(1));
        assertEquals(-4, result.getDistance(2));
        assertEquals(-6, result.getDistance(3));
        assertEquals(0, result.getDistance(4));
        assertEquals(0, result.getDistance(5));

        assertEquals(List.of(1, 2, 3), result.getPath(3));
        assertEquals(List.of(4), result.getPath(4));
        assertEquals(List.of(5), result.getPath(5));
    }

    @Test
    void testEmptyDag() {
        Graph graph = new Graph(0, true);

        DagShortestPath result = DagShortestPath.findPaths(graph);

        assertEquals(0, result.getDistances().length);
        assertEquals(0, result.getParents().length);
    }


}
