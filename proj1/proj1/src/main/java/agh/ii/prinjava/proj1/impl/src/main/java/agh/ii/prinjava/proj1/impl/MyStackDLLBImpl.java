package impl;

import src.main.java.agh.ii.prinjava.proj1.MyStack;
import impl.src.main.java.agh.ii.prinjava.proj1.impl.DLinkList;
import java.util.NoSuchElementException;

/**
 * Implementation of a {@link MyStack} using a Doubly Linked List.
 * Push and Pop operations target the head of the list for O(1) performance.
 */
public class MyStackDLLBImpl<E> implements MyStack<E> {
    private DLinkList<E> elems;

    @Override
    public E pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return elems.removeFirst();
    }

    @Override
    public void push(E x) {
        elems.addFirst(x);
    }

    @Override
    public int numOfElems() {
        return elems.getSize();
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return elems.getFirst().elem;
    }
}
