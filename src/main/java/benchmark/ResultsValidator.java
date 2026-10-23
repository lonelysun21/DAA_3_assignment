package benchmark;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ResultsValidator {

    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(
                Path.of("results/results.csv"));

        if (lines.size() != 51) {
            throw new IllegalStateException("Invalid row count");
        }

        int kruskal = 0;
        int prim = 0;
        int kosaraju = 0;
        int kahn = 0;
        int dag = 0;

        for (int i = 1; i < lines.size(); i++) {
            String[] row = lines.get(i).split(",");

            if (row.length != 10) {
                throw new IllegalStateException("Invalid CSV row");
            }

            String algorithm = row[0];
            long n = Long.parseLong(row[2]);
            long m = Long.parseLong(row[3]);
            long scans = Long.parseLong(row[6]);
            long result = Long.parseLong(row[9]);

            if (algorithm.equals("Kruskal")) {
                kruskal++;
            } else if (algorithm.equals("Prim")) {
                prim++;
            } else if (algorithm.equals("Kosaraju")) {
                kosaraju++;

                if (scans != 2 * m) {
                    throw new IllegalStateException("Invalid SCC scans");
                }
            } else if (algorithm.equals("Kahn")) {
                kahn++;

                if (scans != m || result != n) {
                    throw new IllegalStateException("Invalid Kahn result");
                }
            } else if (algorithm.equals("DagShortestPath")) {
                dag++;

                if (scans != m) {
                    throw new IllegalStateException("Invalid DAG scans");
                }
            } else {
                throw new IllegalStateException("Unknown algorithm");
            }
        }

        if (kruskal != 10 || prim != 10 || kosaraju != 10
                || kahn != 10 || dag != 10) {
            throw new IllegalStateException("Missing algorithms");
        }

        System.out.println("CSV validation passed");
    }
}
