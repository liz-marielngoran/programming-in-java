# Summary of Lab08

## 1) Creating and executing tasks

### 1) Process, thread, and task

- A Process is an instance of a computer program that is being executed.
- A Thread is a unit of execution within a process.
- A Task is a logical unit of work that you want to execute, usually asynchronously.

### 2) Comparison of the two ways we can create execution threads

- Extending the Thread: creates a class that inherits from Thread and overrides run(). 
    As Java only supports single inheritance, if we extend Thread, we can't extend any other class.
    Thread and task are coupled.
```
class MyThread extends Thread {
    public void run() {
        System.out.println("Running in thread: " + getName());
    }
}
new MyThread().start();
```
- Implementing Runnable: defines a task in a class implementing Runnable and passes it to a Thread object.
    It leaves inheritance free. 
    Concerning reusability, it decouples the task from the execution mechanism, allows to extend other classes.
```
class MyTask implements Runnable {
    public void run() {
        System.out.println("Running in thread: " + Thread.currentThread().getName());
    }
}
new Thread(new MyTask()).start();
```
### 3) Difference between Runnable and Callable

Both represent tasks, but they differ in how they handle data:

- Runnable: The run() method returns void and cannot throw checked exceptions. It’s a "fire and forget" task.

- Callable: The call() method returns a value (e.g., Callable<String>) and can throw checked exceptions.It is used with Future objects to retrieve results later.```

```
Callable<Integer> task = () -> {
    return 42; // can return a result
};
Future<Integer> future = executor.submit(task);
int result = future.get(); // blocks until done
```
### 4) Purpose of the basic components (interfaces and classes) of the Executor Framework

The Executor framework manages a pool of threads, so we do not have to manually create new Thread() every time.
It decouples task submission from task execution.

- Executor: the basic interface with a single method: void execute(Runnable command). Submits a task for execution with no return value.

- ExecutorService: an interface extending Executor that adds lifecycle management and support for Callable:
    - submit(Callable/Runnable) → returns Future
    - shutdown() / shutdownNow()
    - invokeAll() / invokeAny()

- ScheduledExecutorService: an interface extending ExecutorService to support delayed and periodic task execution (schedule(), scheduleAtFixedRate()).

- Executors: A factory class with static methods to create pre-configured thread pools:
````
javaExecutors.newFixedThreadPool(4);      // fixed number of threads
Executors.newCachedThreadPool();      // grows/shrinks dynamically
Executors.newSingleThreadExecutor();  // single background thread
Executors.newScheduledThreadPool(2);  // for scheduled tasks
````

### 5)Drawing of the state diagram (life cycle) of a thread in Java the concepts of concurrency an parallelism

1. New: Thread is created but start() hasn't been called.

2. Runnable: Ready to run and waiting for the CPU scheduler.

3. Blocked/Waiting: Waiting for a lock or another thread's signal.

4. Timed Waiting: Waiting for a specific period (e.g., Thread.sleep()).

5. Terminated: The task is finished.

### 6) Comparison of the concepts of concurrency and parallelism

- Concurrency is about structure — designing a program to handle multiple things at once, even if only one runs at a given instant (e.g., a web server handling many requests via threads, even on one core).
  It is like You're switching between tasks. Only one thing happens at any moment, but all are "in progress" simultaneously.

- Parallelism is about execution — actually computing multiple things simultaneously on multiple CPU cores (e.g., splitting a large array calculation across 8 cores).
    It is just doing many things at literally the same time"

## 2) Race conditions and synchronisation

### 1) Concepts of thread-safety, race-condition, deadlock, synchronisation, atomic operation, atomic variable
   
- Thread-safety means a class or method behaves correctly when accessed by multiple threads simultaneously, without requiring extra coordination from the caller.
  Example: Vector is thread-safe, ArrayList is not.

- Race condition occurs when the correctness of a program depends on the relative timing/ordering of threads. 
  Two threads reading and writing shared data simultaneously can produce wrong results because the operations interleave unpredictably.

- Deadlock is when two or more threads are each waiting for a lock held by the other — they block forever. 
  Classic pattern: Thread A holds lock 1, waits for lock 2. Thread B holds lock 2, waits for lock 1. Neither can proceed.

- Synchronisation is the mechanism that coordinates access to shared resources so only one thread executes a critical section at a time. 
  In Java this is done with synchronized, Lock, semaphores, etc.

- Atomic operation is an operation that completes in a single, indivisible step from the perspective of other threads — no thread can observe it in a half-finished state.

- Atomic variable wraps a value and guarantees that operations like increment are performed atomically using CPU-level instructions (CAS — Compare-And-Swap), with no locking overhead:

````
AtomicInteger count = new AtomicInteger(0);
count.incrementAndGet(); 
````

### 2) Advantages of using immutable objects in multithreaded applications

An immutable object's state cannot change after construction. This makes it inherently thread-safe because:

- No shared mutable state — since the value never changes, there's nothing to synchronise. Multiple threads can read it freely at the same time.
- No race conditions — you can't have a write/write or read/write conflict on something that's never written after creation.
- No deadlocks — no locks are ever needed, so no lock ordering problems arise.
- Safe publication — once an immutable object is visible to a thread, it's guaranteed to be fully constructed and correct.

### 3) Comparison of the synchronisation mechanisms available in Java

- synchronized — places an intrinsic lock on a method or block so only one thread can execute it at a time.

- ReentrantLock — an explicit lock that supports try-lock and timeouts, offering more flexibility than synchronized.

- ReadWriteLock — allows multiple threads to read simultaneously while keeping write access exclusive.

- Semaphore — controls access to a resource by maintaining a count of permits that threads must acquire before proceeding.

- CountDownLatch — allows threads to wait until a set of operations in other threads has completed.

- CyclicBarrier — forces a group of threads to wait until all have reached a common point, and unlike CountDownLatch it can be reused.

- volatile — guarantees a variable is always read from main memory rather than a thread-local cache, ensuring visibility without locking.

## 3) The Fork/Join Framework

### 1)  Purpose of the basic components (interfaces and classes) of the Fork/Join Framework

The Fork/Join Framework is designed for divide-and-conquer parallelism — recursively splitting a large task into smaller subtasks, solving them in parallel, and combining the results.

- ForkJoinPool is the thread pool that manages worker threads. There is a common pool available via ForkJoinPool.commonPool(). Unlike a regular ThreadPoolExecutor, it uses work-stealing.

- ForkJoinTask<V> is the abstract base class for tasks submitted to a ForkJoinPool. You typically don't extend it directly.

    - RecursiveTask<V> extends ForkJoinTask: used when the task returns a result (like a sum or a product).
  
    - RecursiveAction extends ForkJoinTask: used when the task returns nothing (like sorting in place).

### 2) purpose of the methods fork and join

- fork(): Arranges for the asynchronous execution of this task in the pool. Essentially, it says "start this sub-task in the background."

- join(): Returns the result of the task once it is finished. It’s a blocking call that waits for the sub-task to complete.

### 3) The concept of work-stealing (used in the Fork/Join Framework)

In a typical thread pool, if one thread finishes its queue, it sits idle while others might be overwhelmed.
Work-stealing allows a thread that has run out of things to do to "steal" tasks from the back of the queues of other threads that are still busy. 
This ensures all CPU cores are kept active, maximizing throughput.

### 4) Using the Fork/Join Framework implement a method that multiplies two matrices

A logging class is often implemented as a Singleton.
You want one single logging instance managing all log messages in the application to:

- Avoid multiple log files
- Prevent configuration conflicts
- Ensure consistent logging behavior

```
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class MatrixMultiply {

    static final int THRESHOLD = 64; // rows below this computed directly

    static class MultiplyTask extends RecursiveAction {
        private final int[][] A, B, C;
        private final int rowStart, rowEnd;

        MultiplyTask(int[][] A, int[][] B, int[][] C, int rowStart, int rowEnd) {
            this.A = A; this.B = B; this.C = C;
            this.rowStart = rowStart; this.rowEnd = rowEnd;
        }

        @Override
        protected void compute() {
            if (rowEnd - rowStart <= THRESHOLD) {
                // Base case: compute assigned rows directly
                int cols = B[0].length;
                int inner = B.length;
                for (int i = rowStart; i < rowEnd; i++) {
                    for (int j = 0; j < cols; j++) {
                        long sum = 0;
                        for (int k = 0; k < inner; k++) {
                            sum += (long) A[i][k] * B[k][j];
                        }
                        C[i][j] = (int) sum;
                    }
                }
            } else {
                // Recursive case: split rows in half
                int mid = (rowStart + rowEnd) / 2;
                MultiplyTask top    = new MultiplyTask(A, B, C, rowStart, mid);
                MultiplyTask bottom = new MultiplyTask(A, B, C, mid, rowEnd);
                top.fork();       // run top half in parallel
                bottom.compute(); // run bottom half on this thread
                top.join();       // wait for top half to finish
            }
        }
    }

    public static int[][] multiply(int[][] A, int[][] B) {
        int rows = A.length;
        int[][] C = new int[rows][B[0].length];
        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.invoke(new MultiplyTask(A, B, C, 0, rows));
        return C;
    }
}
```


## 4) Parallel streams

### 1) Apply parallel streams in proj2. Compare performance of these two solutions (i.e., stream vs. parallelStream based)

we will just replace stream by parallelStream in the code then run

````   
private static Stream<Movie> moviesParallel() {
        return ImdbTop250.movies().orElse(List.of()).parallelStream();
    }
    static Set<String> ex01(String director) {
        return moviesParallel()
                .filter(m -> m.directors().contains(director))
                .map(Movie::title)
                .collect(Collectors.toSet());
    }
 
    static Set<String> ex02(String actor) {
        return moviesParallel()
                .filter(m -> m.actors().contains(actor))
                .map(Movie::title)
                .collect(Collectors.toSet());
    }
 
    static Map<String, Long> ex03() {
        return moviesParallel()
                .flatMap(m -> m.directors().stream())
                .collect(Collectors.groupingByConcurrent(
                        director -> director,
                        Collectors.counting()
                ));
    }
 
    static Map<String, Long> ex04() {
        // Sort + limit must be sequential — parallelism helps the upstream grouping
        return ex03().entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
 
    static Map<String, Set<String>> ex05() {
        Set<String> top10Directors = ex04().keySet();
        return moviesParallel()
                .flatMap(m -> m.directors().stream()
                        .filter(top10Directors::contains)
                        .map(d -> Map.entry(d, m.title()))
                )
                .collect(Collectors.groupingByConcurrent(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toSet())
                ));
    }
 
    static Map<String, Long> ex06() {
        return moviesParallel()
                .flatMap(m -> m.actors().stream())
                .collect(Collectors.groupingByConcurrent(
                        actor -> actor,
                        Collectors.counting()
                ));
    }
 
    static Map<String, Long> ex07() {
        return ex06().entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder()))
                .limit(9)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
 
    static Map<String, Set<String>> ex08() {
        Set<String> top9Actors = ex07().keySet();
        return moviesParallel()
                .flatMap(m -> m.actors().stream()
                        .filter(top9Actors::contains)
                        .map(actor -> Map.entry(actor, m.title()))
                )
                .collect(Collectors.groupingByConcurrent(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toSet())
                ));
    }
 
    static Map<String, Long> ex09() {
        return moviesParallel()
                .flatMap(m -> {
                    List<String> actors = List.copyOf(m.actors());
                    List<String> pairs = new ArrayList<>();
                    for (int i = 0; i < actors.size(); i++) {
                        for (int j = i + 1; j < actors.size(); j++) {
                            String a = actors.get(i), b = actors.get(j);
                            pairs.add(a.compareTo(b) <= 0 ? a + ", " + b : b + ", " + a);
                        }
                    }
                    return pairs.stream();
                })
                .collect(Collectors.groupingByConcurrent(pair -> pair, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
 
    static Map<String, Set<String>> ex10() {
        Set<String> top5Pairs = ex09().keySet();
        return moviesParallel()
                .flatMap(m -> {
                    List<String> actors = List.copyOf(m.actors());
                    List<Map.Entry<String, String>> pairs = new ArrayList<>();
                    for (int i = 0; i < actors.size(); i++) {
                        for (int j = i + 1; j < actors.size(); j++) {
                            String a = actors.get(i), b = actors.get(j);
                            String pair = a.compareTo(b) <= 0 ? a + ", " + b : b + ", " + a;
                            if (top5Pairs.contains(pair)) pairs.add(Map.entry(pair, m.title()));
                        }
                    }
                    return pairs.stream();
                })
                .collect(Collectors.groupingByConcurrent(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toSet())
                ));
    }
````

#### Comparison and interpretation

1. Parallel was slower for simple tasks (ex01, ex02).
   The reason is that Java still needs to split the work and manage multiple threads even for small tasks, and that setup ends up taking longer than just running it normally.

2. ex09 and ex10 benefited the most. These methods generate all possible actor pairs, so there is enough computation happening that splitting the work across threads actually makes a noticeable difference.

3. The dataset is only 250 movies. This is likely the main reason parallel did not help much overall. 250 items is simply not enough to see a real improvement. With thousands or millions of records the results would probably look very different.
 
4. Sorting cannot be paralleled. For the top-10 and top-9 methods, the sorting step always runs sequentially regardless, so parallel can only speed up the part before the sort, which limits the overall gain.

5. In conclusion, Parallel does not automatically mean faster. The thread management overhead can actually make things worse on small datasets. Parallel streams are only worth using when the dataset is large and the computation per element is heavy enough to justify the cost.

### 2) Using parallel streams, find all files in a directory that contain a given word

```
    import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

public class FileSearch {

    public static List<Path> findFilesContaining(String directoryPath, String word) throws IOException {
        try (var files = Files.walk(Paths.get(directoryPath))) {
            return files
                .filter(Files::isRegularFile)           // keep only files, not directories
                .parallel()                              // switch to parallel stream
                .filter(file -> containsWord(file, word))// keep only files containing the word
                .collect(Collectors.toList());
        }
    }

    private static boolean containsWord(Path file, String word) {
        try {
            return Files.readString(file).contains(word);
        } catch (IOException e) {
            return false;  // skip files that can't be read
        }
    }

    public static void main(String[] args) throws IOException {
        List<Path> results = findFilesContaining("C:/myFolder", "hello");
        results.forEach(System.out::println);
    }
}
```




