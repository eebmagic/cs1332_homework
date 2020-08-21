import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayQueue.
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
public class ArrayQueue<T> {

    /*
     * The initial capacity of the ArrayQueue.
     *
     * DO NOT MODIFY THIS VARIABLE.
     */
    public static final int INITIAL_CAPACITY = 9;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int front;
    private int size;

    /**
     * Constructs a new ArrayQueue.
     */
    public ArrayQueue() {
        Object[] newArr = new Object[INITIAL_CAPACITY];
        backingArray = (T[]) newArr;
        front = 0;
        size = 0;
    }

    /**
     * method made by me for resizing
     */
    private void resize() {
        int oldSize = backingArray.length;
        Object[] newArr = new Object[oldSize * 2];
        for (int i = 0; i < oldSize; i++) {
            newArr[i] = dequeue();
        }
        size = oldSize;
        backingArray = (T[]) newArr;
        front = 0;
    }


    /**
     * Adds the data to the back of the queue.
     *
     * If sufficient space is not available in the backing array, resize it to
     * double the current length. When resizing, copy elements to the
     * beginning of the new array and reset front to 0.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the front of the queue
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("cannot add null data to the structure");
        }

        if (size == backingArray.length) {
            resize();
        }

        int backIndex = (size + front) % backingArray.length;
        backingArray[backIndex] = data;
        size += 1;
    }

    /**
     * Removes and returns the data from the front of the queue.
     *
     * Do not shrink the backing array.
     *
     * Replace any spots that you dequeue from with null.
     *
     * If the queue becomes empty as a result of this call, do not reset
     * front to 0.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("cannot dequeue from empty structure");
        }
        int index = (size - front) % backingArray.length;
        T oldData = backingArray[front];
        backingArray[front] = null;
        front = (front + 1) % backingArray.length;
        size -= 1;

        return oldData;
    }

    /**
     * Returns the data from the front of the queue without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("cannot peek at empty structure");
        }
        return backingArray[front];
    }

    /**
     * Returns the backing array of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the queue
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the queue
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
