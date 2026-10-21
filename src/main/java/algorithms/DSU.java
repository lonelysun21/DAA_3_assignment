package algorithms;

public class DSU {
    private long findCalls;
    private long unionCalls;

    private int[] parent;
    private int[] size;
    private int count;

    public DSU(int n) {
        makeSet(n);
    }

    public void makeSet(int n) {
        parent = new int[n];
        size = new int[n];
        count = n;
        findCalls = 0;
        unionCalls = 0;


        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }


    public int find(int x) {
        findCalls++;

        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }


    public boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA == rootB) {
            return false;
        }

        if (size[rootA] < size[rootB]) {
            int temp = rootA;
            rootA = rootB;
            rootB = temp;
        }

        parent[rootB] = rootA;
        size[rootA] += size[rootB];
        count--;

        unionCalls++;

        return true;
    }



    public int components() {
        return count;
    }

    public long getFindCalls() {
        return findCalls;
    }

    public long getUnionCalls() {
        return unionCalls;
    }

    public long getDsuOps() {
        return findCalls + unionCalls;
    }

}
