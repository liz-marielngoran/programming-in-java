package agh.ii.prinjava.lab02.exc02_01;

/**
 * Stack of integers - Abstract Data Type (ADT)
 * This interface implements all the possible operations on a stack
 */

public interface StackOfInts {
    int pop(); /** This function allows to remove and return the top element fron the stack */

    void push(int x); /** This function allows to add an element on the stack's top*/

    default boolean isEmpty() {
        return numOfElems() == 0;
    } /** This function allows to verify if the Stack is empty */

    int numOfElems(); /** This function allows to give the number of elements of the stacks */

    int peek(); /** This function allows to return the top/last element on the stack*/
}
