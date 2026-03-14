package agh.ii.prinjava.lab03.lst03_01;

// Examples of user-defined exceptions/errors

/**
 * An {@link Error} is a subclass of {@code Throwable} that indicates serious problems that a reasonable
 * application should not try to catch.
 *
 * <p>The class Error is a separate subclass of {@link Throwable}, distinct from {@link Exception} in the class
 * hierarchy, to allow programs to use the idiom "} catch (Exception e) {" to catch all exceptions from which
 * recovery may be possible without catching errors from which recovery is typically not possible.
 *
 * <p><i>Note</i>: A method is NOT required to declare, in its {@code throws} clause, any subclasses of {@code Error}
 * that might be thrown during the execution of the method but not caught. Such errors are abnormal
 * conditions that should never occur. That is, {@code Error} and its subclasses are regarded as unchecked
 * exceptions for the purposes of compile-time checking of exceptions.
 *
 * @see <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Throwable.html">Throwable</a>
 * @see <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Error.html">Error</a>
 */
class Error1 extends Error { // A user-defined Error -- you should never do it!!!
    public Error1() {
        super("Error1 occurred");
    }
}