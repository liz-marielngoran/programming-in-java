package agh.ii.prinjava.lab08.lst08_03;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

interface ISequenceGen {
    int getNext();
}

public class Main {

    private static void demo1() {
        System.out.println("demo1...");
        ISequenceGen dataSrc = new UnsafeSequenceGen();
        testSequenceGen(dataSrc);
    }

    private static void demo2() {
        System.out.println("\ndemo2...");
        ISequenceGen dataSrc = new SafeSequenceGen1();
        testSequenceGen(dataSrc);
    }

    private static void demo3() {
        System.out.println("\ndemo3...");
        ISequenceGen dataSrc = new SafeSequenceGen2();
        testSequenceGen(dataSrc);
    }

    private static void demo4() {
        System.out.println("\ndemo4...");
        ISequenceGen dataSrc = new SafeSequenceGen3();
        testSequenceGen(dataSrc);
    }

    /**
     * A <em>race condition</em> occurs when (at least) two threads try to read and write the same data
     *
     * <p>It is possible that two threads could call {@link UnsafeSequenceGen#getNext getNext} and receive the same value!
     */
    private static void testSequenceGen(ISequenceGen dataSrc) {
        Collection<Integer> dataSink = Collections.synchronizedList(new ArrayList<>());

        Runnable seqReader = () -> {
            for (int i = 0; i < 1000; i++) {
                dataSink.add(dataSrc.getNext());
            }
        };

        Thread t1 = new Thread(seqReader);
        Thread t2 = new Thread(seqReader);
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Map<Integer, Long> hist = dataSink.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()));

        hist.entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .forEach(e -> System.out.println(e.getKey() + " => " + e.getValue()));
    }

    private static void demo5() {
        System.out.println("\ndemo5...");
        DeadlockDemo dld = new DeadlockDemo();

        Runnable r1 = () -> {
            while (true) {
                dld.m1();
            }
        };

        Runnable r2 = () -> {
            while (true) {
                dld.m2();
            }
        };

        Thread t1 = new Thread(r1, "t1");
        Thread t2 = new Thread(r2, "t2");

        t1.start();
        t2.start();
    }

    public static void main(String[] args) {
        demo1();
        demo2();
        demo3();
        demo4();
        //demo5(); // <- deadlock !
    }
}

/**
 * Threads communicate primarily by sharing access to fields and the objects reference fields refer to.
 * This form of communication is extremely efficient, but makes two kinds of errors possible:
 * <em>thread interference</em> and <em>memory consistency errors</em>.
 * The tool needed to prevent these errors is <em>synchronization</em>
 *
 * <p>{@code value++} may appear to be a single operation, but is in fact three separate operations:
 * <ol>
 *     <li>read the value</li>
 *     <li>add one to it</li>
 *     <li>write out the new value</li>
 * </ol>
 * Since operations in multiple threads may be arbitrarily interleaved by the runtime, it is possible for two threads
 * to read the value at the same time, both see the same value, and then both add one to it.
 * The result is that the same sequence number is returned from multiple calls in different threads.
 *
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/interfere.html">Thread Interference</a>
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/memconsist.html">Memory Consistency Errors</a>
 */
class UnsafeSequenceGen implements ISequenceGen {
    private int value;

    @Override
    public int getNext() {
        return value++;
    }
}

/**
 * Java provides two basic synchronization idioms:
 * <ul>
 *     <li>synchronized methods (see {@link SafeSequenceGen1#getNext getNext})</li>
 *     <li>synchronized statements</li>
 * </ul>
 *
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/sync.html">Synchronization</a>
 */
class SafeSequenceGen1 implements ISequenceGen {
    private int value;

    /**
     * To make a method {@code synchronized}, simply add the synchronized keyword to its declaration.
     * <p>
     * Making a method synchronized has two effects:
     * <ul>
     *     <li>It is not possible for two invocations of synchronized methods on the same object to interleave.
     *      When one thread is executing a synchronized method for an object, all other threads that invoke synchronized
     *      methods for the same object block (suspend execution) until the first thread is done with the object</li>
     *
     *      <li>When a synchronized method exits, it automatically establishes a happens-before relationship
     *      with any subsequent invocation of a synchronized method for the same object.
     *      This guarantees that changes to the state of the object are visible to all threads</li>
     * </ul>
     *
     * <i>Note</i>: synchronized methods enable a simple strategy for preventing thread interference and memory
     * consistency errors: if an object is visible to more than one thread, all reads or writes to that object's
     * variables are done through synchronized methods
     *
     * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html">Synchronized Methods</a>
     */
    @Override
    public synchronized int getNext() {
        return value++;
    }
}

/**
 * As above, but with the use of the <em>synchronized statement</em>.
 *
 * <p>Notes:
 * <ul>
 *     <li>Synchronization is built around an internal entity known as the <em>intrinsic lock</em> or <em>monitor lock</em>
 *     (the API specification often refers to this entity simply as a <em>monitor</em>)</li>
 *
 *     <li>Every object has an intrinsic lock associated with it. By convention, a thread that needs exclusive and
 *     consistent access to an object's fields has to acquire the object's intrinsic lock before accessing them,
 *     and then release the intrinsic lock when it's done with them</li>
 * </ul>
 *
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/locksync.html">Intrinsic Locks and Synchronizationś</a>
 */
class SafeSequenceGen2 implements ISequenceGen {
    private int value;

    @Override
    public int getNext() {
        // synchronized statement
        synchronized (this) {
            return value++;
        }
    }
}

/**
 * An atomic action is one that effectively happens all at once. An atomic action cannot stop in the middle:
 * it either happens completely, or it doesn't happen at all. No side effects of an atomic action are visible
 * until the action is complete.
 *
 * <p>{@link java.util.concurrent.atomic} - a small toolkit of classes that support lock-free thread-safe programming
 * on single variables.
 *
 * <p>{@link AtomicInteger} an int value that may be updated atomically.
 * It is used in applications such as atomically incremented counters.
 *
 * @see <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/atomic/AtomicInteger.html">AtomicInteger</a>
 * @see <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/atomic/AtomicLong.html">AtomicLong</a>
 * @see <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/atomic/AtomicBoolean.html">AtomicBoolean</a>
 * @see <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/atomic/AtomicReference.html">AtomicReference</a>
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/atomic/package-summary.html">java.util.concurrent.atomic</a>
 * @see <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/invoke/VarHandle.html">VarHandle</a>
 */
class SafeSequenceGen3 implements ISequenceGen {
    AtomicInteger value = new AtomicInteger(0);

    @Override
    public int getNext() {
        return value.getAndIncrement();

    }
}

/**
 * <em>Liveness</em> - a concurrent application's ability to execute in a timely manner
 *
 * <p><em>Deadlock</em> (the most common kind of liveness problem) describes a situation where two or more threads
 * are blocked forever, waiting for each other
 *
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/deadlock.html">Deadlock</a>
 */
class DeadlockDemo {
    private final Object o1 = new Object();
    private final Object o2 = new Object();

    /**
     * Note the order of the synchronization blocks (compare to {@link DeadlockDemo#m2 m2})
     */
    public void m1() {
        System.out.println("m1: (1)");
        synchronized (o1) {
            System.out.println("m1: (2)");
            synchronized (o2) { // <- Deadlock!
                System.out.println("m1: (3)");
            }
        }
    }

    /**
     * Note that {@code o1} and {@code o2} are reversed (when compared to {@link DeadlockDemo#m1 m1})
     */
    public void m2() {
        System.out.println("m2: (1)");
        synchronized (o2) {
            System.out.println("m2: (2)");
            synchronized (o1) { // Deadlock
                System.out.println("m2: (3)");
            }
        }
    }
}
