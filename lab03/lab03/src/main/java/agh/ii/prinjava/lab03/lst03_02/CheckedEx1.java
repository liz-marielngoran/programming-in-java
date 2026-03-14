package agh.ii.prinjava.lab03.lst03_02;
/**
 * You can define a custom exception class by extending the java.lang.Exception class.
 *
 * @see <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Exception.html">Exception</a>
 */
class CheckedEx1 extends Exception {
    public CheckedEx1() {
        super("CheckedEx1 occurred");
    }
}