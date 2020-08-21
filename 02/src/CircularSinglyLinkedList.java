import java.util.NoSuchElementException;
/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
 *
 * @author Ethan Bolton
 * @version 1.0
 * @userid ebolton8
 * @GTID 903368012
 *
 * Collaborators: myself
 *
 * Resources: google, piazza, and previous 1331 homework
 */
public class CircularSinglyLinkedList<T> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("cannot add at index less than zero");
        } else if (index > size) {
            String message = String.format("cannot add at index greater than size (current size: %d)", size);
            throw new IndexOutOfBoundsException(message);
        }
        if (data == null) {
            throw new IllegalArgumentException("cannot add null data to the structure");
        }

        if (size == 0) {
            // adding to front of empty list
            head = new CircularSinglyLinkedListNode(data);
            head.setNext(head);
        } else if (index == 0) {
            // add to front
            head.setNext(new CircularSinglyLinkedListNode<T>(head.getData(), head.getNext()));
            head.setData(data);
        } else if (index == size) {
            // add to back
            head.setNext(new CircularSinglyLinkedListNode<T>(head.getData(), head.getNext()));
            head.setData(data);
            head = head.getNext();
        } else {
            // O(n) method for adding at any index
            CircularSinglyLinkedListNode<T> n = head;
            for (int i = 0; i < index; i++) {
                if (i == index - 1) {
                    n.setNext(new CircularSinglyLinkedListNode<T>(data, n.getNext()));
                }
                n = n.getNext();
            }
        }

        size += 1;
    }

    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("cannot add at index less than zero");
        } else if (index > size) {
            String message = String.format("cannot add at index greater than size (current size: %d)", size);
            throw new IndexOutOfBoundsException(message);
        }

        T oldData = null;
        if (index == 0) {
            oldData = (T) head.getData();
            head.setData(head.getNext().getData());
            head.setNext(head.getNext().getNext());
        } else {
            CircularSinglyLinkedListNode n = head;
            for (int i = 1; i <= index; i++) {
                if (i == index) {
                    oldData = (T) n.getNext().getData();
                    n.setNext(n.getNext().getNext());
                }
                n = n.getNext();
            }
        }
        size -= 1;
        return oldData;
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("cannot remove because structure is emtpy");
        }
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("cannot remove because structure is emtpy");
        }
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("cannot get at index less than 0");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("cannot get at index greater than size");
        }

        CircularSinglyLinkedListNode<T> n = head;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                return n.getData();
            }
            n = n.getNext();
        }

        // Default return if nothing is returned already
        return (T) null;
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (head == null);
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            String message = "cannot remove null data because null data cannot be added to the structure";
            throw new IllegalArgumentException(message);
        }

        if (size == 1 && head.getData() == data) {
            clear();
        }


        CircularSinglyLinkedListNode<T> last = null;
        CircularSinglyLinkedListNode<T> n = head;
        for (int i = 0; i < size - 1; i++) {
            if (n.getNext().getData() == data) {
                last = n;
            }
            n = n.getNext();
        }

        T oldData;
        if (last == null) {
            if (head.getData() == data) {
                oldData = removeFromFront();
            } else {
                String message = String.format("there was no node with value of %d in the structure", data);
                throw new NoSuchElementException(message);
            }
        } else {
            oldData = last.getNext().getData();
            last.setNext(last.getNext().getNext());
            size -= 1;
        }

        return oldData;
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        Object[] outputArr = new Object[size];
        CircularSinglyLinkedListNode<T> n = head;
        for (int i = 0; i < size; i++) {
            outputArr[i] = n.getData();
            n = n.getNext();
        }
        return (T[]) outputArr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
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
        // DO NOT MODIFY!
        return size;
    }

}
