package agh.ii.prinjava.lab04.exc04_02.impl;

import agh.ii.prinjava.lab04.exc04_02.MyStack;
import java.util.NoSuchElementException;

/**
 * Implementation of a {@link MyStack} using a Doubly Linked List.
 * Push and Pop operations target the head of the list for O(1) performance.
 */
public class MyStackDLLBImpl<E> implements MyStack<E> {
    private DLinkList<E> elems= new DLinkList<>();

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
        return elems.size();
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return elems.getFirst();
    }
}

