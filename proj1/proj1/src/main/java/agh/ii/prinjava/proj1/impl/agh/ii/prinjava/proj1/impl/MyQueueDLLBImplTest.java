package impl.agh.ii.prinjava.proj1.impl;

import impl.src.main.java.agh.ii.prinjava.proj1.impl.MyQueueDLLBImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class MyQueueDLLBImplTest {

    MyQueueDLLBImpl<Integer> queue;

    /**
     * Initializes a fresh queue before each test.
     */
    @BeforeEach
    void setUp() { queue = new MyQueueDLLBImpl<>(); }

    /**
     * Cleans up the queue after each test.
     */
    @AfterEach
    void tearDown() { queue = null; }

    /**
     * Tests that enqueue adds an element to the end of the queue.
     */
    @Test
    void enqueue() {
        queue.enqueue(1);
        assertEquals(1, queue.peek());
    }

    /**
     * Tests that dequeue removes and returns the first element.
     */
    @Test
    void dequeue() {
        queue.enqueue(1);
        assertEquals(1, queue.dequeue());
    }

    /**
     * Tests that peek returns the front element without removing it.
     */
    @Test
    void peek() {
        queue.enqueue(1);
        assertEquals(1, queue.peek());
        assertEquals(1, queue.numOfElems()); // still there
    }

    /**
     * Tests that numOfElems returns the correct size.
     */
    @Test
    void numOfElems() {
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(2, queue.numOfElems());
    }

    /**
     * Tests that dequeue on empty queue throws NoSuchElementException.
     */
    @Test
    void dequeueEmptyThrows() {
        assertThrows(NoSuchElementException.class, () -> queue.dequeue());
    }
}