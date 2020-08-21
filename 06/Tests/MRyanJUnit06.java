import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Additional JUnit Tests for the HashMap Homework
 *
 * @author Michael Ryan
 * @version 1.0
 */

public class MRyanJUnit06 {

    private static final int TIMEOUT = 200;
    private ExternalChainingHashMap<Integer, String> map;

    @Before
    public void setUp() {
        map = new ExternalChainingHashMap<>();
    }

    @Test(timeout = TIMEOUT)
    public void testPutReplace() {
        String temp = "BEE";

        // [(0, A), (1, BEE), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, temp));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));

        assertEquals(5, map.size());
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];

        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertSame(temp, map.put(1,"B"));

        assertEquals(5, map.size());

        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT)
    public void testPutReplaceChainHead() {
        String temp = "AYY";

        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));

        // [(13, AYY) -> (0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(ExternalChainingHashMap.INITIAL_CAPACITY, temp));

        assertEquals(6, map.size());

        // [(13, AYO) -> (0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertEquals(temp, map.put(ExternalChainingHashMap.INITIAL_CAPACITY, "AYO"));

        assertEquals(6, map.size());

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];

        expected[0] = new ExternalChainingMapEntry<>(ExternalChainingHashMap.INITIAL_CAPACITY, "AYO");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");

        assertArrayEquals(expected, map.getTable());

        assertEquals(new ExternalChainingMapEntry<>(0, "A"), map.getTable()[0].getNext());
    }

    @Test(timeout = TIMEOUT)
    public void testPutReplaceInChain() {
        String temp = "AYY";

        // [(0, AYY), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, temp));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));

        // [(13, A) -> (0, AYY), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(ExternalChainingHashMap.INITIAL_CAPACITY, "A"));

        assertEquals(6, map.size());

        // [(13, A) -> (0, AYO), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertEquals(temp, map.put(0, "AYO"));

        assertEquals(6, map.size());

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];

        expected[0] = new ExternalChainingMapEntry<>(ExternalChainingHashMap.INITIAL_CAPACITY, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");

        assertArrayEquals(expected, map.getTable());

        assertEquals(new ExternalChainingMapEntry<>(0, "AYO"), map.getTable()[0].getNext());
    }

    @Test(timeout= TIMEOUT)
    public void testChain() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));

        // [(13, F) -> (0, A), (14, G) -> (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(ExternalChainingHashMap.INITIAL_CAPACITY, "F"));
        assertNull(map.put(ExternalChainingHashMap.INITIAL_CAPACITY + 1, "G"));

        assertEquals(7, map.size());
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];
        expected[0] = new ExternalChainingMapEntry<>(ExternalChainingHashMap
                .INITIAL_CAPACITY, "F");
        expected[1] = new ExternalChainingMapEntry<>(ExternalChainingHashMap
                .INITIAL_CAPACITY + 1, "G");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        assertArrayEquals(expected, map.getTable());

        assertEquals(new ExternalChainingMapEntry<>(ExternalChainingHashMap.INITIAL_CAPACITY, "F"), map.getTable()[0]);
        assertEquals(new ExternalChainingMapEntry<>(0, "A"), map.getTable()[0].getNext());
        assertEquals(new ExternalChainingMapEntry<>(ExternalChainingHashMap.INITIAL_CAPACITY + 1, "G"), map.getTable()[1]);
        assertEquals(new ExternalChainingMapEntry<>(1, "B"), map.getTable()[1].getNext());
    }

    @Test(timeout = TIMEOUT)
    public void testChainFromResize() {
        // [(0, A), (1, B), _, _, _, (5, C), (6, D) , _, _, _, (10, E), _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(5, "C"));
        assertNull(map.put(6, "D"));
        assertNull(map.put(10, "E"));
        assertEquals(5, map.size());

        // [(10, "E") -> (5, "C") -> (0, "A"), (6, "D") -> (1, "B"), _, _, _]
        map.resizeBackingTable(5);

        assertEquals(5, map.size());
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[5];

        expected[0] = new ExternalChainingMapEntry<>(10, "E");
        expected[1] = new ExternalChainingMapEntry<>(6, "D");

        assertArrayEquals(expected, map.getTable());

        assertEquals(new ExternalChainingMapEntry<>(5, "C"), map.getTable()[0].getNext());
        assertEquals(new ExternalChainingMapEntry<>(0, "A"), map.getTable()[0].getNext().getNext());
        assertEquals(new ExternalChainingMapEntry<>(1, "B"), map.getTable()[1].getNext());
    }

    @Test(timeout = TIMEOUT)
    public void testResizeEmpty() {
        // [_, _,]
        map.resizeBackingTable(2);

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[2];

        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testUnchainFromResize() {

        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));

        assertEquals(5, map.size());

        // [(13, F) -> (0, A), (14, G) -> (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(ExternalChainingHashMap.INITIAL_CAPACITY, "F"));
        assertNull(map.put(ExternalChainingHashMap.INITIAL_CAPACITY + 1, "G"));

        assertEquals(7, map.size());

        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _, (13, F), (14, G)]
        map.resizeBackingTable(ExternalChainingHashMap.INITIAL_CAPACITY + 2);

        assertEquals(7, map.size());

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY + 2];

        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        expected[ExternalChainingHashMap.INITIAL_CAPACITY] =
                new ExternalChainingMapEntry<>(ExternalChainingHashMap.INITIAL_CAPACITY, "F");
        expected[ExternalChainingHashMap.INITIAL_CAPACITY + 1] =
                new ExternalChainingMapEntry<>(ExternalChainingHashMap.INITIAL_CAPACITY + 1, "G");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testAutoResize() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(5, "F"));
        assertNull(map.put(6, "G"));
        assertNull(map.put(7, "H"));

        assertEquals(ExternalChainingHashMap.INITIAL_CAPACITY, map.getTable().length);
        assertEquals(8, map.size());

        assertNull(map.put(8, "I"));

        assertEquals((ExternalChainingHashMap.INITIAL_CAPACITY * 2) + 1, map.getTable().length);
        assertEquals(9, map.size());

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[(ExternalChainingHashMap
                                .INITIAL_CAPACITY * 2) + 1];

        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        expected[5] = new ExternalChainingMapEntry<>(5, "F");
        expected[6] = new ExternalChainingMapEntry<>(6, "G");
        expected[7] = new ExternalChainingMapEntry<>(7, "H");
        expected[8] = new ExternalChainingMapEntry<>(8, "I");

        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testAutoResizeAfterManualResize() {
        // [(0, A), (1, B), (2, C), (3, D), _, _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));

        // [(0, A), (1, B), (2, C), (3, D)]
        map.resizeBackingTable(4);

        assertEquals(4, map.size());

        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _]
        assertNull(map.put(4, "E"));

        assertEquals(5, map.size());
        assertEquals(9, map.getTable().length);

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[9];

        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");

        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveChainTail() {
        String temp = "D";

        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, temp));
        assertNull(map.put(4, "E"));
        assertEquals(5, map.size());

        // [(0, A), (1, B), (2, C), (16, DEE) -> (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(ExternalChainingHashMap.INITIAL_CAPACITY + 3, "DEE"));
        assertEquals(6, map.size());

        // [(0, A), (1, B), (2, C), (16, DEE), (4, E), _, _, _, _, _, _, _, _]
        assertSame(temp, map.remove(3));

        assertEquals(5, map.size());

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];
        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(ExternalChainingHashMap.INITIAL_CAPACITY + 3, "DEE");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveChainHead() {
        String temp = "DEE";

        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertEquals(5, map.size());

        // [(0, A), (1, B), (2, C), (16, DEE) -> (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(ExternalChainingHashMap.INITIAL_CAPACITY + 3, temp));
        assertEquals(6, map.size());

        assertSame(temp, map.remove(ExternalChainingHashMap.INITIAL_CAPACITY + 3));
        assertEquals(5, map.size());

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];

        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");

        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveChainMid() {
        String temp = "DINNER,";

        // [(0, A), (1, B), (2, CHARLES), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "CHARLES"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertEquals(5, map.size());

        // [(0, A), (1, B), (15, DINNER,) -> (2, CHARLES), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(ExternalChainingHashMap.INITIAL_CAPACITY + 2, temp));
        assertEquals(6, map.size());

        // [(0, A), (1, B), (28, EAT) -> (15, DINNER,) -> (2, CHARLES), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put((ExternalChainingHashMap.INITIAL_CAPACITY * 2) + 2, "EAT"));
        assertEquals(7, map.size());

        // [(0, A), (1, B), (28, EAT) -> (2, CHARLES), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertEquals(temp, map.remove(ExternalChainingHashMap.INITIAL_CAPACITY + 2));
        assertEquals(6, map.size());

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];

        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>((ExternalChainingHashMap.INITIAL_CAPACITY * 2) + 2, "EAT");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");

        assertArrayEquals(expected, map.getTable());

        assertEquals(new ExternalChainingMapEntry<>(2, "CHARLES"), map.getTable()[2].getNext());
    }

    @Test(timeout = TIMEOUT)
    public void testGetFromChain() {
        String temp = "BEE";

        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));

        // [(0, A), (14, BEE) -> (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(ExternalChainingHashMap.INITIAL_CAPACITY + 1, temp));

        assertSame(temp, map.get(ExternalChainingHashMap.INITIAL_CAPACITY + 1));

        // [(13, AYY) -> (0, A), (14, BEE) -> (1, B), (15, CEE) -> (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(ExternalChainingHashMap.INITIAL_CAPACITY, "AYY"));
        assertNull(map.put(ExternalChainingHashMap.INITIAL_CAPACITY + 2, "CEE"));

        assertEquals("AYY", map.get(ExternalChainingHashMap.INITIAL_CAPACITY));
        assertEquals("CEE", map.get(ExternalChainingHashMap.INITIAL_CAPACITY + 2));
    }

    @Test(timeout = TIMEOUT)
    public void testKeySetChained() {
        // [(13, Z) -> (0, A), (14, X) -> (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(ExternalChainingHashMap.INITIAL_CAPACITY, "Z"));
        assertNull(map.put(ExternalChainingHashMap.INITIAL_CAPACITY + 1, "X"));

        assertEquals(7, map.size());

        Set<Integer> expected = new HashSet<>();
        expected.add(0);
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        expected.add(ExternalChainingHashMap.INITIAL_CAPACITY);
        expected.add(ExternalChainingHashMap.INITIAL_CAPACITY + 1);

        assertEquals(expected, map.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void testValuesChained() {
        // [(13, Z) -> (0, A), (14, X) -> (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(ExternalChainingHashMap.INITIAL_CAPACITY, "Z"));
        assertNull(map.put(ExternalChainingHashMap.INITIAL_CAPACITY + 1, "X"));

        List<String> expected = new LinkedList<>();
        expected.add("Z");
        expected.add("A");
        expected.add("X");
        expected.add("B");
        expected.add("C");
        expected.add("D");
        expected.add("E");
        assertEquals(expected, map.values());
    }

    @Test(timeout = TIMEOUT)
    public void testContainsKeyChained() {
        // [(13, Z) -> (0, A), (14, X) -> (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(ExternalChainingHashMap.INITIAL_CAPACITY, "Z"));
        assertNull(map.put(ExternalChainingHashMap.INITIAL_CAPACITY + 1, "X"));

        assertTrue(map.containsKey(ExternalChainingHashMap.INITIAL_CAPACITY));
        assertTrue(map.containsKey(0));
        assertTrue(map.containsKey(ExternalChainingHashMap.INITIAL_CAPACITY + 1));
        assertTrue(map.containsKey(1));
        assertFalse(map.containsKey(ExternalChainingHashMap.INITIAL_CAPACITY + 2));
        assertTrue(map.containsKey(2));
    }

//    @Test(timeout = TIMEOUT)
//    public void testContainsKeyZeroLengthTable() {
//        map.resizeBackingTable(0);
//        assertFalse(map.containsKey(0)); // I think this might throw a divide by zero for some implementations
//    }

    @Test(timeout = TIMEOUT)
    public void testKeyNotInteger() {
        ExternalChainingHashMap<String, Integer> smap = new ExternalChainingHashMap<>();

        // [(A, 0), (B, 1), (C, 2), _, _, _, _, _, _, _, _, _, _]
        assertNull(smap.put("A", 0));
        assertNull(smap.put("B", 1));
        assertNull(smap.put("C", 2));

        assertEquals(3, smap.size());

        ExternalChainingMapEntry<String, Integer>[] expected =
                (ExternalChainingMapEntry<String, Integer>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];

        expected[0] = new ExternalChainingMapEntry<>("A", 0);
        expected[1] = new ExternalChainingMapEntry<>("B", 1);
        expected[2] = new ExternalChainingMapEntry<>("C", 2);

        assertArrayEquals(expected, smap.getTable());

        // [(N, 13) -> (A, 65), (O, 14) -> (B, 1), (P, 15) -> (C, 16), _, (E, 4), _, _, _, _, _, _, _, _]
        assertNull(smap.put("N", 13));
        assertNull(smap.put("O", 14));
        assertNull(smap.put("P", 15));
        assertNull(smap.put("E", 4));
        assertEquals(0, (int) smap.put("A", 65));

        assertEquals(7, smap.size());

        expected[0] = new ExternalChainingMapEntry<>("N", 13);
        expected[1] = new ExternalChainingMapEntry<>("O", 14);
        expected[2] = new ExternalChainingMapEntry<>("P", 15);
        expected[4] = new ExternalChainingMapEntry<>("E", 4);

        assertArrayEquals(expected, smap.getTable());

        assertEquals(new ExternalChainingMapEntry<>("A", 65), smap.getTable()[0].getNext());
        assertEquals(new ExternalChainingMapEntry<>("B", 1), smap.getTable()[1].getNext());
        assertEquals(new ExternalChainingMapEntry<>("C", 2), smap.getTable()[2].getNext());
    }

    @Test(timeout = TIMEOUT)
    public void testAlternateConstructor() {
        ExternalChainingHashMap<Integer, String> newMap = new ExternalChainingHashMap<>(5);

        assertEquals(0, newMap.size());
        assertEquals(5, newMap.getTable().length);
    }

    @Test(timeout = TIMEOUT)
    public void testResizeFromZero() {
        map.resizeBackingTable(0);

        // [(A, 0)]
        assertNull(map.put(0, "A")); // I think this could cause some divide by zero errors for some people

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[1];

        expected[0] = new ExternalChainingMapEntry<>(0, "A");

        assertArrayEquals(expected, map.getTable());

        // [(A, 0), (B, 1), _]
        assertNull(map.put(1, "B"));

        expected = (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[3];

        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");

        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testPutNullKey() {
        map.put(null, "A");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testPutNullVal() {
        map.put(0, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testPutNullBoth() {
        map.put(null, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemoveNull() {
        map.remove(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveNotInMap() {
        map.remove(0);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveNotInChain() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(ExternalChainingHashMap.INITIAL_CAPACITY, "B"));
        map.remove(ExternalChainingHashMap.INITIAL_CAPACITY * 2);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveTwice() {
        assertNull(map.put(0, "A"));
        assertEquals("A", map.remove(0));
        map.remove(0);
    }

//    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
//    public void testRemoveFromZeroSizedTable() {
//        map.resizeBackingTable(0);
//        map.remove(0); // I think some implementations will divide by zero here
//    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testGetNull() {
        map.get(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetNotInMap() {
        map.get(0);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetAfterRemove() {
        assertNull(map.put(0, "A"));
        assertEquals("A", map.remove(0));
        map.get(0);
    }

//    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
//    public void testGetFromZeroLengthTable() {
//        map.resizeBackingTable(0);
//        map.get(0); // Some implementations might divide by zero here
//    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testContainsNullKey() {
        map.containsKey(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testResizeToSmallerThanSize() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        map.resizeBackingTable(1);
    }

}