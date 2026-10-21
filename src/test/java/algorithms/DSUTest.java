
package algorithms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;


public class DSUTest {

    @Test
    void testUnionAndFind() {
        DSU dsu = new DSU(5);

        assertEquals(5, dsu.components());

        assertTrue(dsu.union(0, 1));
        assertTrue(dsu.union(1, 2));

        assertEquals(dsu.find(0), dsu.find(2));
        assertNotEquals(dsu.find(0), dsu.find(3));

        assertFalse(dsu.union(0, 2));
        assertEquals(3, dsu.components());
    }

    @Test
    void testCounters() {
        DSU dsu = new DSU(3);

        dsu.union(0, 1);
        dsu.find(0);

        assertEquals(1, dsu.getUnionCalls());
        assertTrue(dsu.getFindCalls() >= 3);
    }

    @Test
    void testRandomUnions() {
        int n = 30;
        DSU dsu = new DSU(n);
        int[] groups = new int[n];
        Random random = new Random(42);

        for (int i = 0; i < n; i++) {
            groups[i] = i;
        }

        for (int i = 0; i < 200; i++) {
            int a = random.nextInt(n);
            int b = random.nextInt(n);

            int groupA = groups[a];
            int groupB = groups[b];

            assertEquals(groupA != groupB, dsu.union(a, b));

            for (int j = 0; j < n; j++) {
                if (groups[j] == groupB) {
                    groups[j] = groupA;
                }
            }

            Set<Integer> unique = new HashSet<>();

            for (int group : groups) {
                unique.add(group);
            }

            assertEquals(unique.size(), dsu.components());
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                assertEquals(groups[i] == groups[j],
                        dsu.find(i) == dsu.find(j));
            }
        }
    }

}
