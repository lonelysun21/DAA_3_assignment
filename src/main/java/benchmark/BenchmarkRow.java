package benchmark;

import metrics.Metrics;
import java.util.Locale;

public class BenchmarkRow {
    public final String algorithm;
    public final String graph;
    public final int n;
    public final int m;
    public final double timeMs;
    public final long dsuOps;
    public final long edgeScans;
    public final long comparisons;
    public final int maxDepth;
    public final long result;

    public BenchmarkRow(String algorithm, String graph, int n, int m,
                        double timeMs, Metrics metrics, long result) {
        this.algorithm = algorithm;
        this.graph = graph;
        this.n = n;
        this.m = m;
        this.timeMs = timeMs;
        this.dsuOps = metrics.getDsuOps();
        this.edgeScans = metrics.getEdgeScans();
        this.comparisons = metrics.getComparisons();
        this.maxDepth = metrics.getMaxDepth();
        this.result = result;
    }

    public String toCsv() {
        return algorithm + "," + graph + "," + n + "," + m + ","
                + String.format(Locale.US, "%.3f", timeMs) + ","
                + dsuOps + "," + edgeScans + "," + comparisons
                + "," + maxDepth + "," + result;
    }
}
