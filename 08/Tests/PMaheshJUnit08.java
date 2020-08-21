import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Pranav Mahesh's JUnit tests for HW8 - Sorting.
 * Comprehensive tests on all the methods on the HW.
 * Let me know if you have questions or if anything doesn't work by replying to my Piazza post!
 *
 * @author Pranav Mahesh, TeachingAssistant by CS 1332 TAs
 * @version 1.0 (updated 3/24/20)
 */
public class PMaheshJUnit08 {

    private static final int TIMEOUT = 200;
    private TeachingAssistant[] tas;
    private TeachingAssistant[] tasByName;
    private ComparatorPlus<TeachingAssistant> comp;

    @Before
    public void setUp() {
        /*
            Unsorted Names:
                index 0: Alex
                index 1: Ivan
                index 2: Miguel
                index 3: Paige
                index 4: Brooke
                index 5: Sanjana
                index 6: Yotam
                index 7: Nick
                index 8: Reece
                index 9: Destini
         */

        /*
            Sorted Names:
                index 0: Alex
                index 1: Brooke
                index 2: Destini
                index 3: Ivan
                index 4: Miguel
                index 5: Nick
                index 6: Paige
                index 7: Reece
                index 8: Sanjana
                index 9: Yotam
         */

        tas = new TeachingAssistant[10];
        tas[0] = new TeachingAssistant("Alex");
        tas[1] = new TeachingAssistant("Ivan");
        tas[2] = new TeachingAssistant("Miguel");
        tas[3] = new TeachingAssistant("Paige");
        tas[4] = new TeachingAssistant("Brooke");
        tas[5] = new TeachingAssistant("Sanjana");
        tas[6] = new TeachingAssistant("Yotam");
        tas[7] = new TeachingAssistant("Nick");
        tas[8] = new TeachingAssistant("Reece");
        tas[9] = new TeachingAssistant("Destini");
        tasByName = new TeachingAssistant[10];
        tasByName[0] = tas[0];
        tasByName[1] = tas[4];
        tasByName[2] = tas[9];
        tasByName[3] = tas[1];
        tasByName[4] = tas[2];
        tasByName[5] = tas[7];
        tasByName[6] = tas[3];
        tasByName[7] = tas[8];
        tasByName[8] = tas[5];
        tasByName[9] = tas[6];

        comp = TeachingAssistant.getNameComparator();
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testInsertionSortNullArray() {
        Sorting.insertionSort(null, comp);
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testInsertionSortNullComp() {
        Sorting.insertionSort(tas, null);
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSortSorted() {
        TeachingAssistant[] sorted = new TeachingAssistant[10];
        sorted[0] = new TeachingAssistant("Alex");
        sorted[1] = new TeachingAssistant("Brooke");
        sorted[2] = new TeachingAssistant("Destini");
        sorted[3] = new TeachingAssistant("Ivan");
        sorted[4] = new TeachingAssistant("Miguel");
        sorted[5] = new TeachingAssistant("Nick");
        sorted[6] = new TeachingAssistant("Paige");
        sorted[7] = new TeachingAssistant("Reece");
        sorted[8] = new TeachingAssistant("Sanjana");
        sorted[9] = new TeachingAssistant("Yotam");
        Sorting.insertionSort(sorted, comp);
        assertArrayEquals(tasByName, sorted);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 9 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSortSizeOne() {
        TeachingAssistant[] sorted = new TeachingAssistant[1];
        sorted[0] = new TeachingAssistant("Alex");

        TeachingAssistant[] expected = new TeachingAssistant[1];
        expected[0] = new TeachingAssistant("Alex");

        Sorting.insertionSort(sorted, comp);
        assertArrayEquals(expected, sorted);

        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() == 0);
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSortDuplicates() {
        TeachingAssistant[] tas = new TeachingAssistant[13];
        tas[0] = new TeachingAssistant("Brooke");
        tas[1] = new TeachingAssistant("Ivan");
        tas[2] = new TeachingAssistant("Nick");
        tas[3] = new TeachingAssistant("Ivan");
        tas[4] = new TeachingAssistant("Miguel");
        tas[5] = new TeachingAssistant("Paige");
        tas[6] = new TeachingAssistant("Alex");
        tas[7] = new TeachingAssistant("Nick");
        tas[8] = new TeachingAssistant("Sanjana");
        tas[9] = new TeachingAssistant("Destini");
        tas[10] = new TeachingAssistant("Reece");
        tas[11] = new TeachingAssistant("Yotam");
        tas[12] = new TeachingAssistant("Reece");

        TeachingAssistant[] sorted = new TeachingAssistant[13];
        sorted[0] = new TeachingAssistant("Alex");
        sorted[1] = new TeachingAssistant("Brooke");
        sorted[2] = new TeachingAssistant("Destini");
        sorted[3] = new TeachingAssistant("Ivan");
        sorted[4] = new TeachingAssistant("Ivan");
        sorted[5] = new TeachingAssistant("Miguel");
        sorted[6] = new TeachingAssistant("Nick");
        sorted[7] = new TeachingAssistant("Nick");
        sorted[8] = new TeachingAssistant("Paige");
        sorted[9] = new TeachingAssistant("Reece");
        sorted[10] = new TeachingAssistant("Reece");
        sorted[11] = new TeachingAssistant("Sanjana");
        sorted[12] = new TeachingAssistant("Yotam");
        Sorting.insertionSort(tas, comp);
        assertArrayEquals(tas, sorted);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 30 && comp.getCount() != 0);
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testBubbleSortNullArray() {
        Sorting.bubbleSort(null, comp);
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testBubbleSortNullComp() {
        Sorting.bubbleSort(tas, null);
    }

    @Test(timeout = TIMEOUT)
    public void testBubbleSortSorted() {
        TeachingAssistant[] sorted = new TeachingAssistant[10];
        sorted[0] = new TeachingAssistant("Alex");
        sorted[1] = new TeachingAssistant("Brooke");
        sorted[2] = new TeachingAssistant("Destini");
        sorted[3] = new TeachingAssistant("Ivan");
        sorted[4] = new TeachingAssistant("Miguel");
        sorted[5] = new TeachingAssistant("Nick");
        sorted[6] = new TeachingAssistant("Paige");
        sorted[7] = new TeachingAssistant("Reece");
        sorted[8] = new TeachingAssistant("Sanjana");
        sorted[9] = new TeachingAssistant("Yotam");
        Sorting.bubbleSort(sorted, comp);
        assertArrayEquals(tasByName, sorted);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 9 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testBubbleSortSizeOne() {
        TeachingAssistant[] sorted = new TeachingAssistant[1];
        sorted[0] = new TeachingAssistant("Alex");

        TeachingAssistant[] expected = new TeachingAssistant[1];
        expected[0] = new TeachingAssistant("Alex");

        Sorting.bubbleSort(sorted, comp);
        assertArrayEquals(expected, sorted);

        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() == 0);
    }

    @Test(timeout = TIMEOUT)
    public void testBubbleSortDuplicates() {
        TeachingAssistant[] tas = new TeachingAssistant[13];
        tas[0] = new TeachingAssistant("Brooke");
        tas[1] = new TeachingAssistant("Ivan");
        tas[2] = new TeachingAssistant("Nick");
        tas[3] = new TeachingAssistant("Ivan");
        tas[4] = new TeachingAssistant("Miguel");
        tas[5] = new TeachingAssistant("Paige");
        tas[6] = new TeachingAssistant("Alex");
        tas[7] = new TeachingAssistant("Nick");
        tas[8] = new TeachingAssistant("Sanjana");
        tas[9] = new TeachingAssistant("Destini");
        tas[10] = new TeachingAssistant("Reece");
        tas[11] = new TeachingAssistant("Yotam");
        tas[12] = new TeachingAssistant("Reece");

        TeachingAssistant[] sorted = new TeachingAssistant[13];
        sorted[0] = new TeachingAssistant("Alex");
        sorted[1] = new TeachingAssistant("Brooke");
        sorted[2] = new TeachingAssistant("Destini");
        sorted[3] = new TeachingAssistant("Ivan");
        sorted[4] = new TeachingAssistant("Ivan");
        sorted[5] = new TeachingAssistant("Miguel");
        sorted[6] = new TeachingAssistant("Nick");
        sorted[7] = new TeachingAssistant("Nick");
        sorted[8] = new TeachingAssistant("Paige");
        sorted[9] = new TeachingAssistant("Reece");
        sorted[10] = new TeachingAssistant("Reece");
        sorted[11] = new TeachingAssistant("Sanjana");
        sorted[12] = new TeachingAssistant("Yotam");
        Sorting.bubbleSort(tas, comp);
        assertArrayEquals(tas, sorted);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 53 && comp.getCount() != 0);
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testMergeSortNullArray() {
        Sorting.mergeSort(null, comp);
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testMergeSortNullComp() {
        Sorting.mergeSort(tas, null);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortSizeZero() {
        TeachingAssistant[] sizeZero = new TeachingAssistant[0];
        Sorting.mergeSort(sizeZero, comp);

        TeachingAssistant[] expected = new TeachingAssistant[0];
        assertArrayEquals(sizeZero, expected);
        assertTrue("Number of comparisons: " + comp.getCount(), comp.getCount() == 0);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortSizeOne() {
        TeachingAssistant[] sizeOne = new TeachingAssistant[1];
        sizeOne[0] = new TeachingAssistant("Alex");
        Sorting.mergeSort(sizeOne, comp);

        TeachingAssistant[] expected = new TeachingAssistant[1];
        expected[0] = new TeachingAssistant("Alex");
        assertArrayEquals(sizeOne, expected);
        assertTrue("Number of comparisons: " + comp.getCount(), comp.getCount() == 0);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortSizeTwoUnsorted() {
        TeachingAssistant[] unsorted = new TeachingAssistant[2];
        unsorted[0] = new TeachingAssistant("Brooke");
        unsorted[1] = new TeachingAssistant("Alex");

        Sorting.mergeSort(unsorted, comp);

        TeachingAssistant[] expected = new TeachingAssistant[2];
        expected[0] = new TeachingAssistant("Alex");
        expected[1] = new TeachingAssistant("Brooke");

        assertArrayEquals(unsorted, expected);
        assertTrue("Number of comparisons: " + comp.getCount(), comp.getCount() <= 1);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortSizeTwoSorted() {
        TeachingAssistant[] unsorted = new TeachingAssistant[2];
        unsorted[0] = new TeachingAssistant("Alex");
        unsorted[1] = new TeachingAssistant("Brooke");

        Sorting.mergeSort(unsorted, comp);

        TeachingAssistant[] expected = new TeachingAssistant[2];
        expected[0] = new TeachingAssistant("Alex");
        expected[1] = new TeachingAssistant("Brooke");

        assertArrayEquals(unsorted, expected);
        assertTrue("Number of comparisons: " + comp.getCount(), comp.getCount() <= 1);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortDuplicates() {
        TeachingAssistant[] tas = new TeachingAssistant[13];
        tas[0] = new TeachingAssistant("Brooke");
        tas[1] = new TeachingAssistant("Ivan");
        tas[2] = new TeachingAssistant("Nick");
        tas[3] = new TeachingAssistant("Ivan");
        tas[4] = new TeachingAssistant("Miguel");
        tas[5] = new TeachingAssistant("Paige");
        tas[6] = new TeachingAssistant("Alex");
        tas[7] = new TeachingAssistant("Nick");
        tas[8] = new TeachingAssistant("Sanjana");
        tas[9] = new TeachingAssistant("Destini");
        tas[10] = new TeachingAssistant("Reece");
        tas[11] = new TeachingAssistant("Yotam");
        tas[12] = new TeachingAssistant("Reece");

        TeachingAssistant[] sorted = new TeachingAssistant[13];
        sorted[0] = new TeachingAssistant("Alex");
        sorted[1] = new TeachingAssistant("Brooke");
        sorted[2] = new TeachingAssistant("Destini");
        sorted[3] = new TeachingAssistant("Ivan");
        sorted[4] = new TeachingAssistant("Ivan");
        sorted[5] = new TeachingAssistant("Miguel");
        sorted[6] = new TeachingAssistant("Nick");
        sorted[7] = new TeachingAssistant("Nick");
        sorted[8] = new TeachingAssistant("Paige");
        sorted[9] = new TeachingAssistant("Reece");
        sorted[10] = new TeachingAssistant("Reece");
        sorted[11] = new TeachingAssistant("Sanjana");
        sorted[12] = new TeachingAssistant("Yotam");

        Sorting.mergeSort(tas, comp);
        assertArrayEquals(tas, sorted);
        assertTrue("Number of comparisons: " + comp.getCount(), comp.getCount() <= 30);
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testLsdRadixSortNullArray() {
        Sorting.lsdRadixSort(null);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixMinValueNeg() {
        int[] unsortedArray = new int[] {-567, -35, -300, Integer.MIN_VALUE, -2};
        int[] sortedArray = new int[] {Integer.MIN_VALUE, -567, -300, -35, -2};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixMinValuePos() {
        int[] unsortedArray = new int[] {300, 500, 64, 25, Integer.MIN_VALUE};
        int[] sortedArray = new int[] {Integer.MIN_VALUE, 25, 64, 300, 500};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixMinValueMix() {
        int[] unsortedArray = new int[] {-567, 25, 250, -300, Integer.MIN_VALUE, -2};
        int[] sortedArray = new int[] {Integer.MIN_VALUE, -567, -300, -2, 25, 250};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixMaxValueNeg() {
        int[] unsortedArray = new int[] {-567, 25, 250, -300, Integer.MAX_VALUE, -2};
        int[] sortedArray = new int[] {-567, -300, -2, 25, 250, Integer.MAX_VALUE};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixMaxValuePos() {
        int[] unsortedArray = new int[] {300, 500, 64, Integer.MAX_VALUE, 25};
        int[] sortedArray = new int[] {25, 64, 300, 500, Integer.MAX_VALUE};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixMaxValueMix() {
        int[] unsortedArray = new int[] {-567, 25, 250, -300, Integer.MAX_VALUE, -2};
        int[] sortedArray = new int[] {-567, -300, -2, 25, 250, Integer.MAX_VALUE};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixMinMaxValue() {
        int[] unsortedArray = new int[] {-567, 25, 250, -300, Integer.MIN_VALUE, Integer.MAX_VALUE, -2};
        int[] sortedArray = new int[] {Integer.MIN_VALUE, -567, -300, -2, 25, 250, Integer.MAX_VALUE};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixMinValue2() {
        int[] unsortedArray = new int[] {0, 500, Integer.MIN_VALUE + 1, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE - 1, Integer.MIN_VALUE + 2};
        int[] sortedArray = new int[] {Integer.MIN_VALUE, Integer.MIN_VALUE + 1, Integer.MIN_VALUE + 2, 0, 500, Integer.MAX_VALUE - 1, Integer.MAX_VALUE};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixSorted() {
        int[] unsortedArray = new int[] {123, -1239, 29, -500, 5, 586, -25};
        int[] sortedArray = new int[] {-1239, -500, -25, 5, 29, 123, 586};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testHeapSortNullData() {
        Sorting.heapSort(null);
    }

    @Test(timeout = TIMEOUT)
    public void testHeapSortNum() {
        int[] unsortedArray = new int[] {524, 258, 125, 82, 53, 1135, -1345, 4};
        List<Integer> unsortedList = new ArrayList<>();
        for (int i : unsortedArray) {
            unsortedList.add(i);
        }
        int[] sortedArray = new int[] {-1345, 4, 53, 82, 125, 258, 524, 1135};
        int[] actualArray = Sorting.heapSort(unsortedList);
        assertArrayEquals(sortedArray, actualArray);
    }

    @Test(timeout = TIMEOUT)
    public void testHeapSortMinMax() {
        int[] unsortedArray = new int[] {524, Integer.MIN_VALUE, 125, 82, Integer.MAX_VALUE, 1135, -1345, 4};
        List<Integer> unsortedList = new ArrayList<>();
        for (int i : unsortedArray) {
            unsortedList.add(i);
        }
        int[] sortedArray = new int[] {Integer.MIN_VALUE, -1345, 4, 82, 125, 524, 1135, Integer.MAX_VALUE};
        int[] actualArray = Sorting.heapSort(unsortedList);
        assertArrayEquals(sortedArray, actualArray);
    }

    @Test(timeout = TIMEOUT)
    public void testHeapSortReversed() {
        int[] unsortedArray = new int[] {Integer.MAX_VALUE, 956, 250, 124, 0, -250, -1345, Integer.MIN_VALUE};
        List<Integer> unsortedList = new ArrayList<>();
        for (int i : unsortedArray) {
            unsortedList.add(i);
        }
        int[] sortedArray = new int[] {Integer.MIN_VALUE, -1345, -250, 0, 124, 250, 956, Integer.MAX_VALUE};
        int[] actualArray = Sorting.heapSort(unsortedList);
        assertArrayEquals(sortedArray, actualArray);
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testKthSelectNullArray() {
        Sorting.kthSelect(1, null, comp, new Random(234));
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testKthSelectNullComp() {
        Sorting.kthSelect(1, tas, null, new Random(234));
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testKthSelectNullRand() {
        Sorting.kthSelect(1, tas, comp, null);
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testKthSelectKNotInRange() {
        Sorting.kthSelect(0, tas, comp, new Random(234));
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testKthSelectKNotInRange2() {
        Sorting.kthSelect(11, tas, comp, new Random(234));
    }

    @Test(timeout = TIMEOUT)
    public void testKthSelectSmall() {
        assertEquals(new TeachingAssistant("Alex"), Sorting.kthSelect(1,
                tas, comp, new Random(234)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 19 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testKthSelectLarge() {
        assertEquals(new TeachingAssistant("Yotam"), Sorting.kthSelect(10,
                tas, comp, new Random(234)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 9 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testKthSelectSmallRand() {
        assertEquals(new TeachingAssistant("Alex"), Sorting.kthSelect(1,
                tas, comp, new Random(3215)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 15 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testKthSelectLargeRand() {
        assertEquals(new TeachingAssistant("Yotam"), Sorting.kthSelect(10,
                tas, comp, new Random(3215)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 18 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testKthSelectEverythingDiffRand() {
        assertEquals(new TeachingAssistant("Alex"), Sorting.kthSelect(1,
                tas, comp, new Random(234)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 19 && comp.getCount() != 0);

        assertEquals(new TeachingAssistant("Brooke"), Sorting.kthSelect(2,
                tas, comp, new Random(234)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 32 && comp.getCount() != 0);

        assertEquals(new TeachingAssistant("Destini"), Sorting.kthSelect(3,
                tas, comp, new Random(234)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 47 && comp.getCount() != 0);

        assertEquals(new TeachingAssistant("Ivan"), Sorting.kthSelect(4,
                tas, comp, new Random(234)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 65 && comp.getCount() != 0);

        assertEquals(new TeachingAssistant("Miguel"), Sorting.kthSelect(5,
                tas, comp, new Random(234)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 83 && comp.getCount() != 0);

        assertEquals(new TeachingAssistant("Nick"), Sorting.kthSelect(6,
                tas, comp, new Random(2374)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 113 && comp.getCount() != 0);

        assertEquals(new TeachingAssistant("Paige"), Sorting.kthSelect(7,
                tas, comp, new Random(2374)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 143 && comp.getCount() != 0);

        assertEquals(new TeachingAssistant("Reece"), Sorting.kthSelect(8,
                tas, comp, new Random(2374)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 173 && comp.getCount() != 0);

        assertEquals(new TeachingAssistant("Sanjana"), Sorting.kthSelect(9,
                tas, comp, new Random(2374)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 200 && comp.getCount() != 0);

        assertEquals(new TeachingAssistant("Yotam"), Sorting.kthSelect(10,
                tas, comp, new Random(21)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 210 && comp.getCount() != 0);
    }

    /**
     * Class for testing proper sorting.
     */
    private static class TeachingAssistant {
        private String name;

        /**
         * Create a teaching assistant.
         *
         * @param name name of TA
         */
        public TeachingAssistant(String name) {
            this.name = name;
        }

        /**
         * Get the name of the teaching assistant.
         *
         * @return name of teaching assistant
         */
        public String getName() {
            return name;
        }

        /**
         * Create a comparator that compares the names of the teaching
         * assistants.
         *
         * @return comparator that compares the names of the teaching assistants
         */
        public static ComparatorPlus<TeachingAssistant> getNameComparator() {
            return new ComparatorPlus<TeachingAssistant>() {
                @Override
                public int compare(TeachingAssistant ta1,
                                   TeachingAssistant ta2) {
                    incrementCount();
                    return ta1.getName().compareTo(ta2.getName());
                }
            };
        }

        @Override
        public String toString() {
            return "Name: " + name;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }
            if (this == other) {
                return true;
            }
            return other instanceof TeachingAssistant
                    && ((TeachingAssistant) other).name.equals(this.name);
        }
    }

    /**
     * Inner class that allows counting how many comparisons were made.
     */
    private abstract static class ComparatorPlus<T> implements Comparator<T> {
        private int count;

        /**
         * Get the number of comparisons made.
         *
         * @return number of comparisons made
         */
        public int getCount() {
            return count;
        }

        /**
         * Increment the number of comparisons made by one. Call this method in
         * your compare() implementation.
         */
        public void incrementCount() {
            count++;
        }
    }
}