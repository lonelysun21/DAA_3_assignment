
package algorithms;

import graph.Graph;
import graph.GraphReader;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import graph.Edge;
import java.util.ArrayDeque;
import java.util.Random;

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

    @Test
    void testRandomGraphs() {
        Random random = new Random(42);

        for (int t = 0; t < 25; t++) {
            int n = 1 + random.nextInt(200);
            Graph graph = new Graph(n, true);

            for (int i = 0; i < n * 3; i++) {
                int a = random.nextInt(n);
                int b = random.nextInt(n);
                graph.addEdge(a, b, 1);
            }

            List<List<Integer>> components = Kosaraju.findSCC(graph);
            int[] group = new int[n];

            for (int i = 0; i < components.size(); i++) {
                for (int v : components.get(i)) {
                    group[v] = i;
                }
            }

            boolean[][] reachable = new boolean[n][n];

            for (int start = 0; start < n; start++) {
                ArrayDeque<Integer> queue = new ArrayDeque<>();
                queue.add(start);
                reachable[start][start] = true;

                while (!queue.isEmpty()) {
                    int v = queue.poll();

                    for (Edge edge : graph.neighbors(v)) {
                        if (!reachable[start][edge.to]) {
                            reachable[start][edge.to] = true;
                            queue.add(edge.to);
                        }
                    }
                }
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    boolean same = reachable[i][j] && reachable[j][i];
                    assertEquals(same, group[i] == group[j]);
                }
            }
        }
    }

}
