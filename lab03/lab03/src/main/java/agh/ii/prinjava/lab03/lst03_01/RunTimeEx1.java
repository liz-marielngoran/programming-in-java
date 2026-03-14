package agh.ii.prinjava.lab03.lst03_01;

/**
 * {@link RuntimeException} is the superclass of those exceptions that can be thrown
 * during the normal operation of the Java Virtual Machine.
 *
 * <p>{@code RuntimeException} and its subclasses are unchecked exceptions.
 * Unchecked exceptions do not need to be declared in a method or constructor's throws clause
 * if they can be thrown by the execution of the method or constructor and propagate
 * outside the method or constructor boundary.
 *
 * <p>Runtime exceptions can occur anywhere in a program, and in a typical one they can be numerous.
 * Having to add runtime exceptions in every method declaration would reduce a program's clarity.
 * Thus, the compiler does not require that you catch or specify runtime exceptions (although you can).
 *
 * <p>One case where it is common practice to throw a {@code RuntimeException} is when the user calls a method
 * incorrectly. For example, a method can check if one of its arguments is incorrectly null.
 * If an argument is null, the method might throw a NullPointerException, which is an unchecked exception.
 *
 * <p>Generally speaking, do not throw a {@code RuntimeException} or create a subclass of {@code RuntimeException}
 * simply because you don't want to be bothered with specifying the exceptions your methods can throw.
 *
 * <p>Here's the bottom line: If a client can reasonably be expected to recover from an exception,
 * make it a checked exception. If a client cannot do anything to recover from the exception,
 * make it an unchecked exception.
 *
 * @see <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Throwable.html">Throwable</a>
 * @see <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Exception.html">Exception</a>
 * @see <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/RuntimeException.html">RuntimeException</a>
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/exceptions/runtime.html">Unchecked Exceptions-the Controversy</a>
 */
class RunTimeEx1 extends RuntimeException {
    public RunTimeEx1() {
        super("RunTimeEx1 occurred");
    }
}