import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author Noah Statton
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("the list is null");
        }
        for (T element: data) {
            if (element == null) {
                throw new IllegalArgumentException("an item in the list is null");
            }
            add(element);
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("cannot add null data");
        }
        root = rAdd(data, root);
    }

    /**
     * Helper method to add to AVL
     * @param data data to be added
     * @param curr current node
     * @return returns the node needed for pointer reinforcement
     */
    private AVLNode<T> rAdd(T data, AVLNode<T> curr) {
        if (curr == null) {
            size++;
            AVLNode<T> addedNode = new AVLNode<>(data);
            addedNode.setHeight(0);
            addedNode.setBalanceFactor(0);
            return addedNode;
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rAdd(data, curr.getLeft()));
            update(curr);
            if (curr.getBalanceFactor() < -1) {
                if (curr.getRight().getBalanceFactor() < 0) {
                    return leftRotation(curr);
                } else if (curr.getRight().getBalanceFactor() > 0) {
                    //maybe isues
                    curr.setRight(rightRotation(curr.getRight()));
                    return leftRotation(curr);
                }
            }
            if (curr.getBalanceFactor() > 1) {
                if (curr.getLeft().getBalanceFactor() > 0) {
                    return rightRotation(curr);
                } else if (curr.getLeft().getBalanceFactor() < 0) {
                    //maybe isues
                    curr.setLeft(leftRotation(curr.getLeft()));
                    return rightRotation(curr);
                }
            }
            //check for rotation
            //if needed return rotationtype()
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rAdd(data, curr.getRight()));
            update(curr);
            //check for rotation
            if (curr.getBalanceFactor() < -1) {
                if (curr.getRight().getBalanceFactor() < 0) {
                    return leftRotation(curr);
                } else if (curr.getRight().getBalanceFactor() > 0) {
                    //maybe isues
                    curr.setRight(rightRotation(curr.getRight()));
                    return leftRotation(curr);
                }
            }
            if (curr.getBalanceFactor() > 1) {
                if (curr.getLeft().getBalanceFactor() > 0) {
                    return rightRotation(curr);
                } else if (curr.getLeft().getBalanceFactor() < 0) {
                    //maybe isues
                    curr.setLeft(leftRotation(curr.getLeft()));
                    return rightRotation(curr);
                }
            }
        }
        return curr;
    }

    /**
     * updates the height and balance factor of the current node
     * @param curr node to be updated
     */
    private void update(AVLNode<T> curr) {
        curr.setBalanceFactor(easyHeight(curr.getLeft()) - easyHeight(curr.getRight()));
        curr.setHeight(1 + Math.max(easyHeight(curr.getLeft()), easyHeight(curr.getRight())));
    }

    /**
     * calculates the height
     * @param curr the node to have its height calculated
     * @return the value of the height
     */
    private int easyHeight(AVLNode curr) {
        if (curr == null) {
            return -1;
        } else {
            return curr.getHeight();
        }
    }


    /**
     * rotates the tree left
     * @param a point of rotation
     * @return rotated subtree
     */
    private AVLNode<T> leftRotation(AVLNode<T> a) {
        AVLNode b = a.getRight();
        a.setRight(b.getLeft());
        b.setLeft(a);
        update(a);
        update(b);
        return b;
    }

    /**
     *  rotates the tree right
     * @param a point of rotation
     * @return rotated subtree
     */
    private AVLNode<T> rightRotation(AVLNode<T> a) {
        AVLNode b = a.getLeft();
        a.setLeft(b.getRight());
        b.setRight(a);
        update(a);
        update(b);
        return b;
    }


    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data to be removed is null");
        }
        AVLNode<T> dummy = new AVLNode<T>(null);
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
    public AVLNode<T> rRemove(T data, AVLNode<T> curr, AVLNode<T> dummy) {
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
                AVLNode<T> dummy2 = new AVLNode<T>(null);
                curr.setRight(removeSuccessor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        update(curr);
        if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() < 0) {
                return leftRotation(curr);
            } else if (curr.getRight().getBalanceFactor() > 0) {
                //maybe isues
                curr.setRight(rightRotation(curr.getRight()));
                return leftRotation(curr);
            }
        }
        if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft().getBalanceFactor() > 0) {
                return rightRotation(curr);
            } else if (curr.getLeft().getBalanceFactor() < 0) {
                //maybe isues
                curr.setLeft(leftRotation(curr.getLeft()));
                return rightRotation(curr);
            }
        }
        return curr;
    }

    /**
     * helper method to remove successor node
     * @param curr node at current moment in traversal
     * @param dummy node to store successor data
     * @return successor node
     */
    private AVLNode<T> removeSuccessor(AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr.getLeft() == null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), dummy));
        }
        update(curr);
        if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() < 0) {
                return leftRotation(curr);
            } else if (curr.getRight().getBalanceFactor() > 0) {
                //maybe isues
                curr.setRight(rightRotation(curr.getRight()));
                return leftRotation(curr);
            }
        }
        if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft().getBalanceFactor() > 0) {
                return rightRotation(curr);
            } else if (curr.getLeft().getBalanceFactor() < 0) {
                //maybe isues
                curr.setLeft(leftRotation(curr.getLeft()));
                return rightRotation(curr);
            }
        }
        return curr;
    }



    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
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
    private AVLNode<T> search(T target, AVLNode<T> root) {
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
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
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
    private boolean containsHelper(T target, AVLNode<T> root) {
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
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * Your list should not duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> branch = new ArrayList<>();
        if (root != null) {
            deepestBranchesHelper(root, branch);
        }
        return branch;
    }

    /**
     * moves three tree nodes to find the nodes on the deepest branch
     * @param curr the current node in the recusive process
     * @param branch the list of nodes on the deepest branch
     */
    private void deepestBranchesHelper(AVLNode curr, List<T> branch) {
        branch.add((T) curr.getData());
        if (curr.getLeft() != null && curr.getRight() != null) {
            if (curr.getLeft().getHeight() > curr.getRight().getHeight()) {
                deepestBranchesHelper(curr.getLeft(), branch);
            }
            if (curr.getLeft().getHeight() == curr.getRight().getHeight()) {
                deepestBranchesHelper(curr.getLeft(), branch);
                deepestBranchesHelper(curr.getRight(), branch);
            }
            if (curr.getLeft().getHeight() < curr.getRight().getHeight()) {
                deepestBranchesHelper(curr.getRight(), branch);
            }
        } else if (curr.getLeft() == null && curr.getRight() != null) {
            deepestBranchesHelper(curr.getRight(), branch);

        } else if (curr.getRight() == null && curr.getLeft() != null) {
            deepestBranchesHelper(curr.getLeft(), branch);
        }
    }


    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     *
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @throws java.lang.IllegalArgumentException if data1 or data2 are null
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * or if data1 > data2
     * @return a sorted list of data that is > data1 and < data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("one of the threshholds is new");
        }
        ArrayList<T> traversal = new ArrayList<T>(size);
        inorderHelper(traversal, root, data1, data2);
        return traversal;
    }

    /**
     * helper method for sortedInbetween
     * @param traversalList list of inbetween values
     * @param curr current node in traversal
     * @param data1 lower bound
     * @param data2 upper bound
     */
    private void inorderHelper(List<T> traversalList, AVLNode<T> curr, T data1, T data2) {
        if (curr != null) {
            if (curr.getData().compareTo(data1) > 0 && curr.getData().compareTo(data2) < 0) {
                inorderHelper(traversalList, curr.getLeft(), data1, data2);
                traversalList.add(curr.getData());
                inorderHelper(traversalList, curr.getRight(), data1, data2);
            } else if (curr.getData().compareTo(data1) <= 0) {
                inorderHelper(traversalList, curr.getRight(), data1, data2);
            } else if (curr.getData().compareTo(data2) >= 0) {
                inorderHelper(traversalList, curr.getLeft(), data1, data2);
            }
        }
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}