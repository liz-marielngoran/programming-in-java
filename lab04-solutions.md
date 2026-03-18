# Summary of Lab04

## 2) Generic methods, classes, and interfaces

### 1)  Benefits of using generic types
Generics allow classes, methods, and interfaces to operate on different data types while keeping the code type-safe.
Essentially, they act as "templates" where the specific data type is decided only when the code is used. They are safe and allows the code to be reusable.
Using generics like List<Price> immediately tells other developers what the collection is intended to hold, making the codebase easier to maintain and navigate.
Moreover, The compiler checks that you are using the correct data type while you are writing the code. This prevents Runtime Crashes by catching errors before the program even runs.

### 2) Meaning of syntax

- generic class declaration: is with a type parameter section after the class name. This allows the class to handle different data types while maintaining compile-time type safety.
````
public class Pair<T, U> {
    private T first;
    private U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() { return first; }
    public U getSecond() { return second; }
}
````
- generic method declaration: is introducing their own type parameters, which are scoped only to that method.
```
public <T> returnType methodName(T parameterName) {
    // Method body
}
```
### 3)  Raw type, unsafety, Use in Java
A raw type is the name of a generic class or interface without any type arguments.
Raw types are unsafe because they bypass generic type checks, deferring the catch of unsafe code to runtime.
In fact The compiler no longer knows what is inside your collection. It treats everything as a plain Object. Since the compiler can't help you, you might accidentally put an Integer into a list you thought was only for Strings. You won't find out until the program crashes with a ClassCastException at runtime.

Although, they are still in use because:
- You must use List.class, as List<String>.class is not legal.
- of type erasure, you usually check if (obj instanceof List) rather than specifying the generic type.

### 4) Explanations of the compilation results

#### a)
There is a yellow wavy underline on GenBox gb1. It will compile, and it can be run, but we notice that gb1 accepts absolutely anything (Strings, Booleans, Integers) because it is a raw type.

#### b)
There is a red wavy underline on gb2.setX("abc"). We cannot run the program until you either delete this line or change "abc" to an integer.

### 5)Completion

````
public class Main {
    public static void main(String[] args ) {
        Integer[] ints = {1, 2, 3};
        String[] strs = {"A", "B", "C"};

        print(ints);
        print(strs);
    }

    public static <T> void print(T[] elems) { // completed line
    for (int i = 0; i < elems.length; i++)
        System.out.print(elems[i] + " ");
    System.out.println();
    }
}   
````

### 7) Implementation

````
package agh.ii.prinjava.lab04.exc04_01;

public class Pair<F,S> implements Cloneable {
    private F first;
    private S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }
    public void setFirst(F first) {
        this.first = first;
    }
    public S getSecond() {
        return second;
    }
    public void setSecond(S second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "Pair{" + "first=" + first + ", second=" + second + '}';
    }

    @Override
    public boolean equals(Object o) {
        // 1. Check if they are the exact same instance in memory
        if (this == o) return true;

        // 2. Check if the other object is null or a different class
        if (o == null || getClass() != o.getClass()) return false;

        // 3. Cast the object to Pair (using raw type here is okay for the check)
        Pair<?, ?> pair = (Pair<?, ?>) o;

        // 4. Compare both fields (handling potential nulls)
        if (first != null ? !first.equals(pair.first) : pair.first != null) return false;
        return second != null ? second.equals(pair.second) : pair.second == null;
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        // Multiply by a prime number (like 31) to reduce collisions
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }

    @Override
    public Pair<F, S> clone() {
        try {
            // Casting the Object returned by super.clone() to our specific Pair type
            return (Pair<F, S>) super.clone();
        } catch (CloneNotSupportedException e) {
            // This should never happen since we implement Cloneable
            throw new AssertionError();
        }
    }

}
````

## 3) Bounds for type variables

### 1) Purpose of bounds for type variables
In Java, type bounds are used to restrict which types can be passed as arguments to a generic class or method.

### 2) Interfaces bounds and Classes ones

A type variable can have many interface bounds. 
This is used when you need a generic type to support multiple behaviors (e.g., being both Comparable and Serializable)

A type variable cannot have many class bounds. 
You can only extend one class. This follows Java’s general rule of single inheritance, a class cannot have more than one parent.

If you have both a class bound and interface bounds, the class must come first.


### 3) Generic function

````
public interface Moveable {
    void goTo(double x, double y);
}
````
````
// The <T extends Moveable> part is the "Bound"
private static <T extends Moveable> void moveAll(T[] elems, double x, double y) {
    for (var e : elems) {
        e.goTo(x, y);
    }
}
````

## 4) Subtyping and Wildcards

### 1) Explanations

Invariance means there is no subtyping relationship between the generic types, regardless of the relationship between the base types.
If a generic is invariant, List<Dog> is not a List<Animal>, and List<Animal> is not a List<Dog>

Covariance allows the generic type to follow the same direction as the subtyping of its components. The relationship is preserved.
If Dog < Animal, then Producer<Dog> < Producer<Animal>

Contravariance: is the inverse. The subtyping relationship of the generic type is the opposite of the component types. If Dog < Animal, then Consumer<Animal> < Consumer<Dog>. The relationship is reversed.

### 2) Explanations

Subtype wildcard: uses the extends keyword. It specifies that the type must be a specific class or any of its subclasses. This represents Covariance

Supertype wildcard: uses the super keyword. It specifies that the type must be a specific class or any of its parent classes (superclasses). uses the super keyword. 
It specifies that the type must be a specific class or any of its parent classes (superclasses).

Unbounded wildcard: uses only the question mark. It stands for "a type of which I know nothing."

### 3) Compilation errors and explanations
````
GenBox<B> gb1 = new GenBox<B>(); 
GenBox<B> gb2 = new GenBox<C>(); //Incompatible types because Generics are invariant
GenBox<B> gb3 = new GenBox<A>(); //Incompatible types because Generics are invariant
B b1 = gb1.getX();
gb1.setX(new B());

GenBox<? extends B> gb4 = new GenBox<B>(); 
GenBox<? extends B> gb5 = new GenBox<C>();
GenBox<? extends B> gb6 = new GenBox<A>(); //Incompatible types because ? extends B means "B or any subclass of B." Since A is a superclass of B, it falls outside this range.
B b2 = gb5.getX();
gb5.setX(new B()); // The method setX(capture# of ? extends B) is not applicable. The compiler doesn't know if gb5 is actually a GenBox<B> or a GenBox<C>
gb5.setX(new C()); // same above

GenBox<? super B> gb7 = new GenBox<B>();
GenBox<? super B> gb8 = new GenBox<C>(); //Incompatible types. ? super B means "B or any superclass of B." Since C is a subclass, it doesn't satisfy the lower bound.
GenBox<? super B> gb9 = new GenBox<A>(); 
B b3 = gb9.getX(); //Type mismatch. gb9.getX() could return an A (the superclass)
gb9.setX(new B());
gb9.setX(new C());

GenBox<?> gb10 = new GenBox<B>(); //Type mismatch. GenBox<?> is effectively GenBox<? extends Object>. The compiler has no guarantee that the object inside is a B; it only knows it's an Object.
GenBox<?> gb11 = new GenBox<C>();
GenBox<?> gb12 = new GenBox<A>();
B b4 = gb10.getX();
gb10.setX(new B()); //The method setX(capture# of ?) is not applicable. An unbounded wildcard makes the box "read-only" (for specific types). Since the compiler doesn't know the specific type the box requires, it won't let you insert anything
````

## 5) Mini project 04_01 (exc04_02)

### 1) Augmentation of these interfaces with exception handling

````
import agh.ii.prinjava.lab04.exc04_02.impl.MyQueueDLLBImpl;
import java.util.NoSuchElementException;

public interface MyQueue<E> {
    void enqueue(E x);

    /**
     * @throws NoSuchElementException if the queue is empty
     */
    E dequeue() throws NoSuchElementException;

    default boolean isEmpty() {
        return numOfElems() == 0;
    }

    int numOfElems();

    /**
     * @throws NoSuchElementException if the queue is empty
     */
    E peek() throws NoSuchElementException;

    /** Consider pros and cons of having a factory method in the interface */
    static <T> MyQueue<T> create() {
        return new MyQueueDLLBImpl<>();
    }
}
````

````
package agh.ii.prinjava.lab04.exc04_02;

import agh.ii.prinjava.lab04.exc04_02.impl.MyStackDLLBImpl;
import java.util.NoSuchElementException;

public interface MyStack<E> {

    /**
     * @throws NoSuchElementException if the stack is empty
     */
    E pop() throws NoSuchElementException;

    void push(E x);

    default boolean isEmpty() {
        return numOfElems() == 0;
    }

    int numOfElems();

    /**
     * @throws NoSuchElementException if the stack is empty
     */
    E peek() throws NoSuchElementException;

    /** Consider pros and cons of having a factory method in the interface */
    static <T> MyStack<T> create() {
        return new MyStackDLLBImpl<T>();
    }
}

````

### 2) Code completion

````
public class MyStackDLLBImpl<E> implements MyStack<E> {
    private DLinkList<E> elems= new DLinkList<>();

    @Override
    public E pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return elems.removeFirst();
    }

    @Override
    public void push(E x) {
        elems.addFirst(x);
    }

    @Override
    public int numOfElems() {
        throw new IllegalStateException("To be implemented");
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return elems.getFirst();
    }
}
````

````
public class MyQueueDLLBImpl<E> implements MyQueue<E> {
    private DLinkList<E> elems = new DLinkList<>();

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
        return elems.size();
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return elems.getFirst();
    }
}
````
### 3)JavaDoc comments to the interfaces and classes

````
/**
 * A First-In-First-Out (FIFO) data structure.
 * Elements are inserted at the end and removed from the beginning.
 *
 * @param <E> the type of elements held in this queue
 */
public interface MyQueue<E> {

    /**
     * Inserts the specified element at the end of the queue.
     * @param x the element to add
     */
    void enqueue(E x);

    /**
     * Retrieves and removes the head of this queue.
     * @return the head of this queue
     * @throws NoSuchElementException if the queue is empty
     */
    E dequeue() throws NoSuchElementException;


    /**
     * Checks if the queue contains no elements.
     * @return true if the queue is empty, false otherwise
     */
    default boolean isEmpty() {
        return numOfElems() == 0;
    }

    /**
     * Returns the number of elements currently in the queue.
     * @return total element count
     */
    int numOfElems();

    /**
     * Retrieves, but does not remove, the head of this queue.
     * @return the head of this queue
     * @throws NoSuchElementException if the queue is empty
     */
    E peek() throws NoSuchElementException;

    /** Consider pros and cons of having a factory method in the interface */
    static <T> MyQueue<T> create() {
        return new MyQueueDLLBImpl<>();
    }
}

````
````
package agh.ii.prinjava.lab04.exc04_02;

import agh.ii.prinjava.lab04.exc04_02.impl.MyStackDLLBImpl;
import java.util.NoSuchElementException;


/**
 * A Last-In-First-Out (LIFO) data structure.
 * Elements are pushed onto and popped from the top of the stack.
 *
 * @param <E> the type of elements held in this stack
 */
public interface MyStack<E> {

    /**
     * Removes and returns the element at the top of this stack.
     * @return the top element
     * @throws NoSuchElementException if the stack is empty
     */
    E pop() throws NoSuchElementException;

    /**
     * Pushes an item onto the top of this stack.
     * @param x the item to be pushed
     */
    void push(E x);

    /**
     * Checks if the stack contains no elements.
     * @return true if empty, false otherwise
     */
    default boolean isEmpty() {
        return numOfElems() == 0;
    }

    /**
     * Returns the number of elements in the stack.
     * @return total element count
     */
    int numOfElems();

    /**
     * Looks at the object at the top of this stack without removing it.
     * @return the top element
     * @throws NoSuchElementException if this stack is empty
     */
    E peek() throws NoSuchElementException;

    /** Consider pros and cons of having a factory method in the interface */
    static <T> MyStack<T> create() {
        return new MyStackDLLBImpl<T>();
    }
}
````

````
/**
 * Implementation of a {@link MyQueue} using a Doubly Linked List.
 * Provides O(1) time complexity for enqueue and dequeue operations.
 */
public class MyQueueDLLBImpl<E> implements MyQueue<E> {
    private DLinkList<E> elems = new DLinkList<>();

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
        return elems.size();
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return elems.getFirst();
    }
}
````

````
/**
 * Implementation of a {@link MyStack} using a Doubly Linked List.
 * Push and Pop operations target the head of the list for O(1) performance.
 */
public class MyStackDLLBImpl<E> implements MyStack<E> {
    private DLinkList<E> elems= new DLinkList<>();

    @Override
    public E pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return elems.removeFirst();
    }

    @Override
    public void push(E x) {
        elems.addFirst(x);
    }

    @Override
    public int numOfElems() {
        throw new IllegalStateException("To be implemented");
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return elems.getFirst();
    }
}
````


