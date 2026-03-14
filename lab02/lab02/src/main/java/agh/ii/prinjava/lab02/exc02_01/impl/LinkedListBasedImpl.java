package agh.ii.prinjava.lab02.exc02_01.impl;

import agh.ii.prinjava.lab02.exc02_01.StackOfInts;


/**
 * Implementation of a Stack for integers based on a linked list.
 * It follows the LIFO (Last-In-First-Out) principle.
 */
public class LinkedListBasedImpl implements StackOfInts {

    @Override
    public int pop() {

            if (first == null) {
                throw new IllegalStateException("The stack is empty")
            }
        else {
                int valuetoReturn = first.elem;
                first = first.next;
                this.numOfElems --;
                return valuetoReturn
            }

    }

    @Override
    public void push(int x) {

        Node newnode = new Node(x, this.first);
        this.first = newnode;
        numOfElems ++

    }

    @Override
    public int numOfElems() {
        return numOfElems;
    }

    @Override
    public int peek() {
        if (first == null) {
            throw new IllegalStateException("The stack is empty");
        }
        else {
            int valuetoReturn = first.elem;
            return valuetoReturn;

    }

    private static class Node {
        int elem;
        /** Reference to the next node in the sequence. */
        Node next;

        public Node(int elem, Node next) {
            this.elem = elem;
            this.next = next;
        }
    }
    /** Pointer to the first node (the top of the stack). Null if empty. */
    private Node first = null;
    /** Current number of elements in the stack. */
    private int numOfElems = 0;
}
