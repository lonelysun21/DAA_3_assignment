package benchmark;

import graph.Graph;
import graph.GraphGenerator;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class Benchmark {

    public static void main(String[] args) throws IOException {
        int[] sizes = {1000, 3162, 10000, 31623, 100000};
        int[] densities = {4, 20};

        long[] mstExpected = {
                147970, 30363, 468911, 97589, 1486139,
                309550, 4715515, 968517, 15012094, 3056681
        };

        int[] sccExpected = {
                27, 1, 137, 1, 373, 1, 1188, 1, 3923, 1
        };

        long[] dagExpected = {
                396274, 252411, 1271911, 723157, 3941828,
                2381342, 12551891, 7376612, 40096539, 23512087
        };

        Files.createDirectories(Path.of("results"));

        try (PrintWriter writer = new PrintWriter(
                Files.newBufferedWriter(Path.of("results/results.csv")))) {

            writer.println("algorithm,graph,n,m,time_ms,dsu_ops,edge_scans,comparisons,max_depth,result");

            int index = 0;

            for (int n : sizes) {
                for (int density : densities) {
                    int m = n * density;
                    String type = density == 4 ? "sparse" : "dense";

                    Graph graph = GraphGenerator.undirected(n, m);

                    BenchmarkRow kruskal = BenchmarkRunner.measure(
                            "Kruskal", "undirected_" + type, graph);

                    BenchmarkRow prim = BenchmarkRunner.measure(
                            "Prim", "undirected_" + type, graph);

                    if (kruskal.result != prim.result ||
                            kruskal.result != mstExpected[index]) {
                        throw new IllegalStateException(
                                "MST mismatch: " + n + "/" + m +
                                        " got " + kruskal.result +
                                        " expected " + mstExpected[index]);
                    }

                    writer.println(kruskal.toCsv());
                    writer.println(prim.toCsv());

                    graph = null;
                    graph = GraphGenerator.directed(n, m);

                    BenchmarkRow scc = BenchmarkRunner.measure(
                            "Kosaraju", "directed_" + type, graph);

                    if (scc.result != sccExpected[index] ||
                            scc.edgeScans != 2L * m) {
                        throw new IllegalStateException(
                                "SCC mismatch: " + n + "/" + m +
                                        " got " + scc.result +
                                        " expected " + sccExpected[index]);
                    }

                    writer.println(scc.toCsv());

                    graph = null;
                    graph = GraphGenerator.dag(n, m);

                    BenchmarkRow kahn = BenchmarkRunner.measure(
                            "Kahn", "dag_" + type, graph);

                    BenchmarkRow dag = BenchmarkRunner.measure(
                            "DagShortestPath", "dag_" + type, graph);

                    if (kahn.result != n || kahn.edgeScans != m) {
                        throw new IllegalStateException("Kahn mismatch");
                    }

                    if (dag.result != dagExpected[index] ||
                            dag.edgeScans != m) {
                        throw new IllegalStateException(
                                "DAG mismatch: " + n + "/" + m +
                                        " got " + dag.result +
                                        " expected " + dagExpected[index]);
                    }

                    writer.println(kahn.toCsv());
                    writer.println(dag.toCsv());
                    writer.flush();

                    System.out.println(n + " / " + m +
                            " MST=" + kruskal.result +
                            " SCC=" + scc.result +
                            " DAG=" + dag.result);

                    index++;
                }
            }
        }

        System.out.println("Benchmark completed");
    }
}
