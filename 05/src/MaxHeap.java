import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MaxHeap.
 *
 * @author Ethan Bolton
 * @version 1.0
 * @userid ebolton8
 * @GTID 903368012
 *
 * Collaborators: myself
 *
 * Resources: google, and previous java homework
 */
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MaxHeap() {
        size = 0;
        Comparable[] newArr = new Comparable[INITIAL_CAPACITY];
        backingArray = (T[]) newArr;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("cannot add null data to the structure");
        }

        size = 0;
        Comparable[] newArr = new Comparable[data.size() * 2 + 1];
        backingArray = (T[]) newArr;

        // build straight array
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("cannot add null data to the structure");
            }
            if (i == backingArray.length - 1) {
                resize();
            }
            backingArray[i + 1] = data.get(i);
            size += 1;
        }

        // start with first parent node and then downheap moving up
        for (int i = size / 2; i > 0; i--) {
            downheap(i);
        }
    }

    /**
     * helper method that resizes backing array when necessary
     */
    private void resize() {
        Comparable[] newArr = new Comparable[(backingArray.length * 2)];
        int index = 1;
        for (T x: backingArray) {
            if (x != null) {
                newArr[index] = x;
                index += 1;
            }
        }
        backingArray = (T[]) newArr;
    }

    /**
     * Adds the data to the heap.
     *
     * If sufficient space is not available in the backing array (the backing
     * array is full except for index 0), resize it to double the current
     * length.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("cannot add null to the data structure");
        }

        if ((size + 1) == backingArray.length) {
            resize();
        }

        // add to back first
        size += 1;
        backingArray[size] = data;

        // start with first parent node and then downheap moving up
        for (int i = size / 2; i > 0; i--) {
            downheap(i);
        }
        
    }

    /**
     * private helper method for finding max child node
     * @param workingIndex the index of the node in the array
     * @return the value of the max node values
     */
    private int getMaxChildIndex(int workingIndex) {
        if (workingIndex * 2 + 1 > size) {
            return workingIndex * 2;
        } else {
            T one = backingArray[workingIndex * 2];
            T two = backingArray[workingIndex * 2 + 1];

            if (one.compareTo(two) > 0) {
                return workingIndex * 2;
            } else {
                return workingIndex * 2 + 1;
            }
        }
    }

    /**
     * private method for recursively downheaping
     * @param index the index for the node ot downheap from
     */
    private void downheap(int index) {
        if (index * 2 > size) {
            return;
        } else {
            int maxIndex = getMaxChildIndex(index);
            T maxValue = backingArray[maxIndex];

            if (backingArray[index].compareTo(maxValue) > 0) {
                return;
            } else {
                if (backingArray[index].compareTo(maxValue) < 0) {
                    T oldData = backingArray[index];
                    backingArray[index] = maxValue;
                    backingArray[maxIndex] = oldData;

                    downheap(maxIndex);
                }
            }
        }
    }


    /**
     * Removes and returns the root of the heap.
     *
     * Do not shrink the backing array.
     *
     * Replace any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("can't remove from heap because heap is empty");
        }

        T oldMax = backingArray[1];

        if (size == 1) {
            clear();

        } else {
            // move back node to root position
            backingArray[1] = backingArray[size];
            backingArray[size] = null;
            size -= 1;
            downheap(1);
        }

        return oldMax;
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {
        if (size == 0) {
            throw new NoSuchElementException("can't get max of heap because heap is empty");
        }

        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        Comparable[] newArr = new Comparable[INITIAL_CAPACITY];
        backingArray = (T[]) newArr;
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
