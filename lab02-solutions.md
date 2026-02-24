# Summary of Lab02

## 1)Final variables, methods and classes

### 1)looking

### 2) Benefits of using constants
Using constants in programming allows keep a value, the same, along the program runs.

### 3) Meaning of "final"

- Local variables: these variables will be constant only during the running of the methods or voids where they are declared. 
- Instance Variable: it is a variable declared as constant for all the instance of the class.
- static constants: it is a global constant for the class.
- methods: it is a methods that is constant; its definition remains the same for all the instance.
- classes: a final class is a class whose fields are final.

### 4) Questions
 #### 4.1)If a class contains only private data fields and no setter methods, is the class immutable?
The class is not immutable. In fact, if we have getter methods it will return the reference of the object and we could create methods to modify it. For instance add an element to a list.

#### 4.2)If all the data fields in a class are private and of primitive types, and the class does not contain any setter methods, is the class immutable?
, the class is immutable because data are of primitive types. In fact, even if we have a get method it will return a copy of the value of the object not its real address, in sort that it could be found and set.

### 5)Immutability?
Values is a table. As we have a get method, it will return a pointer on its address. So we could easily add element to the tab.

### 6) Refactoring

## 2) Enumeration classes

### 1) Looking

### 2) Reading Java.lang.Enum class

### 3) Implementation of Singleton
    It is not a correct implementation because there is no constructors so the default contructor will be public, not safe for an enumuration because anyone could modify the list-enumeration.

### 4) Refactoring

## 3) Nested classes

### 1) looking

### 2) Differences between types of Nested classes
We do have static member class and inner class.
Static member class is a nested class that is accessible without creating an instance of the outerclass, instead of inner class that, if they're public, could only be accessed by creating an instance of. 
### 3)Questions
    
#### 3.1)Can an inner class be used in a class other than the class in which it nests?
No because an inner class is a non-static nested class.
#### 3.2)Can the modifiers public, protected, private, and static be used for inner classes?
Yes except static because an inner class is a non static nested class.

### 4) Refactoring

## 4)Abstract Data Types

### 1) Looking

### 2)Differences between abstract classes and interfaces in Java
We can implement many interfaces but not inherit of many abstract classes.
Abstract classes can have fields instead of interfaces
We can have non-abstract methods in abstract classes.
### 3)Possible members types of java interfaces
- Public constants
- public abstract methods
### 4)Correct definition of abstract class

```
a) public class abstract B { abstract void m1(); }
b)abstract class D { protected void m1(); }
c) abstract class E { abstract void m1(); }
```

### 5)Correct definition of interfaces
```
interface D { void m1(); }
```
### 6)output of main
```
b is an instance of C
```
### 7) Refactoring

## 5)Functional interface and lambda expressions

### 1) Looking

### 2) Relationship between lambda functions and functional interfaces
They are complementary. We use lambda expressions to instantiate functional interfaces. And as its contains only one method's definition, the compilator implicitly knows that we will implement this one with the lambda expressions.
### 3)Functional interfaces

#### a)void→int
```
@FunctionInterface
interface FI1 {
    int A();
    
FI2 func1 = () -> x*3
```

#### b)$int \rightarrow void$
```
@FunctionInterface
interface FI2 {
    void B(int x);
    
FI2 func2 = (x) -> { System.out.println("Func2.m1()")}
```

#### c)$int \rightarrow int$

```
@FunctionInterface
interface FI3 {
    int C(int x);
    
FI2 func3 = (x) -> 2*x
```

#### d)$(int, int) \rightarrow void$

```
@FunctionInterface
interface FI4 {
    void D(int x, int y);
    
FI2 func4 = (x,y) -> { System.out.println(x+y)}
```

### 4)Refactoring

## 6)Mini project

### 1)Interface's comments

```
/* This interface implements all the possible operations on a stack */
public interface StackOfInts {
    int pop(); /** This function allows to remove and return the top element from the stack */

    void push(int x); /** This function allows to add an element on the stack's top*/

    default boolean isEmpty() {
        return numOfElems() == 0;
    } /** This function allows to verify if the Stack is empty */

    int numOfElems(); /** This function returns the current number of elements of the stacks */

    int peek(); /** This function allows to only return the top/last element on the stack*/
}
````

### 2) comments to LinkedListBasedImpl 
```
package agh.ii.prinjava.lab02.exc02_01.impl;

import agh.ii.prinjava.lab02.exc02_01.StackOfInts;


/**
 * Implementation of a Stack for integers based on a linked list.
 * It follows the LIFO (Last-In-First-Out) principle.
 */
public class LinkedListBasedImpl implements StackOfInts {
/** The methods pop(), push() and peek() are not yet implemented, they throw errors.*/
    @Override
    public int pop() {
        throw new IllegalStateException("To be implemented");
    }

    @Override
    public void push(int x) {
        throw new IllegalStateException("To be implemented");
    }

    @Override
    public int numOfElems() {
        return numOfElems;
    }

    @Override
    public int peek() {
        throw new IllegalStateException("To be implemented");
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

```
## 3) the linked list based implementation

```
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
        this.numOfElems ++

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
```
## 5)

```
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
        throw new IllegalStateException("To be implemented");
    }

    @Override
    public void push(int x) {
        throw new IllegalStateException("To be implemented");
    }

    @Override
    public int numOfElems() {
        return numOfElems;
    }

    @Override
    public int peek() {
        throw new IllegalStateException("To be implemented");
    }

    private int numOfElems = 0;
}
```

## 7)
```
package agh.ii.prinjava.lab02.exc02_01.impl;

import agh.ii.prinjava.lab02.exc02_01.StackOfInts;

/**
 * Implementation of a Stack for integers based on a array.
 * It follows the LIFO (Last-In-First-Out) principle.
 */
public class ArrayBasedImpl implements StackOfInts {

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
            for (i>0; i < elements.length; i++){
                newElements[i] = elements [i];
                /** On remplace l'ancien petit tableau par le nouveau grand */
                elements=newElements;
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
```




