import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 *
 * @author Noah Statton
 * @version 1.0
 *
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
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
    public MinHeap(ArrayList<T> data) {
        backingArray = (T[]) new Comparable[data.size() * 2 + 1];
        size = data.size();
        for (int i = 0; i < data.size(); i++) {
            backingArray[i + 1] = data.get(i);
        }
        for (int i = size / 2; i > 0; i--) { //add +1
            downHeap(i);
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding.
     * 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (backingArray[backingArray.length - 1] != null) {
            T[] newArray = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 0; i <= size; i++) {
                newArray[i] = backingArray[i];
            }
        }
        backingArray[size + 1] = data;
        size++;
        if (size > 1) {
            heapify(size);
        }
    }

    /**
     * moves elemnts up the tree to maintain order property
     * @param currindex the current index of the heapify process
     */
    private void heapify(int currindex) {
        if (currindex < 2 || backingArray[currindex].compareTo(backingArray[currindex / 2]) > 0) {
            int donothing = 0;
        } else {
            T temp = backingArray[currindex / 2];
            backingArray[currindex / 2] = backingArray[currindex];
            backingArray[currindex] = temp;
            heapify(currindex / 2);
        }

    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after adding.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (backingArray[1] == null) {
            throw new NoSuchElementException("the heap is empty");
        }
        T removed = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        downHeap(1);
        return removed;
    }

    /**
     * moves data down the array to restore order property
     * @param currindex index at current point of recursive process
     */
    private void downHeap(int currindex) {
        int leftChildIndex = currindex * 2;
        int rightChildIndex = currindex * 2 + 1;
        if (leftChildIndex <= size) {
            if (rightChildIndex > size && backingArray[leftChildIndex].compareTo(backingArray[currindex]) < 0) {
                T temp = backingArray[currindex];
                backingArray[currindex] = backingArray[leftChildIndex];
                backingArray[leftChildIndex] = temp;
            } else if (rightChildIndex <= size) {
                if (backingArray[leftChildIndex].compareTo(backingArray[rightChildIndex]) < 0
                        && backingArray[leftChildIndex].compareTo(backingArray[rightChildIndex]) < 0) {
                    T temp = backingArray[currindex];
                    backingArray[currindex] = backingArray[leftChildIndex];
                    backingArray[leftChildIndex] = temp;
                    downHeap(leftChildIndex);
                } else if (backingArray[rightChildIndex].compareTo(backingArray[leftChildIndex]) < 0
                        && backingArray[rightChildIndex].compareTo(backingArray[currindex]) < 0) {
                    T temp = backingArray[currindex];
                    backingArray[currindex] = backingArray[rightChildIndex];
                    backingArray[rightChildIndex] = temp;
                    downHeap(rightChildIndex);
                }
            }
        }
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (backingArray[1] == null) {
            throw new NoSuchElementException("the heap is empty");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (backingArray[1] == null) {
            return true;
        }
        return false;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        size = 0;
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
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
