import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import static java.lang.Integer.max;

/**
 * Your implementation of a BST.
 *
 * @author Noah Statton
 * @version 1.0
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: only class resources
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
            throw new IllegalArgumentException("the collection is empty");
        }
        for (T element: data) {
            if (element == null) {
                throw new IllegalArgumentException("an element in the collection is null");
            }
            add(element);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
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
            throw new IllegalArgumentException("cannot add null data");
        }
        root = rAdd(data, root);
    }

    /**
     * Helper method to add to BST
     * @param data data to be added
     * @param curr current node
     * @return returns the node needed for pointer reinforcement
     */
    public BSTNode<T> rAdd(T data, BSTNode<T> curr) {
        if (curr == null) {
            size++;
            return new BSTNode<>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rAdd(data, curr.getLeft()));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rAdd(data, curr.getRight()));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
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
            throw new IllegalArgumentException("data to be removed is null");
        }
        BSTNode<T> dummy = new BSTNode<T>(null);
        root = rRemove(data, root, dummy);
        return dummy.getData();
    }

    /**
     *
     * @param data the data to be removed
     * @param curr the current bst node that the method is on
     * @param dummy  node holding data of removed node
     * @return removed node
     */
    public BSTNode<T> rRemove(T data, BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("data is not in the tree");
        }
        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rRemove(data, curr.getLeft(), dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rRemove(data, curr.getRight(), dummy));
        } else {
            dummy.setData(curr.getData());
            size--;
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() != null && curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getRight() != null && curr.getLeft() == null) {
                return curr.getRight();
            } else {
                BSTNode<T> dummy2 = new BSTNode<T>(null);
                curr.setRight(removeSuccessor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        return curr;
    }

    /**
     * helper method to remove predecessor node
     * @param curr node at current moment in traversal
     * @param dummy node to store predecessors data
     * @return predecessor node
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr.getLeft() == null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), dummy));
        }
        return curr;
    }


    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
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
            throw new IllegalArgumentException("data is null");
        }
        return search(data, root).getData();

    }

    /**
     * helper method to recusively search the BST
     * @param target data to be found
     * @param root current node of recursal
     * @return the node with the data passed in to the get method
     */
    private BSTNode<T> search(T target, BSTNode<T> root) {
        if (root.getData().compareTo(target) == 0) {
            return root;
        } else if (root.getData().compareTo(target) > 0) {
            if (root.getLeft() == null) {
                throw new NoSuchElementException("The target is not in the tree");
            } else {
                return search(target, root.getLeft());
            }
        } else {
            if (root.getRight() == null) {
                throw new NoSuchElementException("The target is not in the tree");
            } else {
                return search(target, root.getRight());
            }
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
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
            throw new IllegalArgumentException("Data is null");
        }
        return containsHelper(data, root);
    }

    /**
     * helper method to search for node in the BST
     * @param target data we are looking for
     * @param root current node during recursive process
     * @return boolean for whether or not data is found
     */
    private boolean containsHelper(T target, BSTNode<T> root) {
        if (root.getData().compareTo(target) == 0) {
            return true;
        } else if (root.getData().compareTo(target) > 0) {
            if (root.getLeft() == null) {
                return false;
            } else {
                return containsHelper(target, root.getLeft());
            }
        } else {
            if (root.getRight() == null) {
                return false;
            } else {
                return containsHelper(target, root.getRight());
            }
        }
    }


    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        ArrayList<T> traversal = new ArrayList<T>(size);
        preorderHelper(traversal, root);
        return traversal;
    }

    /**
     * helper method for traveral
     * @param traversalList list for recording elements
     * @param curr current node during traversal
     */
    private void preorderHelper(List<T> traversalList, BSTNode<T> curr) {
        if (curr != null) {
            traversalList.add(curr.getData());
            preorderHelper(traversalList, curr.getLeft());
            preorderHelper(traversalList, curr.getRight());
        }
    }


    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        ArrayList<T> traversal = new ArrayList<T>(size);
        inorderHelper(traversal, root);
        return traversal;
    }

    /**
     * helper method for traversal
     * @param traversalList list for recording elements
     * @param curr current node in traversal
     */
    private void inorderHelper(List<T> traversalList, BSTNode<T> curr) {
        if (curr != null) {
            inorderHelper(traversalList, curr.getLeft());
            traversalList.add(curr.getData());
            inorderHelper(traversalList, curr.getRight());
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        ArrayList<T> traversal = new ArrayList<T>(size);
        postorderHelper(traversal, root);
        return traversal;
    }

    /**
     * helper method for traversal
     * @param ttraversalListt list for recording traversal
     * @param curr current node during traversal
     */
    private void postorderHelper(List<T> ttraversalListt, BSTNode<T> curr) {
        if (curr != null) {
            postorderHelper(ttraversalListt, curr.getLeft());
            postorderHelper(ttraversalListt, curr.getRight());
            ttraversalListt.add(curr.getData());
        }
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
        ArrayList<T> traversal = new ArrayList<>(size);
        Queue<BSTNode<T>> traversalqueue = new LinkedList<BSTNode<T>>();
        if (root != null) {
            traversalqueue.add(root);
        }
        while (traversal.size() < size) {
            BSTNode<T> dequeued = traversalqueue.remove();
            traversal.add(dequeued.getData());
            if (dequeued.getLeft() != null) {
                traversalqueue.add(dequeued.getLeft());
            }
            if (dequeued.getRight() != null) {
                traversalqueue.add(dequeued.getRight());
            }
        }
        return traversal;

    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
    }

    /**
     * helper method to find height of tree
     * @param curr current node during recusion process
     * @return int that represents the height of the tree
     */
    private int heightHelper(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        }
        int leftHeight = heightHelper(curr.getLeft());
        int rightHeight = heightHelper(curr.getRight());
        return max(leftHeight, rightHeight) + 1;
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
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k).
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k > n, the number of data
     *                                            in the BST
     */
    public List<T> kLargest(int k) {
        if (k > size) {
            throw new IllegalArgumentException("The list is not big enough for this k value");
        }
        LinkedList<T> klargestList = new LinkedList<T>();
        klargestTraversal(klargestList, root, k);
        return klargestList;
    }

    /**
     * helper method recursively find k_largest elements in the BST
     * @param traversalList linked list to record k_largest values
     * @param curr current node during traversal
     * @param k number of largest elements to record
     */
    private void klargestTraversal(LinkedList<T> traversalList, BSTNode<T> curr, int k) {
        if (traversalList.size() == k) {
            return;
        }
        if (curr.getRight() != null) {
            klargestTraversal(traversalList, curr.getRight(), k);
        }
        if (traversalList.size() < k) {
            traversalList.addFirst(curr.getData());
        }
        if (curr.getLeft() != null) {
            klargestTraversal(traversalList, curr.getLeft(), k);
        }
        return;
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
