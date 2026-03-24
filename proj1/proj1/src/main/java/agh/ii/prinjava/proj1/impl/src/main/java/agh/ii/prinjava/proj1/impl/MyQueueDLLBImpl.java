package impl.src.main.java.agh.ii.prinjava.proj1.impl;

import src.main.java.agh.ii.prinjava.proj1.MyQueue;
import impl.src.main.java.agh.ii.prinjava.proj1.impl.DLinkList;
import java.util.NoSuchElementException;

public class MyQueueDLLBImpl<E> implements MyQueue<E> {
    private DLinkList<E> elems;

    @Override
    public void enqueue(E x) {
        elems.addLast(x);
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return elems.removeFirst();
    }

    @Override
    public int numOfElems() {
        return elems.getSize();
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return elems.getFirst().elem;
    }
}
