import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Your implementation of an AVL.
 *
 * @author Ethan Bolton
 * @version 1.0
 * @userid ebolton8
 * @GTID 903368012
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class AVL<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("cannot construct tree from null Collection");
        }

        for (T x: data) {
            if (x == null) {
                throw new IllegalArgumentException("cannot add null data to the structure");
            }

            add(x); /// Is this correct or should it be a more effecient method that avoids extra traversal?
        }
    }

    /**
     * private helper method for updating nodes after adding & removing nodes
     * executed when traversing back up after an operation
     * @param target
     */
    private AVLNode<T> updateNodeValues(AVLNode<T> curr) {
        // get heights of children
        int l;
        if (curr.getLeft() != null) {
            l = curr.getLeft().getHeight();
        } else {
            l = -1;
        }

        int r;
        if (curr.getRight() != null) {
            r = curr.getRight().getHeight();
        } else {
            r = -1;
        }

//        AVLNode<T> newNode = curr;

        // update height
        curr.setHeight(Math.max(l, r) + 1);

        // update balance factor
        curr.setBalanceFactor(l - r);

        if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() > 0) {
                // precede with right rotation
                curr.setRight(rotateRight(curr.getRight()));

            }
            // left rotation
            curr = rotateLeft(curr);   /// check that both of these should be curr ^
//            curr.setLeft(rotateRight(curr.getLeft()));

        } else if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft().getBalanceFactor() < 0) {
                // precede with left rotation
                curr.setLeft(rotateLeft(curr.getLeft()));
            }
            // right rotation
            curr = rotateRight(curr);   /// check that both of these should be curr ^
//            curr.setRight(rotateRight(curr.getRight()));
        }
        return curr;
    }

    /**
     * private method for performing left rotations
     * used in updateNodeValues method
     * @param curr the current node to rotate from
     */
    private AVLNode<T> rotateLeft(AVLNode<T> curr) {
        AVLNode<T> parent = curr;
        AVLNode<T> child = curr.getRight();

        // rearrange connections
        parent.setRight(child.getLeft());
        child.setLeft(parent);

        // recalculate heights & BF (from new bottom up)
        parent = updateNodeValues(parent);
        child = updateNodeValues(child);

        // return the child node which is now the new parent node
        return child;
    }

    /**
     * private method for performating right rotations
     * used in updateNodeValues method
     * @param curr the current node to rotate from
     */
    private AVLNode<T> rotateRight(AVLNode<T> curr) {
        AVLNode<T> parent = curr;
        AVLNode<T> child = curr.getLeft();

        // rearrange connections
        parent.setLeft(child.getRight());
        child.setRight(parent);


        // recalculate heights & BF (from new bottom up)
        parent = updateNodeValues(parent);
        child = updateNodeValues(child);

        // return the child node which is now the new parent node
        return child;
    }


    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("cannot add null data to the structure");
        }

        if (size == 0) {
            // Add node
            root = new AVLNode<>(data);
            // Set node values
            root.setHeight(0);
            root.setBalanceFactor(0);
            size += 1;
        } else {
            if (data != root.getData()) {
                AVLNode<T> newRoot = addHelper(data, root);
                if (newRoot != null) {
                    root = newRoot;
                    size += 1;
                }
            }
        }
    }

    /**
     * private helper for recursive adding
     * @param data the data to add
     * @param targetNode the node at which to try to add to
     */
    private AVLNode<T> addHelper(T data, AVLNode<T> targetNode) {
        if (!(targetNode.getData().equals(data))) {
            if (data.compareTo(targetNode.getData()) < 0) {
                if (targetNode.getLeft() == null) {
                    // add to left
                    targetNode.setLeft(new AVLNode<>(data));
                    targetNode.getLeft().setHeight(0);
                    targetNode.getLeft().setBalanceFactor(0);
                } else {
                    // traverse to the left
                    targetNode.setLeft(addHelper(data, targetNode.getLeft()));
                }
            } else if (data.compareTo(targetNode.getData()) > 0) {
                if (targetNode.getRight() == null) {
                    // add to right
                    targetNode.setRight(new AVLNode<>(data));
                    targetNode.getRight().setHeight(0);
                    targetNode.getRight().setBalanceFactor(0);
                } else {
                    // traverse to the right
                    targetNode.setRight(addHelper(data, targetNode.getRight()));
                }
            }

        } else if (targetNode.getData().equals(data)) {
            // don't change tree if data is already in the tree
            return null;
        }
        // After adding under target node or while traversing back up
        AVLNode<T> updated = updateNodeValues(targetNode);
        targetNode = updated; // updates height & BF  /// is this necessary with rebalancing now working??
        /// check if a rotation is necessary?
        return targetNode;
    }


    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("null data cannot be in the struct");
        }

        if (size == 1) {
            if (root.getData().equals(data)) {
                // if only one node then remove/return root data
                T oldData = root.getData();
                clear();
                return oldData;
            } else {
                throw new NoSuchElementException("target data is not contained in the tree");
            }
        } else {
            T dummy = null;
            root = removeHelp(root, data, dummy);
            return dummy;
        }
    }

    /**
     * my helper method for recursively removing a node
     * @param curr the current working node from which to look for the target data in the tree
     * @param target the data to remove from the tree
     * @return the oldData at the node that is removed
     */
    private AVLNode<T> removeHelp(AVLNode<T> curr, T target, T dummy) {
        if (curr == null) {
            System.out.println("Reached the end of a branch? Is therfor node not present?");
            return null;
        }

        AVLNode<T> child;
        boolean childIsRight = false;
        child = null;
        if (curr.getData().compareTo(target) > 0) {
            child = curr.getLeft();
            childIsRight = false;
        } else if (curr.getData().compareTo(target) < 0) {
            child = curr.getRight();
            childIsRight = true;
        }

        if (child == null) {
            throw new NoSuchElementException("the target data could not be found in the structure");
        }

        if (child.getData().compareTo(target) > 0) {
            curr.set
        }
        if (child.getData().equals(target)) {
            dummy = child.getData();

            // Remove Node
            if (child.getLeft() == null && child.getRight() == null) {
                // case 1 - no child nodes (remove node)
                if (childIsRight) {
                    curr.setRight(null);
                } else {
                    curr.setLeft(null);
                }
            } else if (child.getLeft() == null || child.getRight() == null) {
                // case 2 - one child null (replace with child)
                if (child.getLeft() != null) {
                    if (childIsRight) {
                        curr.setRight(child.getLeft());
                    } else {
                        curr.setLeft(child.getLeft());
                    }
                } else {
                    if (childIsRight) {
                        curr.setRight(child.getRight());
                    } else {
                        curr.setLeft(child.getRight());
                    }
                }
            } else {
                // case 3 - two child nodes (replace with predecessor node)
                child.setData(removePred(child));
            }

            size -= 1;
            AVLNode<T> updated = updateNodeValues(curr);
            return updated;

        } else {
//            T oldData = removeHelp(child, target);
            if (childIsRight) {
                curr.setRight(removeHelp(child, target, dummy));
            } else {
                curr.setLeft(removeHelp(child, target, dummy));
            }
            return curr;
        }

    }

    /**
     * My private helper method for removing the predecessor node
     * @param curr the current node from which to find/remove the predecessor
     * @return the value at the predecessor node
     */
    private T removePred(AVLNode<T> start) {
        if (start.getLeft().getRight() != null){
            AVLNode<T> parent = start;
            AVLNode<T> curr = start.getLeft();
            while (curr.getRight() != null) {
                parent = curr;
                curr = curr.getRight();
            }

            // remove and return the data
            T oldData = curr.getData();
            parent.setRight(null);
            parent = updateNodeValues(parent);
            return oldData;
        } else {
            // in case of shallower tree
            T oldData = start.getLeft().getData();
            start.setLeft(null);
            start = updateNodeValues(start);
            return oldData;
        }
    }


    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("null data cannot be in the struct");
        }

        if (size == 0) {
            throw new NoSuchElementException("the data is not in the tree");
        }

        return getHelper(data, root);
    }

    /**
     * private helper for getting data in tree
     * @param data data to search for
     * @param targetNode target node to search from
     * @return the value of the target node with matching data
     * @throws java.util.NoSuchElementException if the data is not found in the tree
     */
    private T getHelper(T data, AVLNode<T> targetNode) {
        int comparison = data.compareTo(targetNode.getData());

        if (comparison == 0) {
            return targetNode.getData();
        } else if (comparison < 0) {
            if (targetNode.getLeft() == null) {
                throw new NoSuchElementException("data is not in the structure");
            } else {
                return getHelper(data, targetNode.getLeft());
            }
        } else if (comparison > 0) {
            if (targetNode.getRight() == null) {
                throw new NoSuchElementException("data is not in the structure");
            } else {
                return getHelper(data, targetNode.getRight());
            }
        }

        return null;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("null data cannot be in the struct");
        }

        try {
            return get(data).equals(data);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Returns the height of the root of the tree.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }

        /// TEST IMPLEMENTATION
        return heightHelper(root);
    }

    /**
     * Private helper method for recursively finding height of a tree
     * @param curr the node at which to get the current height
     * @return the height of the curr node
     */
    private int heightHelper(AVLNode curr) {
        if (curr == null) {
            return -1;
        }
        int left = heightHelper(curr.getLeft());
        int right = heightHelper(curr.getRight());
        return Math.max(left, right) + 1;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Find all elements within a certain distance from the given data.
     * "Distance" means the number of edges between two nodes in the tree.
     *
     * To do this, first find the data in the tree. Keep track of the distance
     * of the current node on the path to the data (you can use the return
     * value of a helper method to denote the current distance to the target
     * data - but note that you must find the data first before you can
     * calculate this information). After you have found the data, you should
     * know the distance of each node on the path to the data. With that
     * information, you can determine how much farther away you can traverse
     * from the main path while remaining within distance of the target data.
     *
     * Use a HashSet as the Set you return. Keep in mind that since it is a
     * Set, you do not have to worry about any specific order in the Set.
     *
     * Note: We recommend 2 helper methods:
     * 1. One helper method should be for adding the nodes on the path (from
     * the root to the node containing the data) to the Set (if they are within
     * the distance). This helper method will also need to find the distance
     * between each node on the path and the target data node.
     * 2. One helper method should be for adding the children of the nodes
     * along the path to the Set (if they are within the distance). The
     * private method stub called elementsWithinDistanceBelow is intended to
     * be the second helper method. You do NOT have to implement
     * elementsWithinDistanceBelow. However, we recommend you use this method
     * to help implement elementsWithinDistance.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *              50
     *            /    \
     *         25      75
     *        /  \     / \
     *      13   37  70  80
     *    /  \    \      \
     *   12  15    40    85
     *  /
     * 10
     * elementsWithinDistance(37, 3) should return the set {12, 13, 15, 25,
     * 37, 40, 50, 75}
     * elementsWithinDistance(85, 2) should return the set {75, 80, 85}
     * elementsWithinDistance(13, 1) should return the set {12, 13, 15, 25}
     *
     * @param data     the data to begin calculating distance from
     * @param distance the maximum distance allowed
     * @return the set of all data within a certain distance from the given data
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   is the data is not in the tree
     * @throws java.lang.IllegalArgumentException if distance is negative
     */
    public Set<T> elementsWithinDistance(T data, int distance) {
        return null; ///IMPELEMT
    }

    /**
     * You do NOT have to implement this method if you choose not to.
     * However, this will help with the elementsWithinDistance method.
     *
     * Adds data to the Set if the current node is within the maximum distance
     * from the target node. Recursively call on the current node's children to
     * add their data too if the children's data are also within the maximum
     * distance from the target node.
     *
     * @param curNode         the current node
     * @param maximumDistance the maximum distance allowed
     * @param currentDistance the distance between the current node and the
     *                        target node
     * @param currentResult   the current set of data within the maximum
     *                        distance
     */
    private void elementsWithinDistanceBelow(AVLNode<T> curNode,
                                             int maximumDistance,
                                             int currentDistance,
                                             Set<T> currentResult) {
        // STUDENT CODE HERE    /// Implement this
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
