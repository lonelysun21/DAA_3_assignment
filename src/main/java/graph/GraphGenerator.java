package graph;

import java.util.Random;

public class GraphGenerator {

    public static Graph undirected(int n, int m) {
        Random random = new Random(42);
        Graph graph = new Graph(n, false);

        int[] perm = new int[n];

        for (int i = 0; i < n; i++) {
            perm[i] = i;
        }

        for (int i = n - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = perm[i];
            perm[i] = perm[j];
            perm[j] = temp;
        }

        for (int i = 1; i < n; i++) {
            int a = perm[random.nextInt(i)];
            int b = perm[i];
            int weight = 1 + random.nextInt(1000);
            graph.addEdge(a, b, weight);
        }

        for (int i = n - 1; i < m; i++) {
            int a = random.nextInt(n);
            int b = random.nextInt(n);

            while (a == b) {
                b = random.nextInt(n);
            }

            int weight = 1 + random.nextInt(1000);
            graph.addEdge(a, b, weight);
        }

        return graph;
    }

    public static Graph directed(int n, int m) {
        Random random = new Random(42);
        Graph graph = new Graph(n, true);

        for (int i = 0; i < m; i++) {
            int a = random.nextInt(n);
            int b = random.nextInt(n);

            while (a == b) {
                b = random.nextInt(n);
            }

            int weight = 1 + random.nextInt(1000);
            graph.addEdge(a, b, weight);
        }

        return graph;
    }
}
