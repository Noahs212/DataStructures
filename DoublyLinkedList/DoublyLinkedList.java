import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Noah Statton
 * @version 1.0
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is to big or small");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        DoublyLinkedListNode<T> datanode = new DoublyLinkedListNode<>(data, null, null);
        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            DoublyLinkedListNode<T> curr = null;
            if (index <= size / 2) {
                curr = head;
                for (int i = 0; i < index; i++) {
                    curr = curr.getNext();
                }
            } else {
                curr = tail;
                for (int i = size - 1; i > index; i--) {
                    curr = curr.getPrevious();
                }
            }
            curr.getPrevious().setNext(datanode);
            datanode.setNext(curr);
            datanode.setPrevious(curr.getPrevious());
            curr.setPrevious(datanode);
            size++;
        }

    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        DoublyLinkedListNode<T> datanode = new DoublyLinkedListNode<>(data, null, null);
        if (data == null) {
            throw new IllegalArgumentException("cannot add null data");
        }
        if (head == null) {
            head = datanode;
            tail = datanode;
        } else {
            head.setPrevious(datanode);
            datanode.setNext(head);
            head =  datanode;
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        DoublyLinkedListNode<T> datanode = new DoublyLinkedListNode<>(data, null, null);
        if (datanode == null) {
            throw new IllegalArgumentException("cannot add null data");
        }
        if (head == null) {
            head = datanode;
            tail =  datanode;
        } else {
            tail.setNext(datanode);
            datanode.setPrevious(tail);
            tail = datanode;
        }
        size++;

    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is to big or small");
        }
        DoublyLinkedListNode<T> removed = null;
        if (index == 0) {
            removeFromFront();
        } else if (index == (size - 1)) {
            removeFromBack();
        } else {
            DoublyLinkedListNode<T> curr = null;
            if (index <= size / 2) {
                curr = head;
                for (int i = 0; i < index; i++) {
                    curr = curr.getNext();
                }
            } else {
                curr = tail;
                for (int i = size - 1; i > index; i--) {
                    curr = curr.getPrevious();
                }
            }
            removed = new DoublyLinkedListNode<T>(curr.getData());
            curr.getPrevious().setNext(curr.getNext());
            curr.getNext().setPrevious(curr.getPrevious());
            size--;
        }
        return removed.getData();
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }
        DoublyLinkedListNode<T> removeddatanode = new DoublyLinkedListNode<T>(head.getData());
        head = head.getNext();
        head.setPrevious(null);
        size--;
        return removeddatanode.getData();
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }
        DoublyLinkedListNode<T> removeddatanode = new DoublyLinkedListNode<T>(tail.getData());
        tail.getPrevious().setNext(null);
        tail = tail.getPrevious();
        size--;
        return removeddatanode.getData();
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is to big or small");
        }
        if (index == 0) {
            return head.getData();
        } else if (index == (size - 1)) {
            return tail.getData();
        } else {
            DoublyLinkedListNode<T> curr = null;
            if (index <= size / 2) {
                curr = head;
                for (int i = 0; i < index; i++) {
                    curr = curr.getNext();
                }
            } else {
                curr = tail;
                for (int i = size - 1; i > index; i--) {
                    curr = curr.getPrevious();
                }
            }
            return curr.getData();
        }

    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (head == null) {
            return true;
        }
        return false;
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
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (tail.getData().equals(data)) {
            return removeFromBack();
        } else {
            int indexcounter = 0;
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < size - 1; i++) {
                if (curr.getData().equals(data)) {
                    indexcounter = i;
                }
                curr = curr.getNext();
            }
            return removeAtIndex(indexcounter);
        }

    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] linkedlistArray = new Object[size];
        if (head == null) {
            return linkedlistArray;
        }
        DoublyLinkedListNode<T> curr = head;
        for (int i = 0; i < size; i++) {
            linkedlistArray[i] = curr.getData();
            curr = curr.getNext();
        }
        return linkedlistArray;

    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
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
