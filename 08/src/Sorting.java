import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Ethan Bolton
 * @version 1.0
 * @userid ebolton8
 * @GTID 903368012
 *
 * Collaborators: None
 *
 * Resources: Google and all previous Java homeworks along with class notes
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("cannot pass null array or comparator object");
        }

        for (int n = 1; n < arr.length; n++) {
            int oldN = n;
            while (n > 0 && comparator.compare(arr[n], arr[n - 1]) < 0) {
                // execute swap
                T old = arr[n];
                arr[n] = arr[n - 1];
                arr[n - 1] = old;
                n--;
            }

            n = oldN;
        }

    }

    /**
     * Implement bubble sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for bubble sort. You
     * MUST implement bubble sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("cannot sort null data");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("cannot sort data with a null comparator");
        }

        int deepestIndex = arr.length - 1;
        int newDeepest = deepestIndex;
        while (deepestIndex >= 1) {
            for (int x = 0; x < deepestIndex; x++) {
                if (comparator.compare(arr[x], arr[x + 1]) > 0) {
                    // swap elements
                    T old = arr[x];
                    arr[x] = arr[x + 1];
                    arr[x + 1] = old;

                    newDeepest = x;
                }
            }
            if (deepestIndex != newDeepest) {
                // restart loop with new max marker
                deepestIndex = newDeepest;
            } else {
                // no changes were made, so can stop sorting
                break;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot pass null data or comparator to mergeSort");
        }

        arr = mergeHelp(arr, comparator);

    }

    /**
     * private helper method to make merge sort recursive
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @return the sorted version of the array
     */
    private static <T> T[] mergeHelp(T[] arr, Comparator<T> comparator) {
        if (arr.length <= 1) {
            return arr;
        } else {
            Object[] left = new Object[arr.length / 2];
            Object[] right = new Object[arr.length - left.length];

            int swap = arr.length / 2;
            for (int i = 0; i < arr.length; i++) {
                if (i < swap) {
                    left[i] = arr[i];
                } else {
                    right[i - left.length] = arr[i];
                }
            }

            left = mergeHelp((T[]) left, comparator);
            right = mergeHelp((T[]) right, comparator);
            int x = 0;
            int y = 0;
            for (int i = 0; i < arr.length; i++) {
                if (x >= left.length) {
                    arr[i] = (T) right[y];
                    y++;
                } else if (y >= right.length) {
                    arr[i] = (T) left[x];
                    x++;
                } else if (comparator.compare((T) left[x], (T) right[y]) <= 0) {
                    arr[i] = (T) left[x];
                    x++;
                } else {
                    arr[i] = (T) right[y];
                    y++;
                }
            }
            return arr;
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("cannot sort null data");
        } else if (arr.length == 0) {
            return;
        }

        // Find the highest magnitude number (to determine how many digits to attempt)
        int max = Math.abs(arr[0]);
        boolean negsPresent = false;
        for (int x: arr) {
            int abs = Math.abs(x);
            if (abs > max) {
                max = Math.abs(x);
            }
        }

        int iterations = digitCounter(max);
        LinkedList<Integer>[] buckets; // make empty buckets
        for (int i = 0; i < iterations; i++) {
            // reset buckets
            buckets = new LinkedList[19];

            for (int x = 0; x < buckets.length; x++) {
                buckets[x] = new LinkedList<>();
            }

            int factor = exponent(10, (i + 1));
            // Sort each int to a bucket
            for (int j = 0; j < arr.length; j++) {
                int curr = arr[j];
                int remain = (curr % factor) / (factor / 10);
                buckets[remain + 9].add(curr);
            }

            // empty buckets back into arr
            int counter = 0;
            for (LinkedList l: buckets) {
                while (l.size() != 0) {
                    arr[counter] = (int) l.remove();
                    counter++;
                }
            }
        }

    }

    /**
     * my private helper to make exponents (since Math.pow() doesn't appear to be allowed)
     * @param a the first num
     * @param b the num by which to raise the second number
     * @return the result of a raised to b
     */
    private static int exponent(int a, int b) {
        int out = 1;
        for (int i = 0; i < b; i++) {
            out *= a;
        }
        return out;
    }

    /**
     * my private helper method for finding the number of digits that make up a number
     * @param inputNumber the number to determine the number of digits in
     * @return the minimum number of digits needed to write inputNumber
     */
    private static int digitCounter(int inputNumber) {
        int counter = 0;
        while (inputNumber != 0) {
            inputNumber /= 10;
            counter++;
        }
        return counter;
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("cannot sort null data");
        }

        PriorityQueue<Integer> q = new PriorityQueue<>(data);
        int[] arr = new int[data.size()];

        for (int i = 0; i < data.size(); i++) {
            arr[i] = q.remove();
        }

        return arr;
    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator, Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("cannot sort null data");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("cannot sort with a null comparator");
        }
        if (rand == null) {
            throw new IllegalArgumentException("cannot use a null random object for sorting");
        }
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("k cannot be outside of range 1 to arr.length");
        }

        T returnValue = kHelp(k, arr, comparator, rand, 0, arr.length - 1);
        return returnValue;
    }

    /**
     * my private helper method for recursively doing kthSelect
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @param start      the start of the subarray to sort/search in
     * @param end        the end of the subarray to sort/search in
     * @return the kth smallest element
     */
    private static <T> T kHelp(int k, T[] arr, Comparator<T> comparator, Random rand, int start, int end) {
        if (start < end) {
            int pivotIndex = start + rand.nextInt(end - start + 1);

            // swap pivot value with start
            T tmp = arr[start];
            arr[start] = arr[pivotIndex];
            arr[pivotIndex] = tmp;

            int i = start + 1;
            int j = end;

            while (i <= j) {
                while (i <= j && comparator.compare(arr[i], arr[start]) < 0) {
                    i++;
                }
                while (j >= i && comparator.compare(arr[j], arr[start]) > 0) {
                    j--;
                }

                if (i <= j) {
                    tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                    i++;
                    j--;
                }
            }

            // swap at j and pivot (start) before recursion
            tmp = arr[start];
            arr[start] = arr[j];
            arr[j] = tmp;

            int targetIndex = k - 1;
            if (j > targetIndex) {
                // sort left
                return kHelp(k, arr, comparator, rand, start, j - 1);
            } else if (j < targetIndex) {
                // sort right
                return kHelp(k, arr, comparator, rand, j + 1, end);
            } else {
                T dummy = arr[targetIndex];
                return dummy;
            }
        } else {
            return arr[k - 1];
        }

    }
}
