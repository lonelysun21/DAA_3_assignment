package algorithms;

import graph.Graph;
import graph.GraphReader;
import org.junit.jupiter.api.Test;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MSTTest {

    @Test
    void testConnectedGraph() {
        Graph graph = new Graph(4, false);

        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 4);
        graph.addEdge(1, 2, 2);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 3, 3);

        MSTResult kruskal = Kruskal.findMST(graph);
        MSTResult prim = Prim.findMST(graph);

        assertEquals(6, kruskal.getTotalWeight());
        assertEquals(6, prim.getTotalWeight());
        assertEquals(3, kruskal.getEdges().size());
        assertEquals(3, prim.getEdges().size());
        assertEquals(1, kruskal.getComponents());
        assertEquals(1, prim.getComponents());
    }

    @Test
    void testDisconnectedGraph() throws Exception {
        Graph graph = GraphReader.read("data/disconnected.txt");

        MSTResult kruskal = Kruskal.findMST(graph);
        MSTResult prim = Prim.findMST(graph);

        assertEquals(13, kruskal.getTotalWeight());
        assertEquals(13, prim.getTotalWeight());
        assertEquals(3, kruskal.getComponents());
        assertEquals(3, prim.getComponents());
        assertEquals(3, kruskal.getEdges().size());
        assertEquals(3, prim.getEdges().size());
    }


    @Test
    void testRandomGraphs() {
        Random random = new Random(42);

        for (int t = 0; t < 100; t++) {
            int n = 2 + random.nextInt(30);
            Graph graph = new Graph(n, false);

            for (int i = 1; i < n; i++) {
                int parent = random.nextInt(i);
                int weight = 1 + random.nextInt(100);
                graph.addEdge(i, parent, weight);
            }

            for (int i = 0; i < n * 2; i++) {
                int a = random.nextInt(n);
                int b = random.nextInt(n);
                int weight = 1 + random.nextInt(100);
                graph.addEdge(a, b, weight);
            }

            MSTResult kruskal = Kruskal.findMST(graph);
            MSTResult prim = Prim.findMST(graph);

            assertEquals(kruskal.getTotalWeight(), prim.getTotalWeight());
            assertEquals(n - 1, kruskal.getEdges().size());
            assertEquals(n - 1, prim.getEdges().size());
            assertEquals(1, kruskal.getComponents());
            assertEquals(1, prim.getComponents());
        }
    }
}
