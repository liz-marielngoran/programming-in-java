package impl.agh.ii.prinjava.proj1.impl;

import org.junit.jupiter.api.AfterEach;
import impl.src.main.java.agh.ii.prinjava.proj1.impl.DLinkList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DLinkList.
 */
class DLinkListTest {
    DLinkList<Integer> dLinkList = new DLinkList<>();

    /**
     * Initializes a fresh DLinkList before each test.
     */
    @BeforeEach
    void setUp() {
        dLinkList = new DLinkList<>();
    }

    /**
     * Cleans up the DLinkList after each test.
     */
    @AfterEach
    void tearDown() {
        dLinkList = null;
    }

    /**
     * Tests that addLast adds an element to the end of the list.
     */
    @Test
    void addLast() {
        dLinkList.addLast(1);
        assertEquals(1, dLinkList.getLast().elem);
    }

    /**
     * Tests that removeFirst removes and returns the first element.
     */
    @Test
    void removeFirst() {
        dLinkList.addFirst(1);
        assertEquals(1, dLinkList.removeFirst());
    }

    /**
     * Tests that the list is empty after initialization.
     */
    @Test
    void isEmpty() {
        assertEquals(0, dLinkList.getSize()); // TODO: empty body — added basic assertion
    }
}