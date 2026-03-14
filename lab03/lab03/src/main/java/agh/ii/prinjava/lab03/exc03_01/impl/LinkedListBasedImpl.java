package agh.ii.prinjava.lab03.exc03_01.impl;

import agh.ii.prinjava.lab03.exc03_01.QueueOfInts;

/**
 * A doubly linked list-based implementation of the {@link QueueOfInts} interface.
 * This implementation provides O(1) time complexity for both enqueue and dequeue operations.
 * It is serializable, allowing the state of the queue to be persisted.
 */
public class LinkedListBasedImpl implements QueueOfInts, Serializable {

    /**
     * Adds an element to the front of the linked list.
     * Updates the head pointer and establishes backward links for the doubly linked structure.
     * * @param x the integer to be added to the queue
     */
    @Override
    public void enqueue(int x){
        Node newNode = new Node(x, first, null);
        if (first != null) {
            first.prev = newNode;
        }
        first = newNode;

        // If this is the first element added, it is also the last
        if (last == null) {
            last = first;
        }
        numOfElems++;
    }

    /**
     * Removes the element from the back of the linked list.
     * * @return the integer value of the removed node
     * @throws NoSuchElementException if the queue is empty
     */
    @Override
    public int dequeue(){
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty: cannot dequeue");
        }

        int value = last.elem;
        last = last.prev;

        if (last != null) {
            last.next = null;
        } else {
            // Queue is now empty
            first = null;
        }

        numOfElems--;
        return value;
    }

    /**
     * Returns the current size of the queue.
     * * @return the number of elements currently in the list
     */
    @Override
    public int numOfElems() {
        return numOfElems;
    }

    /**
     * Retrieves the value at the back of the queue without removing it.
     * * @return the integer value at the back of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    @Override
    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty: cannot peek");
        }
        return last.elem;
    }

    /**
     * Internal building block for the doubly linked list.
     * Stores the integer value and references to both neighbor nodes.
     */
    private static class Node implements Serializable {
        int elem;
        Node next;
        Node prev;

        /**
         * Constructs a standalone node.
         * @param elem the value to store
         */
        public Node(int elem) {
            this.elem = elem;
        }

        /**
         * Constructs a node with links to its neighbors.
         * @param elem the value to store
         * @param next reference to the next node in the list
         * @param prev reference to the previous node in the list
         */
        public Node(int elem, Node next, Node prev) {
            this.elem = elem;
            this.next = next;
            this.prev = prev;
        }
    }

    /**
     * Reference to the first node in the list (the "front").
     * New elements are added here.
     */
    private Node first = null;

    /**
     * Reference to the last node in the list (the "back").
     * Elements are removed from here to maintain FIFO order.
     */
    private Node last = null;

    /**
     * The current number of elements stored in the queue.
     */
    private int numOfElems = 0;
}
