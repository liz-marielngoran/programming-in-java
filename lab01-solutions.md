# Summary of Lab01

## 1)Concepts of encapsulation, inheritance and polymorphisms

### 1)concept of encapsulation

The encapsulation is a concept that allows to give access or not to an object. it is a security measure.
It is implemented with public, private or protected in the case of inheritance

### 2) Setter and getter

Setter is a method that is used to modify the state of an object.
```
public class Rectangle {
    protected int longueur;
    protected int largeur;
public void setLongueur() {
        this.longueur= longueur;
        }
```
Getter is a method that takes and gives us the state of an object.
```
public int getLargeur() {
    return largeur
    }
```
### 3)This/Super

"This" is a keyword used to reference a constructor when it is followed by () or an attribute when it is followed by a point
```
public rectangle () {}
public int calculate () {
    this(); //constructor
    return 2*(Longueur+largeur)
    }

public void setLongueur() {
        this.longueur= longueur; //attribute
        }
```
"Super" is used in case of inheritance. it is a keyword that is used to call constructor of the superclass in case of "super()" or use to call methods of the superclass in the case of "super."
```
public class square extends Rectangle () {
    private int diagonal
    public square() {
        super();
        this.diagonal=diagonal
        }

```
### 4)Inheritance
 
It is a concept that allows a new class to inherit attributes and methods from an existing class. It is implemented with the keyword "extends".
```
public class paving stone extends rectangle {
    private int height
    
    public void paving stone () {
        super();
        this.height=height;
    }
```
### 5)Polymorphism
It is a concept that allows a value or a variable to have more than one type and to operations to be performed on values of more than one type. We have main three forms
ad-hoc polymorphism, overriding, parametric polymorphism.

### 5.a)Ad-hoc polymorphism
it is a concept which allows to create methods with the same name but with different arguments
```
public class nombres {
    int addition ( int a; int b) {
        return a+b;
    }
    double addition (double a, double b) {
        return a+b;
       }
}
```
### 5.b)Overriding
It is a concept which is used to override methods in super class for subclasses. methods had the same signatures but different implementations.
```
public class animal {
    public void emmettreSon () {
        System.out.println("l'animal fait un bruit");
    }
public class chien extends animal () {
    @Override 
    public void emettreSon() {
        System.out.println("wouaf!")
    }
```
### 5.c)Parametric polymorphism
It is a concept in which we can define an only one function that will be reusing on every type of object by overriding or not.
```
public class array <T> {
    private T contenu;

    public void ajouter (T object) {
        this.contenu= objet;
    }
public class Main() {
    array <String> listeSubject = new array<>();
    listeSubject.ajouter("Français");
    array <Integer> listeInt = new array<>;
    listeInt.ajouter(10);
}
```
### 6) Relationship between inheritance and subtype polymorphism
Subtype polymorphism is used in case of inheritance to override a method in order to express a specific way in which a subclass is behaving.

### 7)

### 8)Tests

```
@Test
void testTotalAreaWithKnownShapes() {
    // Préparation des données (Given)
    List<Shape> shapes = List.of(
        new Rectangle(10, 2), // Aire = 20
        new Rectangle(5, 4)   // Aire = 20
    );

    // Exécution (When)
    double result = Main.totalArea(shapes);

    // Vérification (Then)
    // On attend 40.0 comme résultat
    assertEquals(40.0, result, 0.001);
} 
```

## 2)Static Members

### 1) static variable, static constant, static method
    a static variable is a variable that is not instance_related. It is like a global variable.
    a static constant is also a constant that is not instance-related. It is usable for all the object of the class and it is immutable.
    a Static method is a method that is still the same for all the instance of the class. It is like a global method.

### 2)Public visibility
    As they are immutable (constant), we do not need to put it on private.
### 3)No access to instance members
    Static methods do not access to instances members because they are class-related, not instance related. So if we create instances of a class and we call "class.staticMethod(fiels)", it won't work because there is a confusion: on which instance and with which field should the method be applied or performed?
    That's why they did not get visibility on instance members. Moreover, static method do not have reference like this.
### 4)Example of static method app
```
public class enfant {
    String nom;
    Int age;

    public static String afficherNom(enfant e1; enfant e2)(){
        public static void comparer(Joueur j1, Joueur j2) {
        if (e1.age > e2.age) {
            return e1.nom 
        } else {
            return e2.nom
            }
        }
    }
    
    public class Main {
    public static void main(String[] args) {
                enfant E1= new enfant("Alice", 5);
                enfant E2= new enfant("Bob", 10)
                Enfant.comparer (E1,E2)
       }
      }
```
## 3) Constructor, factory methods and singletons

### 1)Object Initialisation
When an objet is initialized, its default values are zero if it is an integer and null if it is a string. Static variables and statics constants are initialized at the same time, the class is. Concerning the anonymous blocks and the anonymous static block, they are also created when the class is and, they are executed before the constructor. This one (constructor) is executed when we use new in the main. Although, static anonymous blocs are executed only once, the first time we initialize the code (main).

### 2)For class D9

#### 2.1) Draw of inheritance
class D9 extends D1 and D1 extends B1 so

```
D9
├── inherits from D1
     └── inherits from B1
```
#### 2.2)Constructor calls
## Constructor Execution – Explanation Block

When new `D9()` is executed,the superclass constructor is invoked. Because `D9` extends `D1`, and `D1` extends `B1`, the constructor chain starts from the top of the hierarchy. Therefore, `B1()` executes first, followed by `D1()`. This ensures that the inherited part of the object is fully initialized before the subclass logic runs.
After the superclass constructors finish, the instance fields of `D9` are initialized in the order they are declared. The fields `d1` and d4 are first assigned their default value. Then `d7 = new D7()` is executed, which triggers the constructor of `D7`.
Next, the instance initializer block is executed. In this block, `d4 = new D4()` runs, which calls the constructor of `D4`.
Finally, the body of the `D9` constructor executes. The two `System.out.println` statements run, and then `d1 = new D1()` creates a new `D1` object. Since `D1` extends `B1`, this again triggers the constructor chain `B1()` followed by `D1()`.

### 3)Comparison of capabilities of constructors and factory methods
We use factory methods for creating instance at any time.
Constructors allocate memory directly to create a new object of the class instead of factory methods which can return an instance of a subclass or an interface's implement 
Factory methods could recreate a pre-existent instance as Singleton, a kind of recycling. They are used when constructors are private and allow to verify parameters before allocating memory for an object (if it is not yet created).

### 4)applications of Singleton pattern

#### 1)Logging System
A logging class is often implemented as a Singleton.
You want one single logging instance managing all log messages in the application to:

- Avoid multiple log files
- Prevent configuration conflicts
- Ensure consistent logging behavior

```Example: A Logger class where all classes call Logger.getInstance().```

#### 2)Database Connection Manager
A database connection manager is often a Singleton.
- Ensures only one shared connection (or connection manager) exists
- Controls access to the database
- Avoids unnecessary multiple connections (which are expensive)

```Example: DatabaseConnection.getInstance() returns the same connection manager throughout the app.```

## 4)Immutable Objets classes and Java Records

### 1)Strategy
For defining an immutable object, we must provide final access to the class and its fields and do not provide setters.

### 2)Concepts of immutable class and immutable object
A class is immutable when its fields are immutable. Immutable classes are created by developers. Immutable object are the same they are just initialized by java creators.

### 3)Advantages of immutable objects
Immutable objects allow to avoid conflicts when many developers are using the object at the same time. It also provides a max security to the object with the "final" keyword. 

### 4)Java Records uses

Java record is used when a class is immutable and when we do not want the class to be inherited.
```
public class étudiant{
    private String name;
    private int age;
    
    public static String getName() {
        return name
     }
}
public record étudiant ( String name int age) {
    public String name()
    } 
```

## 5)Overriding hashCodes, equal and toString

### 1)Difference between == and equals

"==" compares adddress for reference types and compares values for primitives types. 
"equals" does not apply to primitives. which is used to compare the objects themselves. Its default implementation behave like "==" with primitives but in many cases, it has to be overridden to apply logical equality.


### 2)o1.equals(o2) $\implies$ hashCode(o1) == hashCode(o2)
 Hashcode is a method that transforms an object into its entire value. So if two objects are equals, they obviously have the same entire value in hashcode. Then we know that this value is located on an only one address, so the references of o1 and o2 are going to be the same. That's why equals implies hashcode.

### 3)

### 4)General contract of hashcode and equals

#### 4.1)Contract of equals()

For any non-null reference values x, y, z:
```
    Reflexive: x.equals(x) must return true
    Symmetric: If x.equals(y) is true → y.equals(x) must be true
    Transitive: If:
                    x.equals(y) is true
                    y.equals(z) is true
                Then: x.equals(z) must be true

    Consistent: Multiple calls must return same result (if fields unchanged)
    Non-null: x.equals(null) must return false
```
#### 4.2)General Contract of hashCode()
```
    Internal consistency: If object state doesn’t change → hashCode must remain same
    Equal objects rule: If x.equals(y) → x.hashCode() == y.hashCode()
    Unequal objects: If x.hashCode() != y.hashCode() → objects must not be equal
                        But: If hash codes are equal → objects may or may not be equal.
```
### 5) Generation of JavaDOC documentation



