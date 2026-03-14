package agh.ii.prinjava.lab03.lst03_04;

/**
 * Any object that implements {@link java.lang.AutoCloseable}, which includes all objects which implement
 * {@link java.io.Closeable}, can be used as a <i>resource</i> in try-with-resources statement:
 *
 * <p><pre>
 * try (declare and create resources) {
 *   // Use the resource to process the file;
 * }
 * </pre>
 *
 * @see <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/AutoCloseable.html">AutoCloseable</a>
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se17/html/jls-14.html#jls-14.20.3">try-with-resources</a>
 * @see <a href="https://www.yegor256.com/2017/08/08/raii-in-java.html">RAII in Java</a>
 */
class Resource1 implements AutoCloseable {

    Resource1() {
        System.out.println("Resource1: the resource is being opened...");
    }

    /**
     * Note: although the close method from {@link AutoCloseable} interface throws {@link Exception},
     * we can throw NO exception at all as the methods are covariant with respect to the exceptions
     */
    @Override
    public void close() {
        System.out.println("Resource1.close()");
    }

    public void doStuff() throws ChEx1 {
        System.out.println("Resource1.doStuff()");
        throw new ChEx1();
    }
}