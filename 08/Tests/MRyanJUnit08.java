import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.ArrayList;

/**
 * More tests for HW08 Sorting
 *
 * @author Michael Ryan
 * @version 1.0
 */
public class MRyanJUnit08 {

    // SWITCH THIS TO TRUE TO CHECK FOR AN EXACT MATCH OF EACH SWAP WITH MY IMPLEMENTATION
    //  I have this set to false by default because I think it might be possible that two implementations could have
    //  slight variances in the order of comparisons and still both be correct.
    private static final boolean TEST_EXACT_MATCH = false;

    private static final int TIMEOUT = 200;
    private Integer[] unsorted;
    private Integer[] presorted;
    private Integer[] reversed;
    private Integer[] sorted;
    private ComparatorHistory<Integer> comp;
    private static final String visURL = "http://xenonmolecule.github.io/sortvistool/?v=1.0&s=";

    @Before
    public void setUp() {
        unsorted = new Integer[10];
        unsorted[0] = 0;
        unsorted[1] = 3;
        unsorted[2] = 4;
        unsorted[3] = 6;
        unsorted[4] = 1;
        unsorted[5] = 8;
        unsorted[6] = 9;
        unsorted[7] = 5;
        unsorted[8] = 7;
        unsorted[9] = 2;

        presorted = new Integer[10];
        sorted = new Integer[10];
        reversed = new Integer[10];
        for (int i = 0; i < 10; i ++) {
            sorted[i] = i;
            presorted[i] = i;
            reversed[9-i] = i;
        }

        comp = new ComparatorHistory<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                addSnapshot(unsorted);
                addComparison(o1, o2);
                return o1 - o2;
            }
        };
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSort() {
        Sorting.insertionSort(unsorted, comp);
        System.out.println("Insertion: " + visURL + stringifySortHistory(comp));
        assertArrayEquals(sorted, unsorted);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 24 && comp.getCount() != 0);
        if (TEST_EXACT_MATCH) {
            assertArrayEquals(buildHistory("[3,0],[4,3],[6,4],[1,6],[1,4],[1,3],"
                            + "[1,0],[8,6],[9,8],[5,9],[5,8],[5,6],[5,4],[7,9],[7,8],[7,6],"
                            + "[2,9],[2,8],[2,7],[2,6],[2,5],[2,4],[2,3],[2,1]").toArray(),
                            comp.getHistory().toArray());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionPresorted() {
        unsorted = presorted;
        Sorting.insertionSort(unsorted, comp);
        System.out.println("Insertion Presorted: " + visURL + stringifySortHistory(comp));
        assertArrayEquals(sorted, unsorted);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 9 && comp.getCount() != 0);
        if (TEST_EXACT_MATCH) {
            assertArrayEquals(buildHistory("[1,0],[2,1],[3,2],[4,3],[5,4],[6,5],[7,6],[8,7],[9,8]").toArray(),
                    comp.getHistory().toArray());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionReversed() {
        unsorted = reversed;
        Sorting.insertionSort(unsorted, comp);
        System.out.println("Insertion Reversed: " + visURL + stringifySortHistory(comp));
        assertArrayEquals(sorted, unsorted);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 45 && comp.getCount() != 0);
        if (TEST_EXACT_MATCH) {
            assertArrayEquals(buildHistory("[8,9],[7,9],[7,8],[6,9],[6,8],[6,7],[5,9],[5,8],[5,7],[5,6],[4,9],[4,8]," +
                            "[4,7],[4,6],[4,5],[3,9],[3,8],[3,7],[3,6],[3,5],[3,4],[2,9],[2,8],[2,7],[2,6],[2,5]," +
                            "[2,4],[2,3],[1,9],[1,8],[1,7],[1,6],[1,5],[1,4],[1,3],[1,2],[0,9],[0,8],[0,7],[0,6]," +
                            "[0,5],[0,4],[0,3],[0,2],[0,1]").toArray(),
                    comp.getHistory().toArray());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testBubbleSort() {
        Sorting.bubbleSort(unsorted, comp);
        System.out.println("Bubble: " + visURL + stringifySortHistory(comp));
        assertArrayEquals(sorted, unsorted);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 44 && comp.getCount() != 0);
        if (TEST_EXACT_MATCH) {
            assertArrayEquals(buildHistory("[0,3],[3,4],[4,6],[6,1],[6,8],[8,9],[9,5],[9,7]"
                    + ",[9,2],[0,3],[3,4],[4,1],[4,6],[6,8],[8,5],[8,7],[8,2],[0,3],[3,1],[3,4],"
                    + "[4,6],[6,5],[6,7],[7,2],[0,1],[1,3],[3,4],[4,5],[5,6],[6,2],[0,1],[1,3],"
                    + "[3,4],[4,5],[5,2],[0,1],[1,3],[3,4],[4,2],[0,1],[1,3],[3,2],[0,1],[1,2]").toArray(),
                    comp.getHistory().toArray());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testBubbleSortPresorted() {
        unsorted = presorted;
        Sorting.bubbleSort(unsorted, comp);
        System.out.println("Bubble Presorted: " + visURL + stringifySortHistory(comp));
        assertArrayEquals(sorted, unsorted);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 9 && comp.getCount() != 0);
        if (TEST_EXACT_MATCH) {
            assertArrayEquals(buildHistory("[0,1],[1,2],[2,3],[3,4],[4,5],[5,6],[6,7],[7,8],[8,9]").toArray(),
                    comp.getHistory().toArray());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testBubbleSortReversed() {
        unsorted = reversed;
        Sorting.bubbleSort(unsorted, comp);
        System.out.println("Bubble Reversed: " + visURL + stringifySortHistory(comp));
        assertArrayEquals(sorted, unsorted);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 45 && comp.getCount() != 0);
        if (TEST_EXACT_MATCH) {
            assertArrayEquals(buildHistory("[9,8],[9,7],[9,6],[9,5],[9,4],[9,3],[9,2],[9,1],[9,0],[8,7],[8,6],[8,5]," +
                            "[8,4],[8,3],[8,2],[8,1],[8,0],[7,6],[7,5],[7,4],[7,3],[7,2],[7,1],[7,0],[6,5],[6,4]," +
                            "[6,3],[6,2],[6,1],[6,0],[5,4],[5,3],[5,2],[5,1],[5,0],[4,3],[4,2],[4,1],[4,0],[3,2]," +
                            "[3,1],[3,0],[2,1],[2,0],[1,0]").toArray(),
                    comp.getHistory().toArray());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSort() {
        Sorting.mergeSort(unsorted, comp);
        System.out.println("Merge: " + visURL + stringifySortHistory(comp));
        assertArrayEquals(sorted, unsorted);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 21 && comp.getCount() != 0);
        if (TEST_EXACT_MATCH) {
            assertArrayEquals(buildHistory("[0,3],[6,1],[4,1],[4,6],[0,1],[3,1],[3,4],[8,9],"
                    + "[7,2],[5,2],[5,7],[8,2],[8,5],[8,7],[0,2],[1,2],[3,2],[3,5],[4,5],[6,5],[6,7]").toArray(),
                    comp.getHistory().toArray());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortPresorted() {
        unsorted = presorted;
        Sorting.mergeSort(unsorted, comp);
        System.out.println("Merge Presorted: " + visURL + stringifySortHistory(comp));
        assertArrayEquals(sorted, unsorted);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 15 && comp.getCount() != 0);
        if (TEST_EXACT_MATCH) {
            assertArrayEquals(buildHistory("[0,1],[3,4],[2,3],[0,2],[1,2],[5,6],[8,9],[7,8],[5,7],[6,7]," +
                            "[0,5],[1,5],[2,5],[3,5],[4,5]").toArray(),
                    comp.getHistory().toArray());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortReversed() {
        unsorted = reversed;
        Sorting.mergeSort(unsorted, comp);
        System.out.println("Merge Reversed: " + visURL + stringifySortHistory(comp));
        assertArrayEquals(sorted, unsorted);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 19 && comp.getCount() != 0);
        if (TEST_EXACT_MATCH) {
            assertArrayEquals(buildHistory("[9,8],[6,5],[7,5],[7,6],[8,5],[8,6],[8,7],[4,3],[1,0],[2,0]," +
                            "[2,1],[3,0],[3,1],[3,2],[5,0],[5,1],[5,2],[5,3],[5,4]").toArray(),
                    comp.getHistory().toArray());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixSort() {
        int[] unsortedArray = new int[] {54, 28, 58, 84, 20, 122, -85, 3};
        int[] sortedArray = new int[] {-85, 3, 20, 28, 54, 58, 84, 122};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixSortExtremeValues() {
        int[] unsortedArray = new int[] {Integer.MAX_VALUE, 100, 1332, -1, -1332, 0, 1, -100, Integer.MIN_VALUE};
        int[] sortedArray = new int[] {Integer.MIN_VALUE, -1332, -100, -1, 0, 1, 100, 1332, Integer.MAX_VALUE};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void testHeapSort() {
        int[] unsortedArray = new int[] {54, 28, 58, 84, 20, 122, -85, 3};
        List<Integer> unsortedList = new ArrayList<>();
        for (int i : unsortedArray) {
            unsortedList.add(i);
        }
        int[] sortedArray = new int[] {-85, 3, 20, 28, 54, 58, 84, 122};
        int[] actualArray = Sorting.heapSort(unsortedList);
        assertArrayEquals(sortedArray, actualArray);
    }

    @Test(timeout = TIMEOUT)
    public void testHeapSortReversed() {
        int[] unsortedArray = new int[] {Integer.MAX_VALUE, 1332, 100, 1, 0, -1, -100, -1332, Integer.MIN_VALUE};
        List<Integer> unsortedList = new ArrayList<>();
        for (int i : unsortedArray) {
            unsortedList.add(i);
        }
        int[] sortedArray = new int[] {Integer.MIN_VALUE, -1332, -100, -1, 0, 1, 100, 1332, Integer.MAX_VALUE};
        int[] actualArray = Sorting.heapSort(unsortedList);
        assertArrayEquals(sortedArray, actualArray);
    }

    @Test(timeout = TIMEOUT)
    public void testKthSelect() {
        assertEquals(new Integer(2), Sorting.kthSelect(3,
                unsorted, comp, new Random(234)));
        System.out.println("Kth Select: " + visURL + stringifySortHistory(comp));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 17 && comp.getCount() != 0);
        if (TEST_EXACT_MATCH) {
            assertArrayEquals(buildHistory("[3,8],[4,8],[6,8],[1,8],[0,8],[9,8],"
                    + "[2,8],[5,8],[7,8],[3,2],[5,2],[7,2],[0,2],[4,2],[1,2],[6,2],[6,2]").toArray(),
                    comp.getHistory().toArray());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testKthSelectLastElement() {
        assertEquals(new Integer(9), Sorting.kthSelect(10,
                unsorted, comp, new Random(234)));
        System.out.println("Kth Select Last: " + visURL + stringifySortHistory(comp));
        assertTrue ("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 9 && comp.getCount() != 0);
        if (TEST_EXACT_MATCH) {
            assertArrayEquals(buildHistory("[3,8],[4,8],[6,8],[1,8],[0,8],[9,8],[2,8],[5,8],[7,8]").toArray(),
                    comp.getHistory().toArray());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testKthSelectFirstElement() {
        assertEquals(new Integer(0), Sorting.kthSelect(1,
                unsorted, comp, new Random(234)));
        System.out.println("Kth Select First: " + visURL + stringifySortHistory(comp));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 19 && comp.getCount() != 0);
        if (TEST_EXACT_MATCH) {
            assertArrayEquals(buildHistory("[3,8],[4,8],[6,8],[1,8],[0,8],[9,8],[2,8],[5,8],[7,8]," +
                            "[3,2],[5,2],[7,2],[0,2],[4,2],[1,2],[6,2],[6,2],[1,0],[1,0]").toArray(),
                    comp.getHistory().toArray());
        }
    }

    private static class Comparison<T> {
        private T first;
        private T second;

        public Comparison (T first, T second) {
            this.first = first;
            this.second = second;
        }

        public T getFirst() {
            return first;
        }

        public T getSecond() {
            return second;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }
            if (!(other instanceof Comparison)) {
                return false;
            }
            if (this == other) {
                return true;
            }
            Comparison<T> o = (Comparison<T>) other;
            if (this.first.equals(o.getFirst())) {
                return this.second.equals(o.getSecond());
            }
            if (this.first.equals(o.getSecond())) {
                return this.second.equals(o.getFirst());
            }
            return false;
        }

        @Override
        public String toString() {
            return "[" + first.toString() + "," + second.toString() + "]";
        }
    }

    /**
     * Inner class that stores history of comparisons made
     */
    private abstract static class ComparatorHistory<T> implements Comparator<T> {
        private ArrayList<Comparison<T>> compHistory = new ArrayList<>();
        private ArrayList<T[]> snapshots = new ArrayList<>();

        /**
         * Get comparison history
         *
         * @return the history of comparisons made
         */
        public ArrayList<Comparison<T>> getHistory() {
            return compHistory;
        }

        public ArrayList<T[]> getSnapshots() { return snapshots; }

        /**
         * Get number of comparisons made
         *
         * @return the number of comparisons made
         */
        public int getCount() {
            return compHistory.size();
        }

        /**
         * Add to the history of comparisons made. Call this method in
         * your compare() implementation.
         *
         * @param first the first item being compared
         * @param second the second item being compared
         */
        public void addComparison(T first, T second) {
            compHistory.add(new Comparison<>(first, second));
        }

        /**
         * Add to the history of the unsorted arrays.
         * Call this method in your compare() implementation
         *
         * @param curr the current state of the array to sort
         */
        public void addSnapshot(T[] curr) {
            snapshots.add(Arrays.copyOf(curr, curr.length));
        }
    }

    /**
     * Build a history array list from a stringified version of the comparisons
     *
     * @param history the stringified version of the comparison history
     * @return an array list of comparisons
     */
    public ArrayList<Comparison<Integer>> buildHistory(String history) {
        ArrayList<Comparison<Integer>> hist = new ArrayList<>();
        String[] comps = history.split(",");
        if (comps.length % 2 != 0) {
            throw new IllegalArgumentException("Provided history string did not have valid number of commas");
        }
        for(int i = 0; i < comps.length; i+=2) {
            int first = Integer.parseInt(comps[i].substring(1));
            int second = Integer.parseInt(comps[i+1].substring(0, comps[i+1].length()-1));
            hist.add(new Comparison<>(first, second));
        }
        return hist;
    }

    public String stringifyCompHistory(ArrayList<Comparison<Integer>> history) {
        String output = "";
        for (Comparison c : history) {
            output += c.toString() + ',';
        }
        return output.substring(0, output.length() - 1);
    }

    public String stringifySortHistory(ComparatorHistory<Integer> comp) {
        String output = "";
        for(int i = 0; i < comp.getCount(); i ++) {
            for (int num : comp.getSnapshots().get(i)) {
                output += num;
            }
            output += comp.getHistory().get(i).getFirst();
            output += comp.getHistory().get(i).getSecond();
        }
        for (int num : unsorted) {
            output += num;
        }
        return output;
    }

}