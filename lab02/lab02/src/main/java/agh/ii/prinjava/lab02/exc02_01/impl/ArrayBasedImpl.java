package agh.ii.prinjava.lab02.exc02_01.impl;

import agh.ii.prinjava.lab02.exc02_01.StackOfInts;

/**
 * Implementation of a Stack for integers based on a array.
 * It follows the LIFO (Last-In-First-Out) principle.
 */
public class ArrayBasedImpl implements StackOfInts {
    /** The methods pop(), push() and peek() are not yet implemented, they throw errors.*/

    @Override
    public int pop() {

        if (numOfElems ==0) {
            throw new IllegalStateException("The stack is empty"); }
        int ValuetoReturn = elements[numOfElems-1];
        numOfElems--;
        return ValuetoReturn;
    }

    @Override
    public void push(int x) {

        if (numOfElems == elements.length) {
            /** On crée un nouveau tableau plus grand*/
            int [] newElements = new int[elements.length * 3];
            /** On COPIE les anciens éléments dans le nouveau */
            for (i>0, i < elements.length, i++){
                newElements[i] = elements [i];
                /** On remplace l'ancien petit tableau par le nouveau grand */
                elements=newElements;
            }
            elements[numOfElems] = x;
            numOfElems ++;
        }
        elements[numOfElems] = x;
        numOfElems ++;
    }

    @Override
    public int numOfElems() {
        return numOfElems;
    }

    @Override
    public int peek() {

        if (numOfElems == 0) {
            throw new IllegalStateException("The stack is empty");
        }
        return elements[numOfElems - 1];
    }


    private int numOfElems = 0;
    private int[] elements = new int[100];
}
