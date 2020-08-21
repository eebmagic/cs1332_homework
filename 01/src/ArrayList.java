/**
 * Your implementation of an ArrayList.
 *
 * @author Ethan Bolton
 * @version 1.0
 * @userid ebolton8
 * @GTID 903368012
 *
 * Collaborators: I worked with myself.
 *
 * Resources: Previous 1331 homeworks and Piazza
 */
public class ArrayList<T> {

    /*
     * The initial capacity of the ArrayList.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 9;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayList() {
        Object[] arr = new Object[INITIAL_CAPACITY];
        backingArray = (T[]) arr;
        size = 0;
    }

    /**
     * Adds the data to the specified index.
     *
     * This add may require elements to be shifted.
     *
     * If sufficient space is not available in the backing array, resize it to
     * double the current length.
     *
     * Must be amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(String.format("index cannot be <0 or greater than size: %d", size));
        }
        if (data == null) {
            throw new IllegalArgumentException("cannot add null value to ArrayList");
        }

        if (size == backingArray.length) {
            // make new sized empty array and then add
            Object[] newArr = new Object[size * 2];
            for (int i = 0; i <= size; i++) {
                if (i < index) {
                    // add index before new insertion
                    newArr[i] = backingArray[i];
                } else if (i == index) {
                    // add new insertion
                    newArr[i] = data;
                } else if (i > index) {
                    // add points from old array that come after new insertion
                    newArr[i] = backingArray[i - 1];
                }
            }
            backingArray = (T[]) newArr;
        } else {
            if (size == index) {
                // add data to back
                backingArray[index] = data;
            } else {
                // move points back one index and then insert data
                for (int i = size; i > index; i--) {
                    backingArray[i] = backingArray[i - 1];
                }
                backingArray[index] = data;
            }    
        }
        size += 1;

    }

    /**
     * Adds the data to the front of the list.
     *
     * This add may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("cannot add null value to ArrayList");
        }
        addAtIndex(0, data);

    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("cannot add null value to ArrayList");
        }
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Do not shrink the backing array.
     *
     * This remove may require elements to be shifted.
     *
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            String notification = String.format("The given index %d is less than 0 or greater than %d", index, size);
            throw new IndexOutOfBoundsException(notification);
        }

        // Hold data at index to be removed for returning later
        T oldValue = (T) backingArray[index];

        for (int i = index + 1; i < size; i++) {
            backingArray[i - 1] = backingArray[i];
        }

        // empty the end of the array because one less data point now
        backingArray[size - 1] = null;

        // reduce size and return the removed value
        size -= 1;
        return oldValue;
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Do not shrink the backing array.
     *
     * This remove may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Do not shrink the backing array.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the data at the specified index.
     *
     * Must be O(1).
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        return backingArray[index];
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return backingArray[0] == null;
    }

    /**
     * Clears the list.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        Object[] arr = new Object[INITIAL_CAPACITY];
        backingArray = (T[]) arr;
        size = 0;
    }

    /**
     * Returns the backing array of the list.
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
     * Returns the size of the list.
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

    /**
     * My personal method used for printing out the contents of the list and size
     * Used for debugging purposes
     */
    private void printThrough() {
        System.out.print("[");
        for (int i = 0; i < backingArray.length; i++) {
            T value = backingArray[i];
            if (value == null) {
                System.out.print("null");
            } else {
                System.out.print(value);
            }

            if (i != backingArray.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.printf("] - %d\n", size());
    }
}
