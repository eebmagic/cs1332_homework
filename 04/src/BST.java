import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.LinkedList;

/**
 * Your implementation of a BST.
 *
 * @author Ethan Bolton
 * @version 1.0
 * @userid ebolton8
 * @GTID 903368012
 *
 * Collaborators: myself
 *
 * Resources: Google and previous java homework
 */


public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("cannot add null data to the structure");
        }

        for (T x: data) {
            if (x == null) {
                throw new IllegalArgumentException("cannot add null data to the structure");
            }
            add(x);
        }
    }

    /**
     * helper method for adding that uses a node input
     * @param current the current working node
     * @param data the data that is being attempted to be added
     * @return the new value that the current node should have
     */
    private BSTNode<T> addHelp(BSTNode<T> current, T data) {
        if (current == null) {
            size += 1;
            return new BSTNode<>(data);
        } else if (current.getData().compareTo(data) > 0) {
            current.setLeft(addHelp(current.getLeft(), data));
        } else if (current.getData().compareTo(data) < 0) {
            current.setRight(addHelp(current.getRight(), data));
        } else if (current.getData().equals(data)) {
            return current;
        }

        return current;
    }

    /** Adds the data to the tree.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("cannot add null data to the structure");
        }

        root = addHelp(root, data);
    }


    /**
     *
     * @return the data point that is the successor to the given data
     * @param node the node whose successor should be found and removed for
     */
    private T removeSuccessor(BSTNode<T> node) {
        // move right one
        BSTNode<T> parent = node;
        node = node.getRight();

        // move left as far as possible
        while (node.getLeft() != null) {
            parent = node;
            node = node.getLeft();
        }

        // remove the node
        T oldData = node.getData();
        // removes from the node that we've already traversed to in order to prevent repeating a traversal
        BSTNode<T> dummy = new BSTNode<>(null);
        removeHelp(parent, oldData, dummy);

        return oldData;
    }

    /**
     * helper method for recursively removing a node
     * @param node the node to start from
     * @param data the data to try to remove from the node
     * @param dummy a dummy node that must be passed to carry the data through recursions
     * @return the new node
     */
    private BSTNode<T> removeHelp(BSTNode<T> node, T data, BSTNode<T> dummy) {
        if (node == null) {
            dummy.setData(null);
            String message = "cannot remove the data because it does not appear where it should in the structure"
                + " (not present)";
            throw new NoSuchElementException(message);
        }

        if (node.getData().compareTo(data) > 0) {
            node.setLeft(removeHelp(node.getLeft(), data, dummy));
        } else if (node.getData().compareTo(data) < 0) {
            node.setRight(removeHelp(node.getRight(), data, dummy));
        } else if (node.getData().equals(data)) {
            dummy.setData(node.getData());
            
            // one or no child nodes
            if (node.getLeft() == null) {
                size -= 1;
                return node.getRight();
            } else if (node.getRight() == null) {
                size -= 1;
                return node.getLeft();
            } else {
                // if two children
                T successorData = removeSuccessor(node);
                node.setData(successorData);
            }
        }

        return node;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("cannot remove null data from the structure");
        }

        BSTNode<T> dummy = new BSTNode<>(null);
        root = removeHelp(root, data, dummy);
        return dummy.getData();
    }


    /**
     * Returns the data from the tree matching the given parameter.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("cannot search for null data in the structure");
        }

        return getHelp(root, data);
    }

    /**
     * private helper method to make getting data in the tree recursive
     * @param node the node to check / search from
     * @param data the data to look for
     * @return the data at the value of the node that matches the data
     */
    private T getHelp(BSTNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("the data is not found in the structure");
        } else {
            if (data.compareTo(node.getData()) < 0) {
                return getHelp(node.getLeft(), data);
            } else if (data.compareTo(node.getData()) > 0) {
                return getHelp(node.getRight(), data);
            } else {
                return node.getData();
            }
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("cannot check for null data");
        }

        if (root == null) {
            return false;
        }
        try {
            // recursively uses the get helper method to see if the data is in the structure
            // throws an error if not contained
            return getHelp(root, data).equals(data);
        } catch (java.util.NoSuchElementException e) {
            // catches said error and ensures proper behavior
            return false;
        }
    }

    /**
     * helper method for preorder traversal
     * @param node the node at which to execute this recursion
     * @return the List of preordered traversed data from this node.
     */
    private List<T> preHelp(BSTNode<T> node) {
        ArrayList<T> output = new ArrayList<>();

        if (node != null) {
            if (node.getData() != null) {
                output.add(node.getData());
            }
            if (node.getLeft() != null) {
                output.addAll(preHelp(node.getLeft()));
            }
            if (node.getRight() != null) {
                output.addAll(preHelp(node.getRight()));
            }
        }

        return output;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        return preHelp(root);
    }


    /**
     * helper method for inorder traversal
     * @param node the node at which to execute this recursion
     * @return the list of data from the inorder traversal from this node
     */
    private List<T> inHelp(BSTNode<T> node) {
        ArrayList<T> output = new ArrayList<>();

        if (node != null) {
            if (node.getLeft() != null) {
                output.addAll(inHelp(node.getLeft()));
            }
            if (node.getData() != null) {
                output.add(node.getData());
            }
            if (node.getRight() != null) {
                output.addAll(inHelp(node.getRight()));
            }
        }

        return output;
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        return inHelp(root);
    }


    /**
     * helper method for postorder traversal
     * @param node the node at which to execute this recursion from
     * @return the list of data from the postorder traversal from node
     */
    private List<T> postHelp(BSTNode<T> node) {
        ArrayList<T> output = new ArrayList<>();

        if (node != null) {
            if (node.getLeft() != null) {
                output.addAll(postHelp(node.getLeft()));
            }
            if (node.getRight() != null) {
                output.addAll(postHelp(node.getRight()));
            }
            if (node.getData() != null) {
                output.add(node.getData());
            }
        }

        return output;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        return postHelp(root);
    }


    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        ArrayList<T> output = new ArrayList<>();
        LinkedList<BSTNode<T>> nodeQueue = new LinkedList<>();

        if (root != null) {
            nodeQueue.add(root);
        }

        while (nodeQueue.size() != 0) {
            BSTNode<T> workingNode = nodeQueue.getFirst();
            if (workingNode.getLeft() != null) {
                nodeQueue.add(workingNode.getLeft());
            }
            if (workingNode.getRight() != null) {
                nodeQueue.add(workingNode.getRight());
            }
            output.add(workingNode.getData());
            nodeQueue.removeFirst();
        }

        return output;
    }


    /**
     * private helper method for recursively finding the height of the tree
     * @param node the input node that this recursion is working from
     * @return the height of the current node
     */
    private int heightHelp(BSTNode<T> node) {
        if (node.getLeft() == null && node.getRight() == null) {
            // both null
            return 0;
        } else if (node.getLeft() == null && node.getRight() != null) {
            // left null
            return heightHelp(node.getRight()) + 1;
        } else if (node.getLeft() != null && node.getRight() == null) {
            // right null
            return heightHelp(node.getLeft()) + 1;
        } else {
            // none null
            assert node.getLeft() != null;
            return Math.max(heightHelp(node.getLeft()), heightHelp(node.getRight())) + 1;
        }
    }

    /**
     * Returns the height of the root of the tree.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return heightHelp(root);
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Generates a list of the max data per level from the top to the bottom
     * of the tree. (Another way to think about this is to get the right most
     * data per level from top to bottom.)
     *
     * This list should contain the last node of each level.
     *
     * If the tree is empty, an empty list should be returned.
     *
     * Ex:
     * Given the following BST composed of Integers
     *      2
     *    /   \
     *   1     4
     *  /     / \
     * 0     3   5
     * getMaxDataPerLevel() should return the list [2, 4, 5] - 2 is the max
     * data of level 0, 4 is the max data of level 1, and 5 is the max data of
     * level 2
     *
     * Ex:
     * Given the following BST composed of Integers
     *               50
     *           /        \
     *         25         75
     *       /    \
     *      12    37
     *     /  \    \
     *   10   15   40
     *  /
     * 13
     * getMaxDataPerLevel() should return the list [50, 75, 37, 40, 13] - 50 is
     * the max data of level 0, 75 is the max data of level 1, 37 is the
     * max data of level 2, etc.
     *
     * Must be O(n).
     *
     * @return the list containing the max data of each level
     */
    public List<T> getMaxDataPerLevel() {
        ArrayList<T> startingArr = new ArrayList<>();
        if (root == null) {
            return startingArr;
        } else {
            return getMaxHelper(root, startingArr, 0);
        }
    }


    /**
     * helper method for getting the max data at each level in the tree
     * @param curr the node at which to execute this recursion from
     * @param currentArray the data thus far from previous recursions
     * @param currentDepth the depth of the node (used for array index)
     * @return the updated array with new data from this node
     */
    private ArrayList<T> getMaxHelper(BSTNode<T> curr, ArrayList<T> currentArray, int currentDepth) {
        if (currentDepth + 1 > currentArray.size()) {
            // if no entry for this depth
            currentArray.add(curr.getData());
        } else if (curr.getData().compareTo(currentArray.get(currentDepth)) > 0) {
            // if node greater than value for depth
            currentArray.set(currentDepth, curr.getData());
        }

        if (curr.getLeft() != null) {
            currentArray = getMaxHelper(curr.getLeft(), currentArray, currentDepth + 1);
        }
        if (curr.getRight() != null) {
            currentArray = getMaxHelper(curr.getRight(), currentArray, currentDepth + 1);
        }

        return currentArray;
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
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
