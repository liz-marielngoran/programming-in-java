package agh.ii.prinjava.lab02.lst02_11;

/**
 * <ul>
 *     <li>In functional languages, a lambda expression is a convenient way to create an anonymous
 *         (i.e. not named) function</li>
 *     <li>Since in Java we have only methods (i.e., member functions of interfaces or classes),
 *         a lambda expression is just a convenient way (syntactic sugar) to create
 *         an instance of an anonymous class that implements a functional interface</li>
 * </ul>
 *
 * @since Java 8
 */
@FunctionalInterface
interface I1 {
    void apply();
}