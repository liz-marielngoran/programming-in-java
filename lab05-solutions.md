# Summary of Lab05

## 2) List<E> and its two implementations: ArrayList<E>, LinkedList<E>

### 2) The faster loop
The second loop (the for-each loop) will finish in milliseconds, while the first loop could take minutes or even hours to finish for a million values.
In fact concerning the first loop, A LinkedList is made of "nodes" where each node only knows about the next one. To find the i-th element, Java has to start at the very beginning and count one by one until it reaches that index.
Although, for the second one, Instead of starting from the beginning every time, the Iterator stays at the current node and simply says, "Give me the next neighbor."

### 3) Printout of the code

The printout will be:
````
[1, 2, 4, 5]
````
In fact, There are two different remove methods available in the List interface:
- remove(int index): Removes the element at the specific position in the list.
- remove(Object o): Removes the first occurrence of the specified element.
  In our code, the literal 2 was passed to the method. Java treats an unadorned integer literal as a primitive int. Therefore, it calls the first version: remove(int index).


### 4) Printout of the code

The printout will be:
````
[2, 3, 4]
[2, 4]
````
These elements were "skipped" because they shifted into index positions that the loop counter had already passed.

### 5) Differences between pieces of code

The output for the first block will be:
````
true
false
false
[1, 2]
````
The Collection interface does not have a remove(int index) method; it only has remove(Object o) that returns a boolean. 
So When you call l1.remove(i), the compiler sees that i is an int. Since Collection only accepts an Object, Java autoboxes that int into an Integer an return a boolean for the loop. At the end, we got ````[1, 2]````

The output for the second block will be:
````
0
2
````
followed by an ````IndexOutOfBoundsException````.
The List interface does have remove(int index). On Iteration 2, it tries to remove index 2. But the list only has one element left, so we got a crash.

## 3) Iterators, the for-each loop (aka. enhanced for loop), and forEach method

### 1)  Rewriting
```
List<Integer> lst = List.of(1, 2, 3, 4, 5);

// 1. Iterator and while-loop
Iterator<Integer> it = lst.iterator();
while (it.hasNext()) {
    System.out.println(it.next());
}

// 2. Iterator and for-loop
for (Iterator<Integer> it = lst.iterator(); it.hasNext(); ) {
    System.out.println(it.next());
}

// 3. Enhanced for-loop
for (Integer num : lst) {
    System.out.println(num);
}

// 4. forEach method
// Using a lambda expression
lst.forEach(num -> System.out.println(num));

````

## 4) Queue<E>, Deque<E> and their implementations: PriorityQueue<E>, and ArrayDeque<E>

### 1) Explanations

Line 1 (The for loop): ````1 3 2 6 4 5````
The documentation specifically states that the iterator does not guarantee any particular order.
A PriorityQueue is typically implemented as a Binary Heap (stored in an array). While the root (index 0) is always the minimum element, the rest of the array follows the "heap property" rather than a linear sort.

Line 2 (The while loop): ```1 2 3 4 5 6```
poll() always removes and returns the head of the queue, which is the smallest element (based on natural ordering).
After the smallest element is removed, the heap performs a "sift-down" operation to move the next smallest element to the root.
Because the heap reorganizes itself after every removal, you get the numbers in perfectly sorted order

## 5) java.lang.Comparable and java.util.Comparator

### 1) Code

````
static <T extends Comparable<T>> T max(T o1, T o2) {
    if (o1.compareTo(o2) >= 0) {
        return o1;
    } else {
        return o2;
    }
}
````

### 2) Code 

````
private static void m() {
    String[] cities = {"Copenhagen", "Warsaw", "Budapest"};
    
    // The Comparator logic: (city2 length - city1 length) for descending order
    Arrays.sort(cities, (s1, s2) -> s2.length() - s1.length());
    
    System.out.println(Arrays.toString(cities));
}
````

## 6) Set<E> and its implementations: HashSet<E>, LinkedHashSet<E>, TreeSet<E>, and EnumSet<E extends Enum<E>>

### 1) Key differences between the four implementations of the Set interface

If the elements ares enums, we have to use enumSet if it is available.
If we want the set sorted by natural ordering or a custom, we have to use TreeSet.
If we want it sorted by the iteration, as we maintain a doubly-linked list, we use LinkedHashSet. It is hashSet with memory of entry.
If none of above, we use hashSet, it is fast for basic operation but elements are randomly stored.

### 2) How to compute the union, intersection, and difference of two sets, using just the methods of the Set interface and without using loops

- Union: we start with a copy of Set A, then add everything from Set B.
    ```
    Set<T> union = new HashSet<>(setA);
    union.addAll(setB);
    ```
- Intersection: we start with a copy of Set A, then "retain" only what matches Set B.
    ````
    Set<T> intersection = new HashSet<>(setA)
    intersection.retainAll(setB)
    ````
- Difference: we start with a copy of Set A, then subtract everything found in Set B 
  ````
  Set<T> difference = new HashSet<>(setA);
  difference.removeAll(setB);
  ````

### 3) Function that takes a TreeSet of strings and returns a new TreeSet with each string being transformed to uppercase

````
import java.util.TreeSet;
import java.util.stream.Collectors;

public TreeSet<String> toUppercaseSet(TreeSet<String> inputSet) {
    return inputSet.stream()
                   .map(String::toUpperCase)
                   .collect(Collectors.toCollection(TreeSet::new));
}
````

### 4) Comparison of the performance of methods add, remove, and contains for the four implementations of Set interface

| Implementation | `add` | `remove` | `contains` |
| :--- | :--- | :--- | :--- |
| **`HashSet`** | $O(1)$ | $O(1)$ | $O(1)$ |
| **`LinkedHashSet`** | $O(1)$ | $O(1)$ | $O(1)$ |
| **`TreeSet`** | $O(\log n)$ | $O(\log n)$ |
| **`EnumSet`** | $O(1)$ | $O(1)$ | $O(1)$ |

---

## 7) Map<K,V> and its implementations: HashMap<K,V>, LinkedHashMap<K,V>, TreeMap<K,V>, and EnumMap<K extends Enum<K>,V>

### 1) Key differences between the four implementations of the Map interface

If you key are enums, we use EnumMap.
If we want the key sorted in a certain order, we use treeMap
If we want to preserve the insertion order, we use LinkedHashMap
If we want just a standard lookup table

### 2) Program that reads all words in a file and prints out how often each word occurred

````
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordCounter {
    public static void main(String[] args) {
        Path path = Paths.get("yourfile.txt");

        try (Stream<String> lines = Files.lines(path)) {
            
            // Map<Word, Count>
            TreeMap<String, Long> wordCounts = lines
                .flatMap(line -> Stream.of(line.split("\\W+"))) // Split by non-word chars
                .filter(word -> !word.isEmpty())               // Remove empty strings
                .map(String::toLowerCase)                      // Normalize to lowercase
                .collect(Collectors.groupingBy(
                    word -> word, 
                    TreeMap::new,                              // Keep it sorted!
                    Collectors.counting()
                ));

            // Print the results
            wordCounts.forEach((word, count) -> 
                System.out.println(word + ": " + count));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
````

### 3) Program that reads all words in a file and prints out on which line(s) each of them occurred. Use a map from strings to sets

````
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WordLineTracker {
public static void main(String[] args) {
Path path = Paths.get("yourfile.txt");

        try {
            List<String> lines = Files.readAllLines(path);

            // Use IntStream to track the index (line number)
            TreeMap<String, Set<Integer>> wordLocations = IntStream.range(0, lines.size())
                .boxed()
                .flatMap(i -> Arrays.stream(lines.get(i).split("\\W+"))
                    .filter(word -> !word.isEmpty())
                    .map(word -> new AbstractMap.SimpleEntry<>(word.toLowerCase(), i + 1)))
                .collect(Collectors.groupingBy(
                    Map.Entry::getKey,
                    TreeMap::new,
                    Collectors.mapping(Map.Entry::getValue, Collectors.toCollection(TreeSet::new))
                ));

            // Print the results
            wordLocations.forEach((word, linesSet) -> 
                System.out.println(word + " appears on lines: " + linesSet));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
````

## 8) Selected algorithms from java.util.Collections and java.util.Arrays

### 2) Why did binarySearch algorithm require sorted data

Binary search requires sorted data because the algorithm relies on the Principle of Elimination.
If the data is sorted, checking the middle element allows you to prove that your target is either in the left half or the right half.


### 3) Difference between linear search and binary search in terms of the time complexity

- Linear Search: It checks every single element one by one from the start until it finds the target. The time is directly proportional to the number of elements($O(n)$). It Works on any data (sorted or unsorted).

- Binary Search: It jumps to the middle and cuts the search area in half with every step. The time grows extremely slowly $O(\log n)$. It requires sorted data only.

