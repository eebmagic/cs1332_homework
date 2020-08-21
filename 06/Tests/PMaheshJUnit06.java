import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Pranav Mahesh's JUnit tests for HW6 - ExternalChainingHashMap.
 * Comprehensive tests on put, remove, resize, the other methods, and exception testing!
 * Let me know if you have questions or if anything doesn't work by replying to my Piazza post!
 *
 * @author Pranav Mahesh
 * @version 1.1 (updated 2/19/20)
 */

public class PMaheshJUnit06 {
    private ExternalChainingHashMap<Integer, String> map;

    @Before
    public void setUp() {
        map = new ExternalChainingHashMap<Integer, String>();
    }

    @Test
    public void testInitialization() {
        assertEquals(0, map.size());
        assertArrayEquals(new ExternalChainingMapEntry[ExternalChainingHashMap
                .INITIAL_CAPACITY], map.getTable());
    }

    @Test
    public void testRegularConstructor() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        assertEquals(0, map.size());
        assertArrayEquals(new ExternalChainingMapEntry[map.INITIAL_CAPACITY], map.getTable());
    }

    // Is overloaded constructor working properly?
    @Test
    public void testCustomSize() {
        map = new ExternalChainingHashMap<Integer, String>(10);
        assertEquals(0, map.size());
        assertArrayEquals(new ExternalChainingMapEntry[10], map.getTable());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutNullKey() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(null, "A");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutNullValue() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutNullBoth() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(null, null);
    }

    @Test
    public void testPutBasic() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(0, "A");
        map.put(1, "B");
        map.put(2, "C");
        map.put(3, "D");
        assertEquals(4, map.size());

        ExternalChainingMapEntry<Integer, String>[] expectedMap = new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];
        expectedMap[0] = new ExternalChainingMapEntry<>(0, "A");
        expectedMap[1] = new ExternalChainingMapEntry<>(1, "B");
        expectedMap[2] = new ExternalChainingMapEntry<>(2, "C");
        expectedMap[3] = new ExternalChainingMapEntry<>(3, "D");

        assertArrayEquals(expectedMap, map.getTable());
    }

    @Test
    public void testPutBetterKey() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(13, "A");
        map.put(27, "B");
        map.put(41, "C");
        map.put(55, "D");
        assertEquals(4, map.size());

        ExternalChainingMapEntry<Integer, String>[] expectedMap = new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];
        expectedMap[0] = new ExternalChainingMapEntry<>(13, "A");
        expectedMap[1] = new ExternalChainingMapEntry<>(27, "B");
        expectedMap[2] = new ExternalChainingMapEntry<>(41, "C");
        expectedMap[3] = new ExternalChainingMapEntry<>(55, "D");

        assertArrayEquals(expectedMap, map.getTable());
    }

    @Test
    public void testDuplicates() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(0, "A");
        map.put(1, "B");
        map.put(2, "C");
        map.put(3, "D");

        assertEquals("A", map.put(0, "E"));
        assertEquals("B", map.put(1, "F"));
        assertEquals("C", map.put(2, "G"));
        assertEquals("D", map.put(3, "H"));
        assertEquals(4, map.size());

        ExternalChainingMapEntry<Integer, String>[] expectedMap = new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];
        expectedMap[0] = new ExternalChainingMapEntry<>(0, "E");
        expectedMap[1] = new ExternalChainingMapEntry<>(1, "F");
        expectedMap[2] = new ExternalChainingMapEntry<>(2, "G");
        expectedMap[3] = new ExternalChainingMapEntry<>(3, "H");

        assertArrayEquals(expectedMap, map.getTable());
    }

    @Test
    public void testResizeBeforeDuplicateCheck() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(0, "A");
        map.put(1, "B");
        map.put(2, "C");
        map.put(3, "D");
        map.put(4, "E");
        map.put(5, "F");
        map.put(6, "G");
        map.put(7, "H");
        map.put(3, "I");

        assertEquals(27, map.getTable().length);
        assertEquals(8, map.size());

        List<String> expected = new ArrayList<>(Arrays.asList(new String[]{"A", "B", "C", "I", "E", "F", "G", "H"}));
        assertEquals(expected, map.values());
    }

    /*
    index 0: <0,A>
    index 1: <27, "F">, <14, "E">
    index 2: <2, "C">
    index 3: <3, "D">
     */
    @Test
    public void testOneChain() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(0, "A");
        map.put(1, "B");
        map.put(2, "C");
        map.put(3, "D");
        assertNull(map.put(14, "E"));
        assertNull(map.put(27, "F"));
        assertEquals(6, map.size());

        List<String> expected = new ArrayList<>(Arrays.asList(new String[]{"A", "F", "E", "B", "C", "D"}));
        assertEquals(expected, map.values());
    }

    /*
    index 0: <0,A>
    index 1: <27, "F">, <14, "E">, <1, "B">
    index 2: <2, "C">
    index 3: <3, "D">
     */
    @Test
    public void testTwoChains() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(0, "A");
        map.put(1, "B");
        map.put(2, "C");
        map.put(3, "D");
        assertNull(map.put(14, "E"));
        assertNull(map.put(27, "F"));
        assertEquals(6, map.size());

        List<String> expected = new ArrayList<>(Arrays.asList(new String[]{"A", "F", "E", "B", "C", "D"}));
        assertEquals(expected, map.values());
    }

    @Test
    public void testMultipleChains() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(0, "A");
        map.put(1, "B");
        map.put(2, "C");
        map.put(3, "D");
        // Put in slot 1
        assertNull(map.put(14, "E"));
        assertNull(map.put(27, "F"));

        // Put in slot 3
        assertNull(map.put(16, "H"));
        assertNull(map.put(29, "I"));

        assertEquals(8, map.size());
        List<String> expected = new ArrayList<>(Arrays.asList(new String[]{"A", "F", "E", "B", "C", "I", "H", "D"}));
        assertEquals(expected, map.values());
    }

    @Test
    public void testDuplicateChain() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(0, "A");
        map.put(1, "B");
        map.put(2, "C");
        map.put(3, "D");

        // Put in slot 1
        assertNull(map.put(14, "E"));
        assertEquals("B", map.put(1, "F"));

        // Put in slot 3
        assertNull(map.put(16, "H"));

        assertEquals(6, map.size());
        List<String> expected = new ArrayList<>(Arrays.asList(new String[]{"A", "E", "F", "C", "H", "D"}));
        assertEquals(expected, map.values());
    }

    @Test
    public void testBasicLoadFactorSizeCheck() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(0, "A");
        map.put(1, "B");
        map.put(2, "C");
        map.put(3, "D");
        map.put(4, "E");
        map.put(5, "F");
        map.put(6, "G");
        map.put(7, "H");
        assertEquals(8, map.size());
        map.put(8, "I");
        // 9/13 > 0.67 - should resize
        assertEquals(27, map.getTable().length);

        ExternalChainingMapEntry<Integer, String>[] expectedMap = new ExternalChainingMapEntry[27];
        expectedMap[0] = new ExternalChainingMapEntry<>(0, "A");
        expectedMap[1] = new ExternalChainingMapEntry<>(1, "B");
        expectedMap[2] = new ExternalChainingMapEntry<>(2, "C");
        expectedMap[3] = new ExternalChainingMapEntry<>(3, "D");
        expectedMap[4] = new ExternalChainingMapEntry<>(4, "E");
        expectedMap[5] = new ExternalChainingMapEntry<>(5, "F");
        expectedMap[6] = new ExternalChainingMapEntry<>(6, "G");
        expectedMap[7] = new ExternalChainingMapEntry<>(7, "H");
        expectedMap[8] = new ExternalChainingMapEntry<>(8, "I");
        expectedMap[9] = null;

        assertArrayEquals(expectedMap, map.getTable());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveValue() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(0, "A");
        map.put(1, "B");
        map.put(2, "C");
        map.remove(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testValueNotInMap() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(0, "A");
        map.put(1, "B");
        map.put(2, "C");
        map.remove(3);
    }

    @Test
    public void testRemoveSizeOneChain() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(13, "A");
        map.put(14, "B");
        map.put(15, "C");
        map.put(16, "D");
        assertEquals("B", map.remove(14));
        assertEquals(3, map.size());
        ExternalChainingMapEntry<Integer, String>[] table = map.getTable();
        assertNull(table[1]);
    }

    @Test
    public void testRemoveFirstInSizeTwoChain() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(13, "A");
        map.put(14, "B");
        map.put(15, "C");
        map.put(16, "D");
        map.put(28, "E"); //slot 2 -> <28, E>, <15, C>
        assertEquals("E", map.remove(28)); //slot 2 -> <15, C>
        assertEquals(4, map.size());

        ExternalChainingMapEntry<Integer, String>[] table = map.getTable();
        assertEquals((Integer) 15, table[2].getKey());
        assertEquals("C", table[2].getValue());
        assertNull(table[2].getNext());
    }

    @Test
    public void testRemoveSecondInSizeTwoChain() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(13, "A");
        map.put(14, "B");
        map.put(15, "C");
        map.put(16, "D");
        map.put(28, "E"); //slot 2 -> <28, E>, <15, C>
        assertEquals("C", map.remove(15)); //slot 2 -> <28, E>
        assertEquals(4, map.size());

        ExternalChainingMapEntry<Integer, String>[] table = map.getTable();
        assertEquals((Integer) 28, table[2].getKey());
        assertEquals("E", table[2].getValue());
        assertNull(table[2].getNext());
    }

    @Test
    public void testRemoveFirstInSizeThreeChain() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(13, "A");
        map.put(14, "B");
        map.put(15, "C");
        map.put(16, "D");
        map.put(28, "E"); //slot 2 -> <28, E>, <15, C>
        map.put(41, "F"); //slot 2 -> <41, F>, <28, E>, <15, C>
        assertEquals("F", map.remove(41)); //slot 2 -> <28, E>, <15, C>
        assertEquals(5, map.size());

        ExternalChainingMapEntry<Integer, String>[] table = map.getTable();
        assertEquals((Integer) 28, table[2].getKey());
        assertEquals("E", table[2].getValue());
        assertEquals((Integer) 15, table[2].getNext().getKey());
        assertEquals("C", table[2].getNext().getValue());
    }

    @Test
    public void testRemoveSecondInSizeThreeChain() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(13, "A");
        map.put(14, "B");
        map.put(15, "C");
        map.put(16, "D");
        map.put(28, "E"); //slot 2 -> <28, E>, <15, C>
        map.put(41, "F"); //slot 2 -> <41, F>, <28, E>, <15, C>
        assertEquals("E", map.remove(28)); //slot 2 -> <41, F>, <15, C>
        assertEquals(5, map.size());

        ExternalChainingMapEntry<Integer, String>[] table = map.getTable();
        assertEquals((Integer) 41, table[2].getKey());
        assertEquals("F", table[2].getValue());
        assertEquals((Integer) 15, table[2].getNext().getKey());
        assertEquals("C", table[2].getNext().getValue());
    }

    @Test
    public void testRemoveThirdInSizeThreeChain() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(13, "A");
        map.put(14, "B");
        map.put(15, "C");
        map.put(16, "D");
        map.put(28, "E"); //slot 2 -> <28, E>, <15, C>
        map.put(41, "F"); //slot 2 -> <41, F>, <28, E>, <15, C>
        assertEquals("C", map.remove(15)); //slot 2 -> <41, F>, <28, E>
        assertEquals(5, map.size());

        ExternalChainingMapEntry<Integer, String>[] table = map.getTable();
        assertEquals((Integer) 41, table[2].getKey());
        assertEquals("F", table[2].getValue());
        assertEquals((Integer) 28, table[2].getNext().getKey());
        assertEquals("E", table[2].getNext().getValue());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveNotInChain() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(13, "A");
        map.put(14, "B");
        map.put(15, "C");
        map.put(16, "D");
        map.put(28, "E"); //slot 2 -> <28, E>, <15, C>
        map.put(41, "F"); //slot 2 -> <41, F>, <28, E>, <15, C>
        map.remove(54); //54%13 = 2, checks slot 2's chain for the key
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNullKey() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.get(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetNotInIndex() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(13, "A");
        map.put(14, "B");
        map.put(15, "C");
        map.put(16, "D");
        map.get(17);
    }

    @Test
    public void testGetFirstInChain() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(13, "A");
        map.put(14, "B");
        map.put(15, "C");
        map.put(16, "D");
        map.put(28, "E"); //slot 2 -> <28, E>, <15, C>
        map.put(41, "F"); //slot 2 -> <41, F>, <28, E>, <15, C>
        assertEquals("F", map.get(41));
    }

    @Test
    public void testGetSecondInChain() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(13, "A");
        map.put(14, "B");
        map.put(15, "C");
        map.put(16, "D");
        map.put(28, "E"); //slot 2 -> <28, E>, <15, C>
        map.put(41, "F"); //slot 2 -> <41, F>, <28, E>, <15, C>
        assertEquals("E", map.get(28));
    }

    @Test
    public void testGetThirdInChain() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(13, "A");
        map.put(14, "B");
        map.put(15, "C");
        map.put(16, "D");
        map.put(28, "E"); //slot 2 -> <28, E>, <15, C>
        map.put(41, "F"); //slot 2 -> <41, F>, <28, E>, <15, C>
        assertEquals("C", map.get(15));
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetNotInChain() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(13, "A");
        map.put(14, "B");
        map.put(15, "C");
        map.put(16, "D");
        map.put(28, "E"); //slot 2 -> <28, E>, <15, C>
        map.put(41, "F"); //slot 2 -> <41, F>, <28, E>, <15, C>
        map.get(54); //54 % 13 = 2, checks slot 2 and iterates through the chain
    }

    @Test(expected = IllegalArgumentException.class)
    public void testContainsNullKey() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.containsKey(null);
    }

    @Test
    public void testContainsNotInIndex() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(13, "A");
        map.put(14, "B");
        map.put(15, "C");
        map.put(16, "D");
        map.containsKey(17);
    }

    @Test
    public void testContainsFirstInChain() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(13, "A");
        map.put(14, "B");
        map.put(15, "C");
        map.put(16, "D");
        map.put(28, "E"); //slot 2 -> <28, E>, <15, C>
        map.put(41, "F"); //slot 2 -> <41, F>, <28, E>, <15, C>
        assertTrue(map.containsKey(41));
    }

    @Test
    public void testContainsSecondInChain() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(13, "A");
        map.put(14, "B");
        map.put(15, "C");
        map.put(16, "D");
        map.put(28, "E"); //slot 2 -> <28, E>, <15, C>
        map.put(41, "F"); //slot 2 -> <41, F>, <28, E>, <15, C>
        assertTrue(map.containsKey(28));
    }

    @Test
    public void testContainsThirdInChain() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(13, "A");
        map.put(14, "B");
        map.put(15, "C");
        map.put(16, "D");
        map.put(28, "E"); //slot 2 -> <28, E>, <15, C>
        map.put(41, "F"); //slot 2 -> <41, F>, <28, E>, <15, C>
        assertTrue(map.containsKey(15));
    }

    @Test
    public void testContainsNotInChain() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(13, "A");
        map.put(14, "B");
        map.put(15, "C");
        map.put(16, "D");
        map.put(28, "E"); //slot 2 -> <28, E>, <15, C>
        map.put(41, "F"); //slot 2 -> <41, F>, <28, E>, <15, C>
        assertFalse(map.containsKey(54)); //54 % 13 = 2, checks slot 2 and iterates through the chain
    }

    @Test
    public void testKeySet() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(13, "A");
        map.put(14, "B");
        map.put(15, "C");
        map.put(16, "D");
        map.put(28, "E"); //slot 2 -> <28, E>, <15, C>
        map.put(41, "F"); //slot 2 -> <41, F>, <28, E>, <15, C>
        map.put(0, "G"); //slot 0 -> <0, G>, <13, A>
        Set<Integer> set = new HashSet<>(Arrays.asList(new Integer[]{0, 13, 14, 41, 28, 15, 16}));
        assertEquals(set, map.keySet());
    }

    @Test
    public void testValues() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(13, "A");
        map.put(14, "B");
        map.put(15, "C");
        map.put(16, "D");
        map.put(28, "E"); //slot 2 -> <28, E>, <15, C>
        map.put(41, "F"); //slot 2 -> <41, F>, <28, E>, <15, C>
        map.put(0, "G"); //slot 0 -> <0, G>, <13, A>
        List<String> list = new ArrayList<>(Arrays.asList(new String[]{"G", "A", "B", "F", "E", "C", "D"}));
        assertEquals(list, map.values());
    }

    @Test
    public void testResizeSameSize() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.resizeBackingTable(7);
        assertEquals(7, map.getTable().length);
        assertEquals(0, map.size());

        map.put(21, "A");
        map.put(2, "B");
        map.put(16, "C");
        map.put(76, "D");
        assertEquals(7, map.getTable().length);
        /*
        slot 0 -> <21, A>
        slot 2 -> <16, C>, <2, B>
        slot 6 -> <76, D>
         */
        ExternalChainingMapEntry<Integer, String>[] table = map.getTable();

        assertEquals((Integer) 21, table[0].getKey());
        assertEquals("A", table[0].getValue());

        assertEquals((Integer) 16, table[2].getKey());
        assertEquals("C", table[2].getValue());

        assertEquals((Integer) 2, table[2].getNext().getKey());
        assertEquals("B", table[2].getNext().getValue());

        assertEquals((Integer) 76, table[6].getKey());
        assertEquals("D", table[6].getValue());
        assertNull(table[1]);
        assertNull(table[3]);
        assertNull(table[4]);
        assertNull(table[5]);
        assertNull(table[0].getNext());
        assertNull(table[6].getNext());

        // Resize to same length, chain reverses
        map.resizeBackingTable(7);

        ExternalChainingMapEntry<Integer, String>[] expected = map.getTable();

        assertEquals((Integer) 21, expected[0].getKey());
        assertEquals("A", expected[0].getValue());

        assertEquals((Integer) 2, expected[2].getKey());
        assertEquals("B", expected[2].getValue());

        assertEquals((Integer) 16, expected[2].getNext().getKey());
        assertEquals("C", expected[2].getNext().getValue());

        assertEquals((Integer) 76, expected[6].getKey());
        assertEquals("D", expected[6].getValue());
        assertNull(expected[1]);
        assertNull(expected[3]);
        assertNull(expected[4]);
        assertNull(expected[5]);
        assertNull(expected[0].getNext());
        assertNull(expected[6].getNext());
    }

    @Test
    public void testUpResize() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.resizeBackingTable(7);
        assertEquals(7, map.getTable().length);
        assertEquals(0, map.size());

        map.put(21, "A");
        map.put(2, "B");
        map.put(16, "C");
        map.put(76, "D");
        assertEquals(7, map.getTable().length);
        /*
        slot 0 -> <21, A>
        slot 2 -> <16, C>, <2, B>
        slot 6 -> <76, D>
         */
        ExternalChainingMapEntry<Integer, String>[] table = map.getTable();

        assertEquals((Integer) 21, table[0].getKey());
        assertEquals("A", table[0].getValue());

        assertEquals((Integer) 16, table[2].getKey());
        assertEquals("C", table[2].getValue());

        assertEquals((Integer) 2, table[2].getNext().getKey());
        assertEquals("B", table[2].getNext().getValue());

        assertEquals((Integer) 76, table[6].getKey());
        assertEquals("D", table[6].getValue());
        assertNull(table[1]);
        assertNull(table[3]);
        assertNull(table[4]);
        assertNull(table[5]);
        assertNull(table[0].getNext());
        assertNull(table[6].getNext());

        // Resize to length 10
        map.resizeBackingTable(10);

        /*
        slot 1 -> <21, A>
        slot 2 -> <2, B>
        slot 6 -> <76, D>, <16, C>
         */

        ExternalChainingMapEntry<Integer, String>[] expected = map.getTable();

        assertEquals((Integer) 21, expected[1].getKey());
        assertEquals("A", expected[1].getValue());

        assertEquals((Integer) 2, expected[2].getKey());
        assertEquals("B", expected[2].getValue());

        assertEquals((Integer) 76, expected[6].getKey());
        assertEquals("D", expected[6].getValue());
        assertEquals((Integer) 16, expected[6].getNext().getKey());
        assertEquals("C", expected[6].getNext().getValue());

        assertNull(expected[0]);
        assertNull(expected[3]);
        assertNull(expected[4]);
        assertNull(expected[5]);
        assertNull(expected[7]);
        assertNull(expected[8]);
        assertNull(expected[9]);
        assertNull(expected[1].getNext());
        assertNull(expected[2].getNext());
        assertNull(expected[6].getNext().getNext());
    }

    @Test
    public void testDownResize() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.resizeBackingTable(10);
        assertEquals(10, map.getTable().length);
        assertEquals(0, map.size());

        /*
        slot 1 -> <21, A>
        slot 2 -> <2, B>
        slot 6 -> <86, E> <76, D>, <16, C>
        slot 9 -> <19, F>
         */

        map.put(21, "A");
        map.put(2, "B");
        map.put(16, "C");
        map.put(76, "D");
        map.put(86, "E");
        map.put(19, "F");
        assertEquals(10, map.getTable().length);
        assertEquals(6, map.size());

        // Check if everything is put in properly
        ExternalChainingMapEntry<Integer, String>[] firstTable = map.getTable();

        assertEquals((Integer) 21, firstTable[1].getKey());
        assertEquals("A", firstTable[1].getValue());

        assertEquals((Integer) 2, firstTable[2].getKey());
        assertEquals("B", firstTable[2].getValue());

        assertEquals((Integer) 86, firstTable[6].getKey());
        assertEquals("E", firstTable[6].getValue());
        assertEquals((Integer) 76, firstTable[6].getNext().getKey());
        assertEquals("D", firstTable[6].getNext().getValue());
        assertEquals((Integer) 16, firstTable[6].getNext().getNext().getKey());
        assertEquals("C", firstTable[6].getNext().getNext().getValue());

        assertNull(firstTable[0]);
        assertNull(firstTable[3]);
        assertNull(firstTable[4]);
        assertNull(firstTable[5]);
        assertNull(firstTable[7]);
        assertNull(firstTable[8]);
        assertNull(firstTable[1].getNext());
        assertNull(firstTable[2].getNext());
        assertNull(firstTable[6].getNext().getNext().getNext());
        assertNull(firstTable[9].getNext());

        // Resize
        map.resizeBackingTable(6);
        /*
        slot 1 -> <19, F>
        slot 2 -> <86, F>, <2, B>
        slot 3 -> <21, A>
        slot 4 -> <16, C>, <76, D>
         */
        ExternalChainingMapEntry<Integer, String>[] table = map.getTable();

        // Check if all elements in proper slot
        assertEquals((Integer) 19, table[1].getKey());
        assertEquals("F", table[1].getValue());

        assertEquals((Integer) 86, table[2].getKey());
        assertEquals("E", table[2].getValue());
        assertEquals((Integer) 2, table[2].getNext().getKey());
        assertEquals("B", table[2].getNext().getValue());

        assertEquals((Integer) 21, table[3].getKey());
        assertEquals("A", table[3].getValue());

        assertEquals((Integer) 16, table[4].getKey());
        assertEquals("C", table[4].getValue());
        assertEquals((Integer) 76, table[4].getNext().getKey());
        assertEquals("D", table[4].getNext().getValue());

        assertNull(table[0]);
        assertNull(table[5]);

        assertNull(table[1].getNext());
        assertNull(table[2].getNext().getNext());
        assertNull(table[3].getNext());
        assertNull(table[4].getNext().getNext());
    }

    @Test
    public void testMultipleResize() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.resizeBackingTable(10);
        assertEquals(10, map.getTable().length);
        assertEquals(0, map.size());

        /*
        slot 1 -> <21, A>
        slot 2 -> <2, B>
        slot 6 -> <86, E> <76, D>, <16, C>
        slot 9 -> <19, F>
         */

        map.put(21, "A");
        map.put(2, "B");
        map.put(16, "C");
        map.put(76, "D");
        map.put(86, "E");
        map.put(19, "F");
        assertEquals(10, map.getTable().length);
        assertEquals(6, map.size());

        // Resize
        map.resizeBackingTable(6);
        /*
        slot 1 -> <19, F>
        slot 2 -> <86, F>, <2, B>
        slot 3 -> <21, A>
        slot 4 -> <16, C>, <76, D>
         */

        // Resize
        map.resizeBackingTable(8);
        /*
        A nice, spread out table with no collisions!
        slot 0 -> <16, C>
        slot 2 -> <2, B>
        slot 3 -> <19, F>
        slot 4 -> <76, D>
        slot 5 -> <21, A>
        slot 6 -> <86, E>
         */

        ExternalChainingMapEntry<Integer, String>[] table = map.getTable();

        // Check if all elements in proper slot
        assertEquals((Integer) 16, table[0].getKey());
        assertEquals("C", table[0].getValue());

        assertEquals((Integer) 2, table[2].getKey());
        assertEquals("B", table[2].getValue());

        assertEquals((Integer) 19, table[3].getKey());
        assertEquals("F", table[3].getValue());

        assertEquals((Integer) 76, table[4].getKey());
        assertEquals("D", table[4].getValue());

        assertEquals((Integer) 21, table[5].getKey());
        assertEquals("A", table[5].getValue());

        assertEquals((Integer) 86, table[6].getKey());
        assertEquals("E", table[6].getValue());

        assertNull(table[1]);
        assertNull(table[7]);

        assertNull(table[0].getNext());
        assertNull(table[2].getNext());
        assertNull(table[3].getNext());
        assertNull(table[4].getNext());
        assertNull(table[5].getNext());
        assertNull(table[6].getNext());
        assertEquals(6, map.size());
        assertEquals(8, map.getTable().length);
    }

    @Test
    public void testClear() {
        map = new ExternalChainingHashMap<Integer, String>(map.INITIAL_CAPACITY);
        map.put(0, "A");
        map.put(1, "B");
        map.put(2, "C");
        map.put(3, "D");

        map.resizeBackingTable(5);
        assertEquals(4, map.size());
        assertEquals(5, map.getTable().length);

        map.clear();
        assertEquals(0, map.size());
        assertEquals(map.INITIAL_CAPACITY, map.getTable().length);

        ExternalChainingMapEntry<Integer, String>[] table = map.getTable();
        assertNull(table[0]);
        assertNull(table[1]);
        assertNull(table[2]);
        assertNull(table[3]);
    }
}