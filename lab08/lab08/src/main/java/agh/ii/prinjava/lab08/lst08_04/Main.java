package agh.ii.prinjava.lab08.lst08_04;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The <i>fork/join framework</i> is an implementation of the {@link ExecutorService} interface and is designed
 * for work that can be broken into smaller pieces recursively.
 * As with any {@code ExecutorService} implementation, the fork/join framework distributes tasks to worker threads
 * in a thread pool. The fork/join framework uses a <i>work-stealing algorithm</i>.
 *
 * <p>A ForkJoinTask is a thread-like entity that is much lighter than a normal thread.
 * Huge numbers of tasks and subtasks may be hosted by a small number of actual threads in a ForkJoinPool,
 * at the price of some usage limitations.
 *
 * <ul>
 *    <li>{@link RecursiveAction} (abstract class) corresponds to {@link Runnable}</li>
 *    <li>{@link RecursiveTask} (abstract class) corresponds to {@link Callable}</li>
 * </ul>
 *
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/forkjoin.html">Fork/Join</a>
 * @see <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/ForkJoinPool.html">ForkJoinPool</a>
 * @see <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/ForkJoinTask.html">ForkJoinTask</a>
 */
public class Main {
    private static final List<Long> LIST1 =
            Stream.iterate(1L, n -> n <= 128, n -> n + 1).collect(Collectors.toList());

    private static void demo1() {
        System.out.println("demo1...");

        ForkJoinPool fjPool = new ForkJoinPool();
        Long fib10 = fjPool.invoke(new RecursiveFib(10));
        System.out.println("fib10: " + fib10);
    }

    private static void demo2() {
        System.out.println("\ndemo2...");
        System.out.println("Expected result: " + LIST1.stream().mapToLong(e -> e).sum());

        ForkJoinPool fjPool = new ForkJoinPool();
        Long result = fjPool.invoke(new ForkJoinReduction(LIST1));
        System.out.println("Actual result: " + result);
    }

    /**
     * As {@link Main#demo2 demo2} but with fewer threads
     */
    private static void demo3() {
        System.out.println("\ndemo3...");
        System.out.println("Expected result: " + LIST1.stream().mapToLong(e -> e).sum());

        ForkJoinPool fjPool = new ForkJoinPool();
        Long result = fjPool.invoke(new ForkJoinReduction1(LIST1));
        System.out.println("Actual result: " + result);
    }

    private static void demo4() {
        System.out.println("\ndemo4...");
        ForkJoinPool fjPool = new ForkJoinPool();

        long t1 = System.currentTimeMillis();
        fjPool.invoke(new ForkJoinSimulationTask(16));
        long t2 = System.currentTimeMillis();

        System.out.println("Parallel dt = " + (t2 - t1));
    }

    public static void main(String[] args) {
        demo1();
        demo2();
        demo3();
        demo4();
    }
}

/**
 * A classic example - a task computing Fibonacci numbers (see {@link RecursiveTask}).
 *
 * <p><i>Note</i>: besides being a dumb way to compute Fibonacci functions (there is a simple fast linear algorithm
 * that you'd use in practice), this is likely to perform poorly because the smallest subtasks are too small
 * to be worth splitting up.
 */
class RecursiveFib extends RecursiveTask<Long> {
    private final long n;

    public RecursiveFib(long n) {
        this.n = n;
    }

    @Override
    protected Long compute() {
        if (n <= 1) {
            return n;
        } else {
            RecursiveFib f1 = new RecursiveFib(n - 1);
            RecursiveFib f2 = new RecursiveFib(n - 2);
            f1.fork();
            f2.fork();
            return f1.join() + f2.join();
        }
    }
}

class ForkJoinReduction extends RecursiveTask<Long> {
    private final List<Long> xs;

    public ForkJoinReduction(List<Long> xs) {
        this.xs = xs;
    }

    @Override
    protected Long compute() {
        if (xs.size() < 4) {
            return xs.stream().mapToLong(e -> e).sum();
        } else {
            int midIdx = xs.size() / 2;

            ForkJoinReduction task1 = new ForkJoinReduction(xs.subList(0, midIdx)); // [0, midIdx)
            ForkJoinReduction task2 = new ForkJoinReduction(xs.subList(midIdx, xs.size())); // [midIdx, xs.size())

            task1.fork(); // task1 to be executed in a separate thread
            task2.fork(); // task2 to be executed in a separate thread

            return task1.join() + task2.join();
        }
    }
}

/**
 * Fewer threads
 */
class ForkJoinReduction1 extends RecursiveTask<Long> {
    private final List<Long> xs;
    private final int zero = 0;

    public ForkJoinReduction1(List<Long> xs) {
        this.xs = xs;
    }

    @Override
    protected Long compute() {
        if (xs.size() < 4) {
            return xs.stream().mapToLong(e -> e).sum();
        } else {
            int midIdx = xs.size() / 2;

            ForkJoinReduction1 task1 = new ForkJoinReduction1(xs.subList(0, midIdx)); // [0, midIdx)
            ForkJoinReduction1 task2 = new ForkJoinReduction1(xs.subList(midIdx, xs.size())); // [midIdx, xs.size())

            task2.fork(); // task2 to be executed in a separate thread
            return task1.compute() + task2.join(); // task1 in the current thread, join has to be after compute!
            //return task2.join() + task1.compute(); // switching the order creates sequential processing (task2 -> task1?)
        }
    }
}

class ForkJoinSimulationTask extends RecursiveAction {
    private final int simSize;

    public ForkJoinSimulationTask(int simSize) {
        this.simSize = simSize;
    }

    @Override
    protected void compute() {
        if (simSize < 8) {
            try {
                System.out.println(Thread.currentThread().getName() +
                        ": simulation in progress for " + simSize + " seconds");
                Thread.sleep(simSize * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            ForkJoinSimulationTask task1 = new ForkJoinSimulationTask(simSize / 2);
            ForkJoinSimulationTask task2 = new ForkJoinSimulationTask(simSize / 2);
            invokeAll(task1, task2);
        }
    }
}
