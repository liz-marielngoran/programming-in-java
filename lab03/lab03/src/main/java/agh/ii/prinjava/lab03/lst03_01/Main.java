package agh.ii.prinjava.lab03.lst03_01;

/**
 * An exception is an event which occurs during the execution of a program
 * that disrupts the normal flow of the program's instructions.
 *
 * <p>Valid Java code must honor the <i>Catch or Specify Requirement (CSR)</i>.
 * This means that code that might throw certain exceptions must be enclosed by either of the following:
 * <ul>
 *     <li>a try statement that catches the exception
 *         (the try must provide a handler for the exception)</li>
 *     <li>a method that specifies that it can throw the exception
 *         (the method must provide a throws clause that lists the exception)</li>
 * </ul>
 * <i>Note</i>: not all exceptions are subject to the <i>CSR</i>
 *
 * <p>The following three kinds/categories of exceptions are considered:
 * <ul>
 *     <li><em>checked exceptions</em> (are subject to the CSR) - exceptional conditions that a well-written application
 *         should anticipate and recover from</li>
 *     <li><em>unchecked exceptions</em> (are not subject to the CSR) - exceptional conditions that the application
 *         usually cannot anticipate or recover from, namely:
 *     <ul>
 *         <li><em>errors</em> - exceptional conditions that are external to the application</li>
 *         <li><em>runtime exceptions</em> - exceptional conditions that are internal to the application;
 *             these usually indicate programming bugs</li>
 *     </ul>
 *     </li>
 * </ul>
 *
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/exceptions/catchOrDeclare.html">The Catch or Specify Requirement</a>
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se17/html/jls-11.html">Exceptions</a>
 */
public class Main {
    /**
     * RuntimeException typically is not specified in the method declaration
     */
    private static void m1() {
        throw new RuntimeException(); // OK, it's a runtime exception ("throws" in the declaration not needed)
    }

    /**
     * A methods CAN but DOESN'T need to handle or specify an unchecked exception.
     * Specification of unchecked exceptions can be done, e.g., for documentation purposes
     * (it appears in the corresponding JavaDoc -> programmers know what to expect)
     *
     * @throws RunTimeEx1 some description
     */
    private static void m2() throws RunTimeEx1 {
        throw new RunTimeEx1();
    }

    /**
     * Runtime exceptions are unchecked, i.e. are not subject to the CSR
     * (throws/try-catch are not "forced" by the compiler)
     */
    private static void demo1() {
        m1();
        m2();
    }

    /**
     * Yet, runtime exceptions can be handled
     */
    private static void demo2() {
        System.out.println("demo2...");
        try {
            m1();
            m2();
        } catch (RuntimeException e) {
            System.out.println("RuntimeException was caught in demo2");
            //e.printStackTrace();
        }
    }

    /**
     * Auxiliary method
     */
    private static int intDiv(int a, int b) {
        return a / b;
    }

    private static void demo3() {
        System.out.println("\ndemo3...");
        try {
            int x = intDiv(3, 0);
            System.out.println("demo3: x = " + x);
        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException was caught in demo3");
        }
    }

    /**
     * {@link Error} and its subclasses are regarded as unchecked exceptions for the purposes
     * of compile-time checking of exceptions (the CSR does not apply)
     *
     * <p><i>Note</i>: you should NEVER throw Errors (they are used by JVM, e.g.,
     * {@link OutOfMemoryError}, {@link StackOverflowError})
     */
    private static void m3() {
        throw new Error1(); // <- NEVER do it :)
    }

    /**
     * Similar to runtime exceptions, a method CAN but DOESN'T need to handle or specify an {@code Error}
     * ({@code Error} and its subclasses are regarded as unchecked exceptions).
     *
     * @throws Error1
     */
    public static void m4() throws Error1 {
        throw new Error1();
    }

    private static void demo4() {
        System.out.println("\ndemo4...");
        try {
            m3();
        } catch (Error1 e) {
            System.out.println("Error1 was caught in demo4");
        }
    }

    private static void demo5() {
        System.out.println("\ndemo5...");
        try {
            m4();
        } catch (Error e) { // Error is the base class for Error1 which is thrown
            System.out.println("Error was caught in demo5");
        }
    }

    public static void main(String[] args) {
        // demo1(); // <- demo1() does not catch the exception thrown by m1()
        demo2();
        demo3();
        demo4();
        demo5();
    }
}




