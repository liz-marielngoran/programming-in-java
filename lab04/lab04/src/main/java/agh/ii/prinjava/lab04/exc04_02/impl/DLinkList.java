package agh.ii.prinjava.lab04.exc04_02.impl;

public class DLinkList<E> {
    private Node<E> first;
    private Node<E> last;
    private int size = 0;

    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        Node(T value) {
            this.value = value;
        }
    }

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

    public E removeFirst() {
        if (first == null) return null;
        E val = first.value;
        first = first.next;
        if (first == null) {
            last = null;
        } else {
            first.prev = null;
        }
        size--;
        return val;
    }

    public E getFirst() {
        return (first == null) ? null : first.value;
    }

    public int size() {
        return size;
    }
