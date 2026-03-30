package impl.src.main.java.agh.ii.prinjava.proj1.impl;

import java.util.NoSuchElementException;

public class DLinkList<E> {
    private Node<E> first;
    private Node<E> last;
    private int size = 0;

    public Node<E> getFirst() {
        return first;
    }
    public Node<E> getLast() {
        return last;
    }
    public int getSize() {
        return size;
    }

    public static class Node<E> {
        E elem;
        Node<E> next;
        Node<E> prev;

        Node(E elem) {
            this.elem = elem;
            this.next = null;
            this.prev = null;
        }
    }

        /**
         * Adds an element as the first element of the double linked list.
         * Updates the head pointer and establishes backward links for the doubly linked structure.
         * * @param e the element to be added to the list
         */
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e);
        if (first == null) {
            first = last = newNode;
        } else {
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        }
        size++;
    }

    /**
     * Adds an element as the last of the linked list.
     * Updates the queue pointer and establishes backward links for the doubly linked structure.
     * * @param e the element to be added to the list
     */
    public void addLast(E e) {
        Node<E> newNode = new Node<>(e);
        if (last == null) {
            first = last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    /**
     * Removes the first element of the linked list.
     * * @return the element value of the removed node
     * @throws NoSuchElementException if the queue is empty
     */
    public E removeFirst() {
        if (first == null) return null;
        E val = first.elem;
        first = first.next;
        if (first == null) {
            last = null;
        } else {
            first.prev = null;
        }
        size--;
        return val;
    }

    /**
     * Removes the last element of the linked list.
     * * @return the element value of the removed node
     * @throws NoSuchElementException if the queue is empty
     */
    public E removeLast(){
        if (last == null) return null;
        E val = last.elem;
        last = last.prev;
        if (last == null) {
            first = null;
        }
        else  {
            last.next = null;
        }
        size--;
        return val;
    }

    /**
     * Print the double linked list
     * * @return the element values
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        Node<E> current = first;

        while (current != null) {
            result.append(current.elem);
            if (current.next != null) {
                result.append(" <-> ");
            }
            current = current.next;
        }

        result.append("]");
        return result.toString();
    }
}

