
package metrics;

public class Metrics {
    private long dsuOps;
    private long edgeScans;
    private long comparisons;
    private int maxDepth;

    public void addDsuOps(long count) {
        dsuOps += count;
    }

    public void addEdgeScan() {
        edgeScans++;
    }

    public void addComparison() {
        comparisons++;
    }

    public void updateMaxDepth(int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }
    }

    public long getDsuOps() {
        return dsuOps;
    }

    public long getEdgeScans() {
        return edgeScans;
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxDepth() {
        return maxDepth;
    }
}
