
package algorithms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
}
