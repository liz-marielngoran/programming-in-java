# Summary of Lab06

## 1)Functional interfaces, lambda expressions and method references

### 1)Functional interface, lambda expression, and method reference

- A functional interface is an interface that has only one abstract method.
- A lambda expression is an anonymous function designed to implement a functional interface````(parameters -> {body}````
- Method references are a special type of lambda expressions. They’re often used to create simple lambda expressions by referencing existing methods.
  They are a shorthand way to refer to an existing method without invoking it. They were introduced in Java 8 to make lambda expressions shorter, cleaner, and more readable.
  Method references use the double colon (::) operator and are mainly used with functional interfaces. ```class::method```

### 2) Anonymous function

f1(x)=x−2; x∈ℝ
```
x -> x-2
```
f2(x,y)=(√x2+y2); x,y∈ℝ
```
(x,y) -> Math.sqrt(x*x + y*y)
```
f3(x,y,z)=(√x2+y2+z2); x,y,z∈ℤ
````
(x,y,z) -> Math.sqrt(x*x + y*y + z*z)
````

### 3)anonymous functions

"This" is a keyword used to reference a constructor when it is followed by () or an attribute when it is followed by a point
```
import java.util.function.DoubleUnaryOperator;
public class LambdaTest {
    public static void main(String[] args) {
        
        DoubleUnaryOperator sqrt = Math::sqrt; // Maths.sqrt(x)
        DoubleUnaryOperator abs  = Math::abs; // Maths.abs(x)
        DoubleUnaryOperator log  = Math::log; //Maths.log(x)
        DoubleUnaryOperator id   = x -> x; // 

        // Test
        double input = 25.0;
        double negativeInput = -10.5;

        System.out.println("Input: " + input);
        System.out.println("Square Root: " + sqrt.applyAsDouble(input));    // 5.0
        System.out.println("Absolute (-10.5): " + abs.applyAsDouble(negativeInput)); // 10.5
        System.out.println("Logarithm: " + log.applyAsDouble(input));      // 3.218...
        System.out.println("Identity: " + id.applyAsDouble(input));        // 25.0
    }
}
```

### 4) Code completion
 
It is a concept that allows a new class to inherit attributes and methods from an existing class. It is implemented with the keyword "extends".
```
 @FunctionalInterface
 interface FunIf<T, R> {
     R apply(T t);
 }
```
````
// f1: String to Integer (Converts text "123" to number 123)
FunIf<String, Integer> f1 = s -> Integer.parseInt(s);

// f2: Integer to String (Converts number 50 to text "50")
FunIf<Integer, String> f2 = x -> String.valueOf(x);

// f3: Double to Double (Returns the absolute value)
FunIf<Double, Double> f3 = x -> Math.sqrt(x);

// f4: Integer to Boolean (Checks if the number is even)
FunIf<Integer, Boolean> f4 = x -> x % 2 == 0;

// f5: Boolean to Integer (Map True to 1 and False to 0)
FunIf<Boolean, Integer> f5 = b -> b ? 1 : 0;

// f6: Boolean to Boolean (The Logical NOT - flips the value)
FunIf<Boolean, Boolean> f6 = b -> !b;
````
### 5) Previous exercise using method references instead of lambda expressions
````
// f1: String to Integer (Converts text "123" to number 123)
FunIf<String, Integer> f1 = Integer::parseInt;
System.out.println(f1.apply("abc"))

// f2: Integer to String (Converts number 50 to text "50")
FunIf<Integer, String> f2 = String::valueOf;
System.out.println(f1.apply(x))

// f3: Double to Double (Returns the absolute value)
FunIf<Double, Double> f3 = Math::sqrt;
System.out.println(f1.apply(x))

// f4: Integer to Boolean (Checks if the number is even)
FunIf<Integer, Boolean> f4 = x -> x % 2 == 0;

// f5: Boolean to Integer (Map True to 1 and False to 0)
FunIf<Boolean, Integer> f5 = b -> b ? 1 : 0;

// f6: Boolean to Boolean (The Logical NOT - flips the value)
FunIf<Boolean, Boolean> f6 = b -> !b;
````

## 2)Standard functional interfaces

### 2) Examples
    ```
        // BiConsumer<T, U> - takes two args, returns nothing
        BiConsumer<String, String> bc = (s1, s2) -> System.out.println(s1 + " " + s2);
        bc.accept("Hello", "World");

        // BiFunction<T, U, R> - takes two args, returns a result
        BiFunction<String, Integer, String> biFunction = (s, n) -> s.repeat(n);
        System.out.println(biFunction.apply("Hi!", 3));

        // BinaryOperator<T> - two args of same type, returns same type
        BinaryOperator<Integer> binaryOperator = (a, b) -> a + b;
        System.out.println(binaryOperator.apply(10, 20));

        // BiPredicate<T, U> - takes two args, returns boolean
        BiPredicate<String, Integer> biPredicate = (s, n) -> s.length() == n;
        System.out.println(biPredicate.test("Hello", 5));

        // BooleanSupplier - supplies a boolean
        BooleanSupplier booleanSupplier = () -> true;
        System.out.println(booleanSupplier.getAsBoolean());

        // Consumer<T> - takes one arg, returns nothing
        Consumer<String> consumer = s -> System.out.println("Consumed: " + s);
        consumer.accept("Java");

        // Function<T, R> - takes one arg, returns a result
        Function<String, Integer> function = s -> s.length();
        System.out.println(function.apply("Hello"));

        // Predicate<T> - takes one arg, returns boolean
        Predicate<String> predicate = s -> s.isEmpty();
        System.out.println(predicate.test(""));

        // Supplier<T> - takes no args, returns a result
        Supplier<String> supplier = () -> "Hello from Supplier";
        System.out.println(supplier.get());

        // UnaryOperator<T> - one arg, returns same type
        UnaryOperator<String> unaryOperator = s -> s.toUpperCase();
        System.out.println(unaryOperator.apply("hello"));

        // --- Primitive specializations ---

        // DoubleBinaryOperator
        DoubleBinaryOperator doubleBinaryOp = (a, b) -> a * b;
        System.out.println(doubleBinaryOp.applyAsDouble(2.5, 4.0));

        // DoubleConsumer
        DoubleConsumer doubleConsumer = d -> System.out.println("Double: " + d);
        doubleConsumer.accept(3.14);

        // DoubleFunction<R>
        DoubleFunction<String> doubleFunction = d -> "Value: " + d;
        System.out.println(doubleFunction.apply(9.99));

        // DoublePredicate
        DoublePredicate doublePredicate = d -> d > 0;
        System.out.println(doublePredicate.test(-1.0));

        // DoubleSupplier
        DoubleSupplier doubleSupplier = () -> Math.PI;
        System.out.println(doubleSupplier.getAsDouble());

        // DoubleToIntFunction
        DoubleToIntFunction doubleToInt = d -> (int) d;
        System.out.println(doubleToInt.applyAsInt(9.7));

        // DoubleToLongFunction
        DoubleToLongFunction doubleToLong = d -> (long) d;
        System.out.println(doubleToLong.applyAsLong(123.456));

        // DoubleUnaryOperator
        DoubleUnaryOperator doubleUnary = d -> d * 2;
        System.out.println(doubleUnary.applyAsDouble(5.0));

        // IntBinaryOperator
        IntBinaryOperator intBinaryOp = (a, b) -> a - b;
        System.out.println(intBinaryOp.applyAsInt(10, 3));

        // IntConsumer
        IntConsumer intConsumer = i -> System.out.println("Int: " + i);
        intConsumer.accept(42);

        // IntFunction<R>
        IntFunction<String> intFunction = i -> "Number " + i;
        System.out.println(intFunction.apply(7));

        // IntPredicate
        IntPredicate intPredicate = i -> i % 2 == 0;
        System.out.println(intPredicate.test(4));

        // IntSupplier
        IntSupplier intSupplier = () -> 42;
        System.out.println(intSupplier.getAsInt());

        // IntToDoubleFunction
        IntToDoubleFunction intToDouble = i -> i / 2.0;
        System.out.println(intToDouble.applyAsDouble(5));

        // IntToLongFunction
        IntToLongFunction intToLong = i -> (long) i * 1000;
        System.out.println(intToLong.applyAsLong(3));

        // IntUnaryOperator
        IntUnaryOperator intUnary = i -> i * i;
        System.out.println(intUnary.applyAsInt(6));

        // LongBinaryOperator
        LongBinaryOperator longBinaryOp = (a, b) -> a + b;
        System.out.println(longBinaryOp.applyAsLong(100L, 200L));

        // LongConsumer
        LongConsumer longConsumer = l -> System.out.println("Long: " + l);
        longConsumer.accept(999L);

        // LongFunction<R>
        LongFunction<String> longFunction = l -> "Long value: " + l;
        System.out.println(longFunction.apply(12345L));

        // LongPredicate
        LongPredicate longPredicate = l -> l > 100L;
        System.out.println(longPredicate.test(200L));

        // LongSupplier
        LongSupplier longSupplier = () -> System.currentTimeMillis();
        System.out.println(longSupplier.getAsLong());

        // LongToDoubleFunction
        LongToDoubleFunction longToDouble = l -> l / 100.0;
        System.out.println(longToDouble.applyAsDouble(500L));

        // LongToIntFunction
        LongToIntFunction longToInt = l -> (int) l;
        System.out.println(longToInt.applyAsInt(42L));

        // LongUnaryOperator
        LongUnaryOperator longUnary = l -> l * 2;
        System.out.println(longUnary.applyAsLong(10L));

        // ObjDoubleConsumer<T>
        ObjDoubleConsumer<String> objDoubleConsumer = (s, d) -> System.out.println(s + ": " + d);
        objDoubleConsumer.accept("PI", 3.14);

        // ObjIntConsumer<T>
        ObjIntConsumer<String> objIntConsumer = (s, i) -> System.out.println(s + ": " + i);
        objIntConsumer.accept("Count", 10);

        // ObjLongConsumer<T>
        ObjLongConsumer<String> objLongConsumer = (s, l) -> System.out.println(s + ": " + l);
        objLongConsumer.accept("Timestamp", System.currentTimeMillis());

        // ToDoubleBiFunction<T, U>
        ToDoubleBiFunction<String, String> toDoubleBiFunc = (s1, s2) -> (double) (s1.length() + s2.length());
        System.out.println(toDoubleBiFunc.applyAsDouble("Hello", "World"));

        // ToDoubleFunction<T>
        ToDoubleFunction<String> toDoubleFunc = s -> s.length() * 1.5;
        System.out.println(toDoubleFunc.applyAsDouble("Java"));

        // ToIntBiFunction<T, U>
        ToIntBiFunction<String, String> toIntBiFunc = (s1, s2) -> s1.length() + s2.length();
        System.out.println(toIntBiFunc.applyAsInt("Hi", "There"));

        // ToIntFunction<T>
        ToIntFunction<String> toIntFunc = s -> s.length();
        System.out.println(toIntFunc.applyAsInt("Hello"));

        // ToLongBiFunction<T, U>
        ToLongBiFunction<String, String> toLongBiFunc = (s1, s2) -> (long) s1.length() * s2.length();
        System.out.println(toLongBiFunc.applyAsLong("Hello", "World"));

        // ToLongFunction<T>
        ToLongFunction<String> toLongFunc = s -> (long) s.length() * 100;
        System.out.println(toLongFunc.applyAsLong("Java"));
    }
    
    }```

### 3) Rationale behind the primitive type specialisations of the standard generic functional interfaces

To avoid the performance cost of "boxing" (converting a primitive like int to an object like Integer) or the unboxing, the package includes interfaces versions for int, long, and double because in Java, generics only work with reference types, not primitives.
A primitive int is 4 bytes, but an Integer object is typically 16–24 bytes. Wrapping millions of primitives into objects wastes massive amounts of RAM. Then Moving data between the Stack (primitives) and the Heap (objects) takes time.
MoreOver, Creating thousands of temporary "wrapper" objects forces the Garbage Collector to run more frequently, slowing down your entire application.

```
// Forces boxing: int → Integer → int
Supplier<Integer> s = () -> 42;
int result = s.get(); // unboxing here — overhead!

// With primitive specialisation — no boxing at all
IntSupplier s = () -> 42;
int result = s.getAsInt(); // direct primitive, no overhead
```
```
  Every time a primitive value passes through a generic functional interface:

primitive value (int, double, long)
                ↓
heap allocation (Integer, Double, Long)   ← autoboxing
                ↓
generic functional interface
                ↓
unboxing back to primitive               ← extra cost
                ↓
garbage collection of wrapper object     ← GC pressure  
```

## 3) Higher-order functions

### 1) Sum  $\sum_{i=1}^{15} i^5$

````
public static double sumOfWith(int n, DoubleUnaryOperator f) {
        return IntStream.rangeClosed(1, n)
                        .mapToDouble(i -> f.applyAsDouble(i))
                        .sum();
    }

//Test
double result = sumOfWith(1, 15, i -> Math.pow(i, 5));
````

### 2) Maclaurin Polynomial for $e^x$

```
DoubleUnaryOperator expApproxUpTo(int n) {
        return x -> {
            double sum = 0;
            double term = 1; // x^0 / 0! = 1
            for (int k = 1; k <= n; k++) {
                sum += term;
                term *= x / k; // x^k / k! = x^(k-1)/(k-1)! * x/k
            }
            sum += term; // add last term
            return sum;
        };
    }
    
   //test
  for (int n = 1; n < 6; n++) {
            DoubleUnaryOperator approx = expApproxUpTo(n);
            System.out.printf("expApproxUpTo(%d)(1.0) = %.6f  (exact e^1 = %.6f)%n",
                    n, approx.applyAsDouble(1.0), Math.E);
        }
```

### 3) First Derivative Approximation

````
public static DoubleUnaryOperator dfr(DoubleUnaryOperator f, double h) {
        return x -> (f.applyAsDouble(x + h) - f.applyAsDouble(x)) / h;
    }
  
  //test
        System.out.println("Derivative of sin(x) at x=0, exact = 1.0");
        for (double h : new double[]{1e-1, 1e-3, 1e-6, 1e-9}) {
            DoubleUnaryOperator derivative = dfr(sinFunc, h);
            double approx = derivative.applyAsDouble(x0);
            System.out.printf("  h=%.0e → f'≈%.10f  error=%.2e%n", h, approx, Math.abs(1.0 - approx));
        }
   
````

### 4) Second Derivative Approximation

```
public DoubleUnaryOperator d2f(DoubleUnaryOperator f, double h) {
    return x0 -> (f.applyAsDouble(x0 + h) - 2 * f.applyAsDouble(x0) + f.applyAsDouble(x0 - h)) / 
                 Math.pow(h, 2);
}

// Test example with f(x) = x^3 (f'' should be 6x, so at x=2, result is 12)
DoubleUnaryOperator cube = x -> x * x * x;
DoubleUnaryOperator secondDeriv = d2f(cube, 0.001);
System.out.println(secondDeriv.applyAsDouble(2)); // ~12.0
```

## 5) Analysis of applyAll
This method is a classic example of mapping a list of functions onto a single value.

- It uses <T, R>, meaning it can take an input of any type T and return a result of any type R.
- It iterates through a list of functions (fs). For each function, it applies the value x0.
- It collects all the different results into a new List<R>.
- By using Collections.unmodifiableList, it ensures that the caller cannot modify the result list (adding/removing elements), which is a "best practice" in functional programming.

```
//test
List<Function<String, Integer>> functions = List.of(
    String::length,
    s -> s.indexOf("a"),
    s -> s.split(" ").length
);

List<Integer> results = applyAll(functions, "Hello Java");
// Output: [10, 7, 2]
```

## 4) Function composition

### 1) Functions composition using Function.compose

````
import java.util.List;
import java.util.function.Function;

 // f1(x) = 2x,  g1(x) = x²
        Function<Double, Double> f1 = x -> 2 * x;
        Function<Double, Double> g1 = x -> x * x;
        Function<Double, Double> f1_compose_g1 = f1.compose(g1); // f1(g1(x)) = 2x²

        // f2(x) = sin(x),  g2(x) = (1-x)/(1+x²)
        Function<Double, Double> f2 = x -> Math.sin(x);
        Function<Double, Double> g2 = x -> (1 - x) / (1 + x * x);
        Function<Double, Double> f2_compose_g2 = f2.compose(g2); // f2(g2(x)) = sin((1-x)/(1+x²))

        // f3(x) = (1-sin(x))/(1+2x²),  g3(x) = cos(x)
        Function<Double, Double> f3 = x -> (1 - Math.sin(x)) / (1 + 2 * x * x);
        Function<Double, Double> g3 = x -> Math.cos(x);
        Function<Double, Double> f3_compose_g3 = f3.compose(g3); // f3(g3(x)) = (1-sin(cos(x)))/(1+2cos²(x))
````

### 2) Previous exercise using Function.andThen

We keep the first two line of each function implementation

````
Function<Double, Double> andThen1 = g1.andThen(f1);
Function<Double, Double> andThen2 = g2.andThen(f2);
Function<Double, Double> andThen3 = g3.andThen(f3);
````

### 3) a function/method that composes a given list of functions

```
public static <T> Function<T, T> composeFunctions(List<Function<T, T>> functions) {
    Function<T, T> result = Function.identity();
    for (Function<T, T> f : functions) {
        result = result.andThen(f);
    }
    return result;
}
```

## 5) Dealing with optional data

### 2) Pros and cons of the following approaches to represent a "no-valid-result" of a function/metho

#### Throwing an exception
- Pros: Explicitly signals a failure; forces the caller to handle it (if checked).
- Cons: Very expensive (heavy CPU cost to create stack traces); breaks the program flow.

#### Returning null
- Pros: Extremely fast and memory-efficient.
- Cons: easy to forget a null-check, leading to runtime crashes.

#### Using Optional
- Pros: tells the caller "this might be empty"; allows functional chaining (ifPresent, map).
- Cons: Slight memory overhead (object wrapper); can be misused if used as a field or parameter.

### 3) Three variants of a method that returns the tail of a given list (see headOf_v1, headOf_v2, headOf_v3 in lst01_05)
The "tail" of a list is everything except the first element. If the list is empty, there is no tail.

#### Throwing an exception
````
public <T> List<T> tailOf_v2(List<T> list) {
if (list == null || list.isEmpty()) {
throw new NoSuchElementException("List has no tail");
}
return list.subList(1, list.size());
}
````

#### Returning null
````
public <T> List<T> tailOf_v1(List<T> list) {
    if (list == null || list.isEmpty()) return null;
    return list.subList(1, list.size());
}
````
#### Using Optional

```
public <T> Optional<List<T>> tailOf_v3(List<T> list) {
    if (list == null || list.isEmpty()) {
        return Optional.empty();
    }
    return Optional.of(list.subList(1, list.size()));
}
```

### 4) Methods that could have Optional as the return type in proj1

- removeFirst()
- removeLast()
- Peek()
- dequeue()
- 


