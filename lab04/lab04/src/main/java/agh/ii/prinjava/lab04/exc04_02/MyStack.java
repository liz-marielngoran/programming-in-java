package agh.ii.prinjava.lab04.exc04_02;

import agh.ii.prinjava.lab04.exc04_02.impl.MyStackDLLBImpl;
import java.util.NoSuchElementException;


/**
 * A Last-In-First-Out (LIFO) data structure.
 * Elements are pushed onto and popped from the top of the stack.
 *
 * @param <E> the type of elements held in this stack
 */
public interface MyStack<E> {

    /**
     * Removes and returns the element at the top of this stack.
     * @return the top element
     * @throws NoSuchElementException if the stack is empty
     */
    E pop() throws NoSuchElementException;

    /**
     * Pushes an item onto the top of this stack.
     * @param x the item to be pushed
     */
    void push(E x);

    /**
     * Checks if the stack contains no elements.
     * @return true if empty, false otherwise
     */
    default boolean isEmpty() {
        return numOfElems() == 0;
    }

    /**
     * Returns the number of elements in the stack.
     * @return total element count
     */
    int numOfElems();

    /**
     * Looks at the object at the top of this stack without removing it.
     * @return the top element
     * @throws NoSuchElementException if this stack is empty
     */
    E peek() throws NoSuchElementException;

    /** Consider pros and cons of having a factory method in the interface */
    static <T> MyStack<T> create() {
        return new MyStackDLLBImpl<T>();
    }
}
