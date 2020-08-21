import org.junit.Before;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

/**
 * This set of JUnits is for HW06: Externally Chained Hash Maps
 *
 * This contains detailed test cases for each method you implemented, including tests for each exception thrown
 * and many edge cases (primarily chaining and resizing, some cases even include both at once).
 *
 * If you find any issues, please reply to my Piazza post. Hope this helps.
 */

public class Adi_JUnit_HW06 {

    private static final int TIMEOUT = 200;
    private ExternalChainingHashMap<Integer, String> map;

    @Before
    public void setUp() {
        map = new ExternalChainingHashMap<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, map.size());
        assertArrayEquals(new ExternalChainingMapEntry[ExternalChainingHashMap
                .INITIAL_CAPACITY], map.getTable());
    }

    //put Tests
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void putNullKey() {
        map.put(null, "word");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void putNullValue() {
        map.put(1, null);
    }

    @Test(timeout = TIMEOUT)
    public void putToEmptyTable() {
        ExternalChainingMapEntry<Integer, String>[] expected = new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];
        expected[8] = new ExternalChainingMapEntry<>(21, "word");
        map.put(21, "word");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void putWithAllCollisions() {
        ExternalChainingMapEntry<Integer, String>[] expected = new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];

        ExternalChainingMapEntry<Integer, String> entry1 = new ExternalChainingMapEntry<>(2, "word", null);
        ExternalChainingMapEntry<Integer, String> entry2 = new ExternalChainingMapEntry<>(15, "word1", entry1);
        ExternalChainingMapEntry<Integer, String> entry3 = new ExternalChainingMapEntry<>(28, "word2", entry2);
        expected[2] = entry3;


        map.put(2, "word");
        map.put(15, "word1");
        map.put(28, "word2");

        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void putSameKeyAtFrontIndex() {
        ExternalChainingMapEntry<Integer, String>[] expected = new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];
        expected[5] = new ExternalChainingMapEntry<>(5, "newWord");

        map.put(5, "oldWord");

        assertEquals("oldWord", map.put(5, "newWord"));
        assertArrayEquals(expected, map.getTable());
        assertEquals(1, map.size());
    }

    @Test(timeout = TIMEOUT)
    public void putSameKeyAtEndIndex() {
        ExternalChainingMapEntry<Integer, String>[] expected = new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];

        ExternalChainingMapEntry<Integer, String> entry1 = new ExternalChainingMapEntry<>(2, "newWord", null);
        ExternalChainingMapEntry<Integer, String> entry2 = new ExternalChainingMapEntry<>(15, "word1", entry1);
        ExternalChainingMapEntry<Integer, String> entry3 = new ExternalChainingMapEntry<>(28, "word2", entry2);
        expected[2] = entry3;

        map.put(2, "oldWord");
        map.put(15, "word1");
        map.put(28, "word2");

        assertEquals("oldWord", map.put(2, "newWord"));
        assertArrayEquals(expected, map.getTable());
        assertEquals(3, map.size());
    }

    @Test(timeout = TIMEOUT)
    public void putAtVariousIndices() {
        ExternalChainingMapEntry<Integer, String>[] expected = new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];
        expected[2] = new ExternalChainingMapEntry<>(15, "word");
        expected[9] = new ExternalChainingMapEntry<>(22, "word1");
        expected[12] = new ExternalChainingMapEntry<>(25, "word2");
        expected[0] = new ExternalChainingMapEntry<>(13, "word3");

        map.put(15, "word");
        map.put(22, "word1");
        map.put(25, "word2");
        map.put(13, "word3");

        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void putAndResize() {
        map.put(1, "word");
        map.put(1, "replaceWord"); //this call shouldn't be incrementing the size counter
        map.put(14, "word1");
        map.put(2, "word");
        map.put(15, "word1");
        map.put(3, "word");
        map.put(16, "word1");
        map.put(4, "word");
        map.put(17, "word1");
        map.put(5, "word"); //this 9th put will trigger a resize

        ExternalChainingMapEntry[] expected = new ExternalChainingMapEntry[27];
        expected[1] = new ExternalChainingMapEntry(1, "replaceWord");
        expected[14] = new ExternalChainingMapEntry(14, "word1");
        expected[2] = new ExternalChainingMapEntry(2, "word");
        expected[15] = new ExternalChainingMapEntry(15, "word1");
        expected[3] = new ExternalChainingMapEntry(3, "word");
        expected[16] = new ExternalChainingMapEntry(16, "word1");
        expected[4] = new ExternalChainingMapEntry(4, "word");
        expected[17] = new ExternalChainingMapEntry(17, "word1");
        expected[5] = new ExternalChainingMapEntry(5, "word");

        assertEquals(9, map.size());
        assertArrayEquals(expected, map.getTable());
        assertEquals(expected.length, map.getTable().length);
    }

    //remove Tests
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void removeNull() {
        map.remove(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeWhenKeyNotInIndex() {
        ExternalChainingMapEntry<Integer, String>[] expected = new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];

        expected[6] = new ExternalChainingMapEntry<>(19, "word");
        map.put(19, "word");
        map.remove(18);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeWhenKeyNotInLinkedList() {
        ExternalChainingMapEntry<Integer, String>[] expected = new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];

        ExternalChainingMapEntry<Integer, String> entry1 = new ExternalChainingMapEntry<>(2, "word", null);
        ExternalChainingMapEntry<Integer, String> entry2 = new ExternalChainingMapEntry<>(15, "word1", entry1);
        ExternalChainingMapEntry<Integer, String> entry3 = new ExternalChainingMapEntry<>(28, "word2", entry2);
        expected[2] = entry3;

        map.put(2, "word");
        map.put(15, "word1");
        map.put(28, "word2");

        map.remove(41);
    }

    @Test(timeout = TIMEOUT)
    public void removeKeyAtFrontOfLinkedList() {
        ExternalChainingMapEntry<Integer, String>[] expected = new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];

        ExternalChainingMapEntry<Integer, String> entry1 = new ExternalChainingMapEntry<>(2, "word", null);
        ExternalChainingMapEntry<Integer, String> entry2 = new ExternalChainingMapEntry<>(15, "word1", entry1);
        expected[2] = entry2;

        map.put(2, "word");
        map.put(15, "word1");
        map.put(28, "word2");

        map.remove(28);
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void removeKeyInMiddleOfLinkedList() {
        ExternalChainingMapEntry<Integer, String>[] expected = new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];

        ExternalChainingMapEntry<Integer, String> entry1 = new ExternalChainingMapEntry<>(2, "word", null);
        ExternalChainingMapEntry<Integer, String> entry2 = new ExternalChainingMapEntry<>(28, "word2", entry1);
        expected[2] = entry2;

        map.put(2, "word");
        map.put(15, "word1");
        map.put(28, "word2");

        map.remove(15);
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void removeKeyAtEndOfLinkedList() {
        ExternalChainingMapEntry<Integer, String>[] expected = new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];

        ExternalChainingMapEntry<Integer, String> entry1 = new ExternalChainingMapEntry<>(15, "word1", null);
        ExternalChainingMapEntry<Integer, String> entry2 = new ExternalChainingMapEntry<>(28, "word2", entry1);
        expected[2] = entry2;

        map.put(2, "word");
        map.put(15, "word1");
        map.put(28, "word2");

        map.remove(2);
        assertArrayEquals(expected, map.getTable());
    }
    //get Tests

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void getNullKey() {
        map.get(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void getKeyNotInFirstIndex() {
        map.put(11, "word");
        map.put(9, "word1");

        map.get(10);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void getKeyNotInFilledIndex() {
        map.put(2, "word");
        map.put(15, "word1");
        map.put(28, "word2");

        map.get(41);
    }

    @Test(timeout = TIMEOUT)
    public void getKeyAtFrontOfIndex() {
        ExternalChainingMapEntry<Integer, String>[] expected = new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];

        ExternalChainingMapEntry<Integer, String> entry1 = new ExternalChainingMapEntry<>(2, "word", null);
        ExternalChainingMapEntry<Integer, String> entry2 = new ExternalChainingMapEntry<>(15, "word1", entry1);
        ExternalChainingMapEntry<Integer, String> entry3 = new ExternalChainingMapEntry<>(28, "word2", entry2);
        expected[2] = entry3;

        map.put(2, "word");
        map.put(15, "word1");
        map.put(28, "word2");

        assertEquals("word2", map.get(28));
    }

    @Test(timeout = TIMEOUT)
    public void getKeyInMiddleOfLinkedList() {
        ExternalChainingMapEntry<Integer, String>[] expected = new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];

        ExternalChainingMapEntry<Integer, String> entry1 = new ExternalChainingMapEntry<>(2, "word", null);
        ExternalChainingMapEntry<Integer, String> entry2 = new ExternalChainingMapEntry<>(15, "word1", entry1);
        ExternalChainingMapEntry<Integer, String> entry3 = new ExternalChainingMapEntry<>(28, "word2", entry2);
        expected[2] = entry3;

        map.put(2, "word");
        map.put(15, "word1");
        map.put(28, "word2");

        assertEquals("word1", map.get(15));
    }

    @Test(timeout = TIMEOUT)
    public void getKeyAtEndOfLinkedList() {
        ExternalChainingMapEntry<Integer, String>[] expected = new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];

        ExternalChainingMapEntry<Integer, String> entry1 = new ExternalChainingMapEntry<>(2, "word", null);
        ExternalChainingMapEntry<Integer, String> entry2 = new ExternalChainingMapEntry<>(15, "word1", entry1);
        ExternalChainingMapEntry<Integer, String> entry3 = new ExternalChainingMapEntry<>(28, "word2", entry2);
        expected[2] = entry3;

        map.put(2, "word");
        map.put(15, "word1");
        map.put(28, "word2");

        assertEquals("word", map.get(2));
    }

    //containsKey Tests
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void containsNullKeyTest() {
        map.containsKey(null);
    }

    @Test(timeout = TIMEOUT)
    public void containsKeyAtEmptyIndex() {
        map.put(4, "word");
        map.put(6, "word1");

        assertFalse(map.containsKey(18));
    }

    @Test(timeout = TIMEOUT)
    public void containsKeyAtFrontOfIndex() {
        ExternalChainingMapEntry<Integer, String>[] expected = new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];

        ExternalChainingMapEntry<Integer, String> entry1 = new ExternalChainingMapEntry<>(2, "word", null);
        ExternalChainingMapEntry<Integer, String> entry2 = new ExternalChainingMapEntry<>(15, "word1", entry1);
        ExternalChainingMapEntry<Integer, String> entry3 = new ExternalChainingMapEntry<>(28, "word2", entry2);
        expected[2] = entry3;

        map.put(2, "word");
        map.put(15, "word1");
        map.put(28, "word2");

        assertTrue(map.containsKey(28));
    }

    @Test(timeout = TIMEOUT)
    public void containsKeyAtMiddleOfLinkedList() {
        ExternalChainingMapEntry<Integer, String>[] expected = new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];

        ExternalChainingMapEntry<Integer, String> entry1 = new ExternalChainingMapEntry<>(2, "word", null);
        ExternalChainingMapEntry<Integer, String> entry2 = new ExternalChainingMapEntry<>(15, "word1", entry1);
        ExternalChainingMapEntry<Integer, String> entry3 = new ExternalChainingMapEntry<>(28, "word2", entry2);
        expected[2] = entry3;

        map.put(2, "word");
        map.put(15, "word1");
        map.put(28, "word2");

        assertTrue(map.containsKey(15));
    }

    @Test(timeout = TIMEOUT)
    public void containsKeyAtEndOfIndex() {
        ExternalChainingMapEntry<Integer, String>[] expected = new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];

        ExternalChainingMapEntry<Integer, String> entry1 = new ExternalChainingMapEntry<>(2, "word", null);
        ExternalChainingMapEntry<Integer, String> entry2 = new ExternalChainingMapEntry<>(15, "word1", entry1);
        ExternalChainingMapEntry<Integer, String> entry3 = new ExternalChainingMapEntry<>(28, "word2", entry2);
        expected[2] = entry3;

        map.put(2, "word");
        map.put(15, "word1");
        map.put(28, "word2");

        assertTrue(map.containsKey(2));
    }

    @Test(timeout = TIMEOUT)
    public void containsKeyNotInFilledIndex() {
        ExternalChainingMapEntry<Integer, String>[] expected = new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];

        ExternalChainingMapEntry<Integer, String> entry1 = new ExternalChainingMapEntry<>(2, "word", null);
        ExternalChainingMapEntry<Integer, String> entry2 = new ExternalChainingMapEntry<>(15, "word1", entry1);
        ExternalChainingMapEntry<Integer, String> entry3 = new ExternalChainingMapEntry<>(28, "word2", entry2);
        expected[2] = entry3;

        map.put(2, "word");
        map.put(15, "word1");
        map.put(28, "word2");

        assertFalse(map.containsKey(41));
    }

    //keySet Tests

    @Test(timeout = TIMEOUT)
    public void keySetOfNullMap() {
        Set<Integer> set = new HashSet<Integer>();
        assertEquals(set, map.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void keySetOfSpreadOutElements() {
        Set<Integer> set = new HashSet<Integer>();
        set.add(1);
        set.add(15);
        set.add(11);
        set.add(12);

        map.put(1, "word");
        map.put(15, "word1");
        map.put(11, "word2");
        map.put(12, "word3");

        assertEquals(set, map.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void keySetOfFilledIndices() {
        Set<Integer> set = new HashSet<Integer>();
        set.add(2);
        set.add(15);
        set.add(28);
        set.add(10);
        set.add(23);
        set.add(36);

        map.put(2, "word");
        map.put(15, "word1");
        map.put(28, "word2");

        map.put(10, "hello");
        map.put(23, "hello2");
        map.put(36, "hello3");

        assertEquals(set, map.keySet());
    }

    //values Tests
    @Test(timeout = TIMEOUT)
    public void valuesOfEmptyMap() {
        List<String> expected = new ArrayList<String>();
        assertEquals(expected, map.values());
    }

    @Test(timeout = TIMEOUT)
    public void valuesOfElementsAtFrontAndBackOfMap() {
        List<String> expected = new ArrayList<String>();
        expected.add("A");
        expected.add("B");
        expected.add("C");
        expected.add("D");

        map.put(1, "A");
        map.put(2, "B");
        map.put(10, "C");
        map.put(11, "D");

        assertEquals(expected, map.values());
    }

    @Test(timeout = TIMEOUT)
    public void valuesOfFilledIndices() {
        List<String> expected = new ArrayList<String>();
        expected.add("C");
        expected.add("B");
        expected.add("A");
        expected.add("F");
        expected.add("E");
        expected.add("D");

        map.put(2, "A");
        map.put(15, "B");
        map.put(28, "C");

        map.put(10, "D");
        map.put(23, "E");
        map.put(36, "F");

        assertEquals(expected.size(), map.values().size());
        assertEquals(expected, map.values());
    }

    //clear Tests
    @Test(timeout = TIMEOUT)
    public void clearEmptyMap() {
        ExternalChainingMapEntry<Integer, String>[] expected = new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];
        map.clear();

        assertEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void clearFilledMap() {
        ExternalChainingMapEntry<Integer, String>[] expected = new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];

        map.put(3, "A");
        map.put(8, "B");
        map.put(123, "C");
        map.put(99, "D");

        map.clear();
        assertEquals(0, map.size());
        assertEquals(expected, map.getTable());
    }
}
