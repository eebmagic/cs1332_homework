import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Your implementation of a ExternalChainingHashMap.
 *
 * @author Ethan Bolton
 * @version 1.0
 * @userid ebolton8
 * @GTID 903368012
 *
 * Collaborators: N/A
 *
 * Resources: Previous Java homewor and Piazza
 */
public class ExternalChainingHashMap<K, V> {

    /*
     * The initial capacity of the ExternalChainingHashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * The max load factor of the ExternalChainingHashMap.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private ExternalChainingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new ExternalChainingHashMap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     *
     * Use constructor chaining.
     */
    public ExternalChainingHashMap() {
        ExternalChainingMapEntry<K, V>[] newArr = new ExternalChainingMapEntry[INITIAL_CAPACITY];
        table = newArr;
        size = 0;
    }

    /**
     * Constructs a new ExternalChainingHashMap.
     *
     * The backing array should have an initial capacity of initialCapacity.
     *
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public ExternalChainingHashMap(int initialCapacity) {
        ExternalChainingMapEntry<K, V>[] newArr = new ExternalChainingMapEntry[initialCapacity];
        table = newArr;
        size = 0;
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, use external chaining as your resolution
     * strategy. Add new entries to the front of an existing chain, but don't
     * forget to check the entire chain for duplicate keys first.
     *
     * If you find a duplicate key, then replace the entry's value with the new
     * one passed in. When replacing the old value, replace it at that position
     * in the chain, not by creating a new entry and adding it to the front.
     *
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. Resize if the load factor is greater than max LF (it is okay
     * if the load factor is equal to max LF). For example, let's say the
     * array is of length 5 and the current size is 3 (LF = 0.6). For this
     * example, assume that no elements are removed in between steps. If
     * another entry is attempted to be added, before doing anything else,
     * you should check whether (3 + 1) / 5 = 0.8 is larger than the max LF.
     * It is, so you would trigger a resize before you even attempt to add
     * the data or figure out if it's a duplicate. Be careful to consider the
     * differences between integer and double division when calculating load
     * factor.
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Cannot add null data to the structure");
        }

        double loadFactor = (double) (size + 1) / table.length;
        if (loadFactor > MAX_LOAD_FACTOR) {
            resizeBackingTable((table.length * 2) + 1);
        }

        int targetIndex = Math.abs(key.hashCode() % table.length);
        if (table[targetIndex] == null) {
            // Insert @ index if no nodes present
            ExternalChainingMapEntry<K, V> newNode = new ExternalChainingMapEntry<>(key, value);
            table[targetIndex] = newNode;
            size += 1;
            return null;
        } else {
            ExternalChainingMapEntry<K, V> curr = table[targetIndex];
            while (curr != null) {

                if (curr.getKey() == key) {
                    // already in the map; update the data
                    V oldData = curr.getValue();
                    curr.setValue(value);
                    return oldData;
                }

                if (curr.getNext() == null) {
                    // key not in the stack, but hash index taken; add to FRONT of chain
                    table[targetIndex] = new ExternalChainingMapEntry<>(key, value, table[targetIndex]);

                    size += 1;
                    return null;
                }

                curr = curr.getNext();
            }
        }
        return null;
    }

    /**
     * Removes the entry with a matching key from the map.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot search for null data in the structure");
        }

        int targetIndex = Math.abs(key.hashCode() % table.length);
        if (table[targetIndex] == null) {
            throw new NoSuchElementException("the key value is not in the structure");
        } else {
            ExternalChainingMapEntry<K, V> curr = table[targetIndex];
            if (curr.getKey().equals(key)) {
                V oldData = curr.getValue();
                table[targetIndex] = curr.getNext();
                size -= 1;
                return oldData;
            }
            while (curr.getNext() != null) {
                if (curr.getNext().getKey().equals(key)) {
                    V oldData = curr.getNext().getValue();
                    curr.setNext(curr.getNext().getNext());
                    size -= 1;
                    return oldData;
                }
                curr = curr.getNext();
            }
            throw new NoSuchElementException("the key value is not in the structure");
        }
    }


    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot search for null data in the structure");
        }

        int targetIndex = Math.abs(key.hashCode() % table.length);
        if (table[targetIndex] == null) {
            throw new NoSuchElementException("the key value is not in the structure");
        } else {
            ExternalChainingMapEntry<K, V> curr = table[targetIndex];
            while (curr != null) {
                if (curr.getKey().equals(key)) {
                    return curr.getValue();
                }
                curr = curr.getNext();
            }
            throw new NoSuchElementException("the key value is not in the structure");
        }
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot search for null data in the structure");
        }

        int targetIndex = Math.abs(key.hashCode() % table.length);
        if (table[targetIndex] == null) {
            return false;
        } else {
            ExternalChainingMapEntry<K, V> curr = table[targetIndex];
            while (curr != null) {
                if (curr.getKey().equals(key)) {
                    return true;
                } else {
                    curr = curr.getNext();
                }
            }
        }
        return false;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        HashSet<K> output = new HashSet<>();
        for (int i = 0; output.size() < size; i++) {
            ExternalChainingMapEntry<K, V> curr = table[i];
            while (curr != null) {
                output.add(curr.getKey());
                curr = curr.getNext();
            }
        }
        return output;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use java.util.ArrayList or java.util.LinkedList.
     *
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        ArrayList<V> output = new ArrayList<>();
        for (int i = 0; output.size() < size; i++) {
            ExternalChainingMapEntry<K, V> curr = table[i];
            while (curr != null) {
                output.add(curr.getValue());
                curr = curr.getNext();
            }
        }
        return output;
    }

    /**
     * Resize the backing table to length.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     *
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     *                                            number of items in the hash
     *                                            map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("cannot make table smaller than current size");
        }

        ExternalChainingMapEntry<K, V>[] oldTable = table;
        table = new ExternalChainingMapEntry[length];

        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] != null) {
                ExternalChainingMapEntry<K, V> oldNode = oldTable[i];
                while (oldNode != null) {
                    resizeHelper(oldNode.getKey(), oldNode.getValue());
                    oldNode = oldNode.getNext();
                }
            }
        }

    }

    /**
     * private helper for adding nodes during resizing
     * @param key the hash key for the node
     * @param value the value of the node
     */
    private void resizeHelper(K key, V value) {
        int targetIndex = Math.abs(key.hashCode() % table.length);
        table[targetIndex] = new ExternalChainingMapEntry<>(key, value, table[targetIndex]);
    }


    /**
     * Clears the map.
     *
     * Resets the table to a new array of the initial capacity and resets the
     * size.
     *
     * Must be O(1).
     */
    public void clear() {
        ExternalChainingMapEntry<K, V>[] newArr = new ExternalChainingMapEntry[INITIAL_CAPACITY];
        table = newArr;
        size = 0;
    }

    /**
     * Returns the table of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public ExternalChainingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
