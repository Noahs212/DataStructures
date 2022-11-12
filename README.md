# DataStructures
An implementation of several data structures for generic data types and corresponding unit testing files. Data Structures include, ArrayList, DoublyLinkedList, Deque, HashMap, Min Heap, Binary Search Tree, and AVL tree. 

ArrayList Methods:
  addAtIndex(int index, T data)
  addToFront(T data)
  addToBack(T data)
  removeAtIndex(int index)
  removeFromFront()
  removeFromBack()
  get(int index)
  isEmpty()
  clear()
  
  
DoublyLinkedList Methods:
  addAtIndex(int index, T data), 
  addToFront(T data), 
  addToBack(T data), 
  removeAtIndex(int index), 
  removeFromFront(), 
  removeFromBack(), 
  get(int index), 
  isEmpty(), 
  clear(), 
  removeLastOccurrence(T data), 
  toArray(), 
  getHead(), 
  getTail(), 
  size()
  
Deque - Array and LinkedList Backed

Array Backed: 
   addFirst(T data), 
   addLast(T data), 
   removeFirst(), 
   removeLast(), 
   getFirst(), 
   getLast(), 
   getBackingArray(), 
   size(), 
   
 LinkedList Backed Deque Methods: 
   addFirst(T data),
   addLast(T data),
   removeFirst(),
   removeLast(),
   getFirst(), 
   getLast(), 
   getHead(),
   getTail(), 
   size()
  
HashMap - uses a linear probing collision strategy
HashMap methods: put(K key, V value), remove(K key), get(K key), containsKey(K key), keySet(), values(), resizeBackingTable(int length), clear(), size()
  
MinHeap Methods:
    add(T data),  heapify(int currindex, remove(), downHeap(int currindex), getMin(), isEmpty(), clear(), getBackingArray(), size()
    
Binary Search Tree Methods:
    add(T data), remove(T data), get(T data), search(T target, BSTNode root), contains(T data), preorder(), inorder(), postorder(), levelorder(), height(), clear(), kLargest(int k), getRoot(), size()
   
AVL Methods:
    add(T data), update(AVLNode curr), easyHeight(AVLNode curr), leftRotation(AVLNode a), rightRotation(AVLNode a), remove(T data), get(T data), search(T target, AVLNode root), contains(T data), deepestBranches(), sortedInBetween(T data1, T data2), clear(), height(), size(), getRoot()
    
Unit Testing Files:
    ArrayListStudentTest.java
    ArrayListTest.java
    DoublyLinkedListStudentTest
    DequeStudentTest.java
    LinearProbingHashMapStudentTest.java
    MinHeapStudentTest.java
    BSTStudentTest.java
    
    
