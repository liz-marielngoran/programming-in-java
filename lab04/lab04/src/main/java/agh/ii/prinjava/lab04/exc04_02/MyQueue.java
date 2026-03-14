package agh.ii.prinjava.lab04.exc04_02;

import agh.ii.prinjava.lab04.exc04_02.impl.MyQueueDLLBImpl;
import java.util.NoSuchElementException;

/**
 * A First-In-First-Out (FIFO) data structure.
 * Elements are inserted at the end and removed from the beginning.
 *
 * @param <E> the type of elements held in this queue
 */
public interface MyQueue<E> {

    /**
     * Inserts the specified element at the end of the queue.
     * @param x the element to add
     */
    void enqueue(E x);

    /**
     * Retrieves and removes the head of this queue.
     * @return the head of this queue
     * @throws NoSuchElementException if the queue is empty
     */
    E dequeue() throws NoSuchElementException;


    /**
     * Checks if the queue contains no elements.
     * @return true if the queue is empty, false otherwise
     */
    default boolean isEmpty() {
        return numOfElems() == 0;
    }

    /**
     * Returns the number of elements currently in the queue.
     * @return total element count
     */
    int numOfElems();

    /**
     * Retrieves, but does not remove, the head of this queue.
     * @return the head of this queue
     * @throws NoSuchElementException if the queue is empty
     */
    E peek() throws NoSuchElementException;

    /** Consider pros and cons of having a factory method in the interface */
    static <T> MyQueue<T> create() {
        return new MyQueueDLLBImpl<>();
    }
}
