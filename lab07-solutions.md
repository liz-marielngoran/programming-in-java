# Summary of Lab07

## 1)Streams (finite and infinite) creation

### 1)The concept of a stream

It is a sequence of objects that supports various methods that can be pipelined to produce the desired result.
A stream does not store data; it moves data from a source through a pipeline of computational steps.
Operations on a stream produce a result but do not modify the original source. For example, filtering a List produces a new stream of filtered elements rather than removing them from the list.
Unlike collections, streams can represent infinite sequences (e.g., a stream of random numbers) that are processed as needed.
They are lazy, meaning they don't do any work until a terminal operation is invoked.


### 3) A finite stream of 5 boolean values

```
Stream<Boolean> fiveBooleans = Stream.of(true, false, true, true, false);
```

### 4) An infinite stream of random integer values

```
Stream<Integer> infiniteRandoms = Stream.generate(() -> new Random().nextInt());
```

### 5) The infinite stream of even positive integer values

```
Stream<Integer> evenPositives = Stream.iterate(2, n -> n + 2);
```
### 6) The stream of the first 20 prime numbers

````
public static boolean isPrime(int number) {
    return number > 1 && IntStream.rangeClosed(2, (int) Math.sqrt(number))
                                  .noneMatch(n -> number % n == 0);
}

Stream<Integer> first20Primes = Stream.iterate(2, n -> n + 1)
                                      .filter(MyClass::isPrime)
                                      .limit(20);
````

### 7) The infinite stream of Fibonacci numbers

```
Stream<Long> fibonacci = Stream.iterate(new long[]{0, 1}, f -> new long[]{f[1], f[0] + f[1]})
                               .map(f -> f[0]);
```

## 2) Stream methods (I): skip, peek, takeWhile, dropWhile, distinct, sorted, max, min, count, findFirst, findAny, anyMatch, allMatch, and noneMatch

### 2) Partition of the methods into intermediate and terminal

Intermediate methods are methods which return a Stream. 
In the list we do have distinct, limit, skip, peek, takeWhile, dropWhile, sorted.

Terminal methods are methods which consume the Stream. They trigger execution and produce a result.
In the list we do have count, max, min, findFirst, findAny, allMatch, anyMatch, noneMatch, forEach.


### 3) Potential applications of method peek

Peek is an intermediate operation that lets you inspect elements as they flow through the pipeline, without modifying them. It takes a Consumer and returns the same Stream.

As use cases we have:
- Debugging: prints elements between operations to see what's happening at each stage
- Logging: logs values to a file/logger without interrupting the pipeline

### 4)  Difference between methods skip and dropWhile

The dropWhile method is conditional. It only cares about the condition. It inspects each element starting from the beginning. 
It keeps discarding elements as long as a specific condition (the predicate) is true. The moment it hits an element that returns false, it stops "dropping" and keeps everything else from that point forward.

````
List<Integer> mixed = List.of(1, 3, 2, 5, 4);
mixed.stream().dropWhile(x -> x < 4).toList();
// 1 < 4, we drop 1 then 3 < 4, we drop 3 then 2 < 4, we drop 2 then we actually get [5, 4] 
````

The skip method is positional. It only cares about the index. 
It will discard the first n elements and keep everything that follows.
 
````
List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);

// skip: always drops first 3
numbers.stream().skip(3).toList();
// [4, 5, 6]
````

### 5)The rule of chaining distinct and sorted

- Distinct removes duplicates
- Sorted sorts all elements of the stream including duplicates

- In the "distinct → sorted" scenario, the stream first removes all duplicate elements. 
  Only the unique elements are then passed to the sorting algorithm. It is more efficient because we sort less data

- In the "sorted → distinct" scenario, the stream sorts the entire dataset —including all duplicates— and then filters out the redundant items afterward.
  It is less efficient because we do more sorting work upfront.

However, they give the same result. Order matters significantly for performance.


## 3) throw-catch and functional programming mismatch

### 1) The problem of the "throw-catch and functional programming mismatch"
When an objet is initialized, its default values are zero if it is an integer and null if it is a string. Static variables and statics constants are initialized at the same time, the class is. Concerning the anonymous blocks and the anonymous static block, they are also created when the class is and, they are executed before the constructor. This one (constructor) is executed when we use new in the main. Although, static anonymous blocs are executed only once, the first time we initialize the code (main).

### 2) Comparison

The core issue is that a lambda expression must match the signature of the functional interface it implements.

- Unchecked Exceptions (RuntimeExceptions): These are straightforward. You can let them be thrown inside the lambda; the stream will stop immediately, and the exception will propagate up to the caller of the terminal operation. No special syntax is required.

- Checked Exceptions: Since interfaces like map expect a function that doesn't throw checked exceptions, you have two main options:
    - Litter the Lambda with Try-Catch: Wrap the risky code inside the lambda in a try-catch block. This is often criticized for making the stream pipeline look "messy."
    - Wrapper Methods: Create a helper method that catches the checked exception and rethrows it as an UncheckedException. This keeps the stream pipeline readable.

### 3) Advantages of using Optional when compared to throwing exceptions or using the null

Problems with null:

- Any method receiving the result must remember to null-check, and forgetting causes a NullPointerException at runtime with no warning at compile time.
- The method signature gives no hint that the value might be absent.

Problems with throwing exceptions:

- Exceptions are meant for exceptional situations, not for a normal "value not found" outcome.
- They are expensive to create (the JVM captures the full stack trace) and make control flow harder to follow.

The concrete advantages of optional are:

- Explicit API (vs. null): When a method returns Optional<T>, it forces the caller to acknowledge that the result might be missing. With null, the developer might forget to check, leading to the dreaded NullPointerException.
- Performance (vs. Exceptions): Throwing an exception is "expensive" for the JVM because it has to capture the entire stack trace. If a "not found" scenario is a common part of your business logic (like a user looking for a file that isn't there), Optional is much faster.
- Chainable operations — Optional works naturally with streams via map(), filter(), orElse(), orElseThrow(), making code more fluent and readable
- Forces handling — the caller is pushed to consciously deal with the empty case rather than forgetting a null-check.

## 4) Stream methods (II): filter, map, flatMap, and reduce

### 1) Imperative (loop based) implementations of filter, map, flatMap, and reduce

#### Filter

````
//Returns elements that match a predicate
public static <T> List<T> filter(List<T> list, java.util.function.Predicate<T> pred) {
List<T> result = new ArrayList<>();
for (T item : list) {
if (pred.test(item)) result.add(item);
}
return result;
}
````
#### Map

````
//Transforms each element into something else
public static <T, R> List<R> map(List<T> list, java.util.function.Function<T, R> fn) {
        List<R> result = new ArrayList<>();
        for (T item : list) result.add(fn.apply(item));
        return result;
    }
````

####  FlatMap

````
//Flattens a list of lists into a single list
public static <T, R> List<R> flatMap(List<T> list, java.util.function.Function<T, List<R>> fn) {
        List<R> result = new ArrayList<>();
        for (T item : list)
            for (R sub : fn.apply(item)) result.add(sub);
        return result;
    }
````

#### Reduce

````
//Combines elements into a single value
public static <T> T reduce(List<T> list, T identity, java.util.function.BinaryOperator<T> op) {
        T acc = identity;
        for (T item : list) acc = op.apply(acc, item);
        return acc;
    }
````

### 2) Stream of 100 random integers print out only even values

````
public class Ex2 {
    public static void main(String[] args) {
        new Random().ints(100)
            .filter(n -> n % 2 == 0)
            .forEach(System.out::println);
    }
}
````

### 3) Print out the first letter (capitalized) of each element

````
public class Ex3 {
    public static void main(String[] args) {
        List.of("alpha", "bravo", "charlie", "delta")
            .stream()
            .map(s -> String.valueOf(s.charAt(0)).toUpperCase())
            .forEach(System.out::println);
    }
}
````

### 4) Using flatMap, flatten the list

```
public class Ex4 {
    public static void main(String[] args) {
        List<Integer> flat = List.of(
                List.of(1, 2),
                List.of(3, 4, 5),
                List.of(6, 7, 8, 9)
            )
            .stream()
            .flatMap(Collection::stream)
            .toList();

        System.out.println(flat); // [1, 2, 3, 4, 5, 6, 7, 8, 9]
    }
}
```


### 5) Sum of 100 random positive integers using reduce

````
 int sum = Stream.generate(() -> new Random().nextInt(100) + 1)
                .limit(100)
                .reduce(0, Integer::sum);
        System.out.println("Sum: " + sum);
````
### 6) Product of 7 random positive integers

```` 
 int product = Stream.generate(() -> new Random().nextInt(100) + 1)
                .limit(7)
                .reduce(1, (a, b) -> a * b);
        System.out.println("Product: " + product);
````
### 7) Max of 100 random positive integers

````
int max = Stream.generate(() -> new Random().nextInt(100) + 1)
                .limit(100)
                .reduce(0, Integer::max);
        System.out.println("Max: " + max);
````
### 8) Concatenation of 10 random positive integers

````
String concatenated = Stream.generate(() -> new Random().nextInt(100) + 1)
                .limit(10)
                .reduce("", (a, b) -> a + b, String::concat);
        System.out.println("Concatenated: " + concatenated);
````

## 5) Primitive type streams: IntStream, LongStream, and DoubleStream


### 1) Compare IntStream with Stream<Integer>

The fundamental difference between IntStream and Stream<Integer> lies in how they handle data types and memory. 

- While IntStream is a specialized stream designed specifically for primitive int values, Stream<Integer> is a generic object stream that holds Integer wrapper objects.

- Because IntStream works directly with primitives, it is significantly more performant and memory-efficient; it avoids the overhead of "Auto-boxing," which is the process Java uses to wrap a primitive int into an Integer object. Conversely, Stream<Integer> incurs this performance penalty every time a value is processed because it must constantly convert between the primitive and the object.

- Furthermore, their built-in capabilities differ. IntStream provides specialized numerical methods such as sum(), average(), and summaryStatistics() directly on the stream. In contrast, if you are using a Stream<Integer>, you must first transform it into an IntStream using the mapToInt() method before you can access these specific mathematical functions.

### 2) Explanation of the output of the following code

First Block: 
- Stream.of(a) creates a stream of arrays: {1, 2}, {3, 4}, and {5, 6}.
- Step 2: .mapToInt(e -> IntStream.of(e).sum()) takes each sub-array, creates an IntStream for it, and calculates its sum.{1, 2} becomes 3{3, 4} becomes 7{5, 6} becomes 11
- Step 3: The outer .sum() adds these results together: $3 + 7 + 11 = 21$.
- Output: 21

Second Block:
- Step 1: DoubleStream.of(numbers) creates a stream of 1.2, 1.0, 2.2, 3.6.
- Step 2: .mapToInt(e -> (int)e) casts each double to an integer, truncating the decimals.1.2 $\rightarrow$ 11.0 $\rightarrow$ 12.2 $\rightarrow$ 23.6 $\rightarrow$ 3
- Step 3: .sum() adds them: $1 + 1 + 2 + 3 = 7$.
- Output: 7

### 3) Explanation of the output of the following code

This code uses the numeric ASCII/Unicode values of characters.
The expression e - 'A' calculates the distance of the character from 'A' (where 'A' = 0).
'D' - 'A' = $68 - 65 = 3$
'B' - 'A' = $66 - 65 = 1$
'A' - 'A' = $65 - 65 = 0$
'C' - 'A' = $67 - 65 = 2$
The .sum() is $3 + 1 + 0 + 2 = 6$.
The output is 6

### 4) Stream of 1000 random integers and then calculate their min, max, sum and average

````
IntSummaryStatistics stats = new Random().ints(1000)
    .summaryStatistics();

System.out.println("Min: " + stats.getMin());
System.out.println("Max: " + stats.getMax());
System.out.println("Sum: " + stats.getSum());
System.out.println("Average: " + stats.getAverage());
````

### 5) Average string length

```
Stream<String> stringStream = Stream.of("Apple", "Banana", "Cherry");

double average = stringStream
    .mapToInt(String::length)
    .average()
    .orElse(0.0); // Handles the case if the stream is empty

System.out.println("Average length: " + average);
```

## 6) Stream pipelines and collectors

````
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

record Client(String name, String address, Optional<String> email) {}
record Ticket(String departure, String destination, LocalDate date, Client client, double price) {}

public class TicketExercises {

    public static void main(String[] args) {
````

### 1) Generate a list of clients (i.e., List<Client> clients) and and a list of tickets (i.e., List<Ticket> tickets)

````
        List<Client> clients = List.of(
            new Client("Alice", "Paris",    Optional.of("alice@mail.com")),
            new Client("Bob",   "Lyon",     Optional.empty()),
            new Client("Carol", "Marseille", Optional.of("carol@mail.com"))
        );

        List<Ticket> tickets = List.of(
            new Ticket("Paris",     "London",  LocalDate.of(2024, 6, 10), clients.get(0), 120.0),
            new Ticket("Lyon",      "Berlin",  LocalDate.of(2024, 6, 15), clients.get(1), 200.0),
            new Ticket("Marseille", "London",  LocalDate.of(2024, 6, 10), clients.get(2), 150.0),
            new Ticket("Paris",     "Madrid",  LocalDate.of(2024, 7, 1),  clients.get(0), 95.0),
            new Ticket("Lyon",      "London",  LocalDate.of(2024, 7, 1),  clients.get(1), 180.0)
        );
````

### 2) Number of tickets with the given destination

````
        String destination = "London";
        long count = tickets.stream()
                .filter(t -> t.destination().equals(destination))
                .count();
        System.out.println("Tickets to " + destination + ": " + count);

````

### 3) Tickets for the given date

````
LocalDate date = LocalDate.of(2024, 6, 10);
        System.out.println("Tickets on " + date + ":");
        tickets.stream()
                .filter(t -> t.date().equals(date))
                .forEach(System.out::println);
````

### 4) Checking if there is at least one ticket reserved fot the given client

````
String clientName = "Bob";
        boolean hasTicket = tickets.stream()
                .anyMatch(t -> t.client().name().equals(clientName));
        System.out.println(clientName + " has a ticket: " + hasTicket);
````

### 5) Average value of the prices for all the tickets in the list


```
double average = tickets.stream()
                .mapToDouble(Ticket::price)
                .average()
                .orElse(0.0);
        System.out.println("Average price: " + average);
```
### 6) Checking if all the clients have an email address

````
 boolean allHaveEmail = tickets.stream()
                .allMatch(t -> t.client().email().isPresent());
        System.out.println("All clients have email: " + allHaveEmail);
````

### 7) Comma separated value containing all the destination in the list

````
String destinations = tickets.stream()
                .map(Ticket::destination)
                .distinct()
                .collect(Collectors.joining(", "));
        System.out.println("Destinations: " + destinations);
    }
}
````

## 7) Streams in text processing (I)

````
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ImdbExercises {

    static List<Movie> movies = ImdbTop250.movies().orElseThrow();

    private static void ex1() {
````

### 1) Total number of actors

````
long totalActors = movies.orElseThrow().stream()
    .flatMap(m -> m.actors().stream())
    .distinct()
    .count();
````

### 2) Total number of movies rated "PG-13"

````
long pg13Count = movies.orElseThrow().stream()
    .filter(m -> "PG-13".equals(m.certification()))
    .count();
````

### 3) Total number of genres

````
long totalGenres = movies.orElseThrow().stream()
    .flatMap(m -> m.genres().stream())
    .distinct()
    .count();
````

### 4) List of movies for each certification
````
Map<String, List<Movie>> moviesByCert = movies.orElseThrow().stream()
    .collect(Collectors.groupingBy(Movie::certification));
````

### 5) Number of movies for each certification

```
 Map<String, Long> countByCert = movies.orElseThrow().stream()
    .collect(Collectors.groupingBy(
        Movie::certification, 
        Collectors.counting()
    ));
```
### 6) List of movies for each actor
````
Map<String, List<String>> moviesByActor = movies.orElseThrow().stream()
    .flatMap(m -> m.actors().stream()
        .map(actor -> new AbstractMap.SimpleEntry<>(actor, m.title())))
    .collect(Collectors.groupingBy(
        Map.Entry::getKey,
        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
    ));
````

### 7) Number of movies for each actor
````
Map<String, Long> movieCountByActor = movies.orElseThrow().stream()
    .flatMap(m -> m.actors().stream())
    .collect(Collectors.groupingBy(
        actor -> actor, 
        Collectors.counting()
    ));
````
### 8) 5 most frequent directors (sorted)

````
movies.orElseThrow().stream()
    .flatMap(m -> m.director().stream()) // Assuming director() returns a List
    .collect(Collectors.groupingBy(d -> d, Collectors.counting()))
    .entrySet().stream()
    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
    .limit(5)
    .forEach(System.out::println);
````

### 9) 5 most actors (sorted)

````
movies.orElseThrow().stream()
    .flatMap(m -> m.actors().stream())
    .collect(Collectors.groupingBy(a -> a, Collectors.counting()))
    .entrySet().stream()
    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
    .limit(5)
    .forEach(System.out::println);
````

## 8) Streams in text processing (II)

````
import java.util.*;
import java.util.stream.*;
import java.util.function.Function;

public class AliceExercises {

    static List<String> allLines = Alice.lines().orElseThrow();
    static Map<Integer, List<String>> chapterLines = Alice.chapterLines().orElseThrow();

    // Split a line into words
    private static Stream<String> toWords(String line) {
        return Arrays.stream(line.trim().split("\\s+"))
                .map(w -> w.replaceAll("[^a-zA-Z_]", ""))
                .filter(w -> !w.isEmpty());
    }

    // Sheck if a word is italicized (wrapped in _word_)
    private static boolean isItalic(String word) {
        return word.startsWith("_") && word.endsWith("_") && word.length() > 2;
````

### 1) Total number of words

````
long total = chapterLines.values().stream()
                .flatMap(List::stream)
                .flatMap(AliceExercises::toWords)
                .count();
        System.out.println("Total words: " + total);
````

### 2) Total number of italicized words (i.e., _it_)

````
long italics = chapterLines.values().stream()
                .flatMap(List::stream)
                .flatMap(line -> Arrays.stream(line.trim().split("\\s+")))
                .filter(AliceExercises::isItalic)
                .count();
        System.out.println("Italicized words: " + italics);
````

### 3) Total number of words for each chapter

````
chapterLines.forEach((chapter, lines) -> {
            long count = lines.stream()
                    .flatMap(AliceExercises::toWords)
                    .count();
            System.out.println("Chapter " + chapter + " => " + count + " words");
        })
````

### 4) 10 most frequent words in the whole text (sorted)
````
System.out.println("Top 10 most frequent words:");
        chapterLines.values().stream()
                .flatMap(List::stream)
                .flatMap(AliceExercises::toWords)
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .forEach(e -> System.out.println(e.getKey() + " => " + e.getValue()));
````

### 5) 10 most frequent words for each chapter (sorted)

```
chapterLines.forEach((chapter, lines) -> {
            System.out.println("Chapter " + chapter + ":");
            lines.stream()
                    .flatMap(AliceExercises::toWords)
                    .map(String::toLowerCase)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .limit(10)
                    .forEach(e -> System.out.println("  " + e.getKey() + " => " + e.getValue()));
        })
```
### 6) 10 longest words in the whole text (sorted)
````
  System.out.println("Top 10 longest words:");
        chapterLines.values().stream()
                .flatMap(List::stream)
                .flatMap(AliceExercises::toWords)
                .map(String::toLowerCase)
                .distinct()
                .sorted(Comparator.comparingInt(String::length).reversed())
                .limit(10)
                .forEach(System.out::println);
````

### 7) 10 longest words for each chapter (sorted)
````
chapterLines.forEach((chapter, lines) -> {
            System.out.println("Chapter " + chapter + ":");
            lines.stream()
                    .flatMap(AliceExercises::toWords)
                    .map(String::toLowerCase)
                    .distinct()
                    .sorted(Comparator.comparingInt(String::length).reversed())
                    .limit(10)
                    .forEach(w -> System.out.println("  " + w));
        })
````
### 8) Frequency table of vowels in the whole text

````
chapterLines.values().stream()
                .flatMap(List::stream)
                .flatMap(line -> Alice.expand(line.toLowerCase()).stream())
                .filter(s -> "aeiou".contains(s))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.println(e.getKey() + " => " + e.getValue()));
````

### 9) Frequency table of vowels for each chapter

````
 chapterLines.forEach((chapter, lines) -> {
            System.out.println("Chapter " + chapter + ":");
            lines.stream()
                    .flatMap(line -> Alice.expand(line.toLowerCase()).stream())
                    .filter(s -> "aeiou".contains(s))
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(e -> System.out.println("  " + e.getKey() + " => " + e.getValue()));
        });
````
