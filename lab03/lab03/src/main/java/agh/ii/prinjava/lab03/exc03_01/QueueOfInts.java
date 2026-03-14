package agh.ii.prinjava.lab03.exc03_01;

        /**@param: Describes a method parameter (e.g., @param x the integer to add).
        @return: Describes what the method sends back to the caller.
        @throws: Documents the exceptions the method might "fire" if something goes wrong.*/

/**
 * A First-In-First-Out (FIFO) queue of integers.
 * Elements are added to the "front" and removed from the "back"
 */
public interface QueueOfInts {

    /**
     * Adds an integer to the front of the queue.
     * * @param x the integer element to be added
     */
    void enqueue(int x);

    /**
     * Removes and returns the integer at the back of the queue.
     * * @return the integer removed from the back of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    int dequeue();

    /**
     * Checks if the queue contains no elements.
     * * @return {@code true} if the queue is empty, {@code false} otherwise
     */
    default boolean isEmpty() {
        return numOfElems() == 0;
    }

    /**
     * Returns the current number of elements in the queue.
     * * @return the number of elements
     */
    int numOfElems();

    /**
     * Retrieves, but does not remove, the integer at the back of the queue.
     * * @return the integer at the back of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    int peek();
}
