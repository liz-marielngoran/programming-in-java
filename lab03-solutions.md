# Summary of Lab03

## 2) throws, throw, try-catch, try-finally, and try-catch-finally

### 2) Differences between checked and unchecked exceptions
Checked exceptions are exceptions that are detected by the compilator and that the application should anticipate and be able to handle with try or throws in order to run correctly.
Although, Unchecked expressions are detected while the code is running, not by the compilator. Most of the time it is an error of logic, we are not obliged to handle but that can be.

### 3) Meaning of throw and throws

- throw: triggers an error in the code
````
public void checkedAge(int age) {
    if (age < 18) {
        throw new ArithmeticException("Accès refusé : mineur");
    }
}
````
- throws: declares the possible exceptions that the method can return, a kind of prevention. It is used in methods signature.
```
public void readFile() throws IOException, SQLException {
    // Code 
}
```

## 5) Simple File I/O: text files

### 1)  A function that counts the number of characters in a given text file
```
public static long countCharacters(String fileName) throws IOException {
    String content = Files.readString(Path.of(fileName), StandardCharsets.UTF_8);
    return content.length();
}
````
### 2) A function that counts the number of lines in a given text file
````
public static long countLines(String fileName) throws IOException {
    var lines = Files.lines(Path.of(fileName), StandardCharsets.UTF_8) {
        return lines.count();
    }
}
````

### 3) A function that concatenates two given files; consider two approaches:

#### 3.a) The second file is appended to the first one
````
public static void appendFiles(String file1, String file2) throws IOException {
    byte[] content2 = Files.readAllBytes(Path.of(file2));
    Files.write(Path.of(file1), content2, java.nio.file.StandardOpenOption.APPEND);
}
````
#### 3.b) The result is a new file
````
public static void mergeToNewFile(String file1, String file2, String resultFile) throws IOException {
    List<String> lines1 = Files.readAllLines(Path.of(file1));
    List<String> lines2 = Files.readAllLines(Path.of(file2));
    lines1.addAll(lines2);
    Files.write(Path.of(resultFile), lines1);
}
````

### 4) A function that counts the number of words in a given text file
````
public static long countWords(String fileName) throws IOException {
    long count = 0;
    try (var scanner = new Scanner(Path.of(fileName), StandardCharsets.UTF_8)) {
        while (scanner.hasNext()) {
            scanner.next();
            count++;
        }
    }
    return count;
}
````
### 5) A function that counts the number of whitespace characters in a given text file

```
public static long countWhitespaces(String fileName) throws IOException {
    String content = Files.readString(Path.of(fileName));
    return content.length() - content.replaceAll("\\s", "").length();
}
````
### 6) A function that changes, in a given text file, all TAB characters to SPACE characters
````
public static void replaceTabsWithSpaces(String fileName) throws IOException {
    Path path = Path.of(fileName);
    String content = Files.readString(path);
    String updatedContent = content.replace("\t", " ");
    Files.writeString(path, updatedContent);
}
````

## 6) Simple File I/O: binary files

### 1) Explanations

- BufferedReader: a class containing methods that read big text blocs and store it in memory. One of its methods readLine() allows to read files line by line.
- BufferedWriter: a class containing methods that store data in memory and write on ware when the buffer in full, which allows to boost performances.

- FileReader: a class containing methods to read characters in a file.
- FileWriter: a class containing methods to write characters in a file.
- PrintWriter: a class containing methods like print() and println(), allows to write

- FileInputStream: a class containing methods to read bytes directly in a file.
- FileOutputStream: a class containing methods to write bytes directly in a file.

- DataInputStream: a class containing methods to read primitive types directly in a file.
- DataOutputStream: a class containing methods to write primitive types directly in a file.

- ObjectInputStream: a class used for deserialization containing methods like ``.readObject()`` to read the bytes of a file and rebuild the same object that was, in memory.
- ObjectOutputStream: a class used for serialization containing methods like ``.writeObject(monObjet)`` to transform a java object entirely (fields,lists,texts) into a suite of bytes in a file.

- Files: a class containing methods like ```.readAllLine(path)``` that, alone, allows to read, copy or an entire file.
- Path: an interface that represents the address of a file
- File: a class containing methods to verify if a file exists, to delete it or create folders.

### 2) Extension of the code
````
/**
     * Tracks how many times this program has been executed by persisting a counter to a file.
     */
    private static void trackExecutions() {
        int count = 0;
        Path path = Path.of(counterFileName);

        // 1. Read the existing count if the file exists
        if (Files.exists(path)) {
            try (DataInputStream in = new DataInputStream(Files.newInputStream(path))) {
                count = in.readInt();
            } catch (IOException e) {
                System.err.println("Could not read execution counter: " + e.getMessage());
            }
        }

        // 2. Increment
        count++;

        // 3. Save the updated count
        try (DataOutputStream out = new DataOutputStream(Files.newOutputStream(path))) {
            out.writeInt(count);
            System.out.println("--- This program has been executed " + count + " time(s) ---");
        } catch (IOException e) {
            System.err.println("Could not save execution counter: " + e.getMessage());
        }
    }
````

## 8) Mini project 03_01 (exc03_01)

### 1) Completion of the linked list based implementation and 3)JavaDoc comments to LinkedListBasedImpl

````
/**
 * A doubly linked list-based implementation of the {@link QueueOfInts} interface.
 * This implementation provides O(1) time complexity for both enqueue and dequeue operations.
 * It is serializable, allowing the state of the queue to be persisted.
 */
public class LinkedListBasedImpl implements QueueOfInts, Serializable {

    /**
     * Adds an element to the front of the linked list.
     * Updates the head pointer and establishes backward links for the doubly linked structure.
     * * @param x the integer to be added to the queue
     */
    @Override
    public void enqueue(int x){
        Node newNode = new Node(x, first, null);
        if (first != null) {
            first.prev = newNode;
        }
        first = newNode;

        // If this is the first element added, it is also the last
        if (last == null) {
            last = first;
        }
        numOfElems++;
    }

    /**
     * Removes the element from the back of the linked list.
     * * @return the integer value of the removed node
     * @throws NoSuchElementException if the queue is empty
     */
    @Override
    public int dequeue(){
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty: cannot dequeue");
        }

        int value = last.elem;
        last = last.prev;

        if (last != null) {
            last.next = null;
        } else {
            // Queue is now empty
            first = null;
        }

        numOfElems--;
        return value;
    }

    /**
     * Returns the current size of the queue.
     * * @return the number of elements currently in the list
     */
    @Override
    public int numOfElems() {
        return numOfElems;
    }

    /**
     * Retrieves the value at the back of the queue without removing it.
     * * @return the integer value at the back of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    @Override
    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty: cannot peek");
        }
        return last.elem;
    }

    /**
     * Internal building block for the doubly linked list.
     * Stores the integer value and references to both neighbor nodes.
     */
    private static class Node implements Serializable {
        int elem;
        Node next;
        Node prev;

        /**
         * Constructs a standalone node.
         * @param elem the value to store
         */
        public Node(int elem) {
            this.elem = elem;
        }

        /**
         * Constructs a node with links to its neighbors.
         * @param elem the value to store
         * @param next reference to the next node in the list
         * @param prev reference to the previous node in the list
         */
        public Node(int elem, Node next, Node prev) {
            this.elem = elem;
            this.next = next;
            this.prev = prev;
        }
    }

    /**
     * Reference to the first node in the list (the "front").
     * New elements are added here.
     */
    private Node first = null;

    /**
     * Reference to the last node in the list (the "back").
     * Elements are removed from here to maintain FIFO order.
     */
    private Node last = null;

    /**
     * The current number of elements stored in the queue.
     */
    private int numOfElems = 0;
}

````

### 2)JavaDoc comments to the interface and all its methods

````
/**
 * A First-In-First-Out (FIFO) queue of integers.
 * Elements are added to the "front" and removed from the "back"
 */
public interface QueueOfInts {

    /**
     * Adds an integer to the front of the queue.
     * * @param x the integer element to be added
     */
    void enqueue(int x);

    /**
     * Removes and returns the integer at the back of the queue.
     * * @return the integer removed from the back of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    int dequeue();

    /**
     * Checks if the queue contains no elements.
     * * @return {@code true} if the queue is empty, {@code false} otherwise
     */
    default boolean isEmpty() {
        return numOfElems() == 0;
    }

    /**
     * Returns the current number of elements in the queue.
     * * @return the number of elements
     */
    int numOfElems();

    /**
     * Retrieves, but does not remove, the integer at the back of the queue.
     * * @return the integer at the back of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    int peek();
}

````



