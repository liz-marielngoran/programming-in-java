package agh.ii.prinjava.lab02.lst02_08;

/**
 * An example of a tight coupling. In most cases a VERY bad design decision <br>
 * (interfaces, as contracts, should be used instead -> loose coupling)
 */
class C7 {
    private C6 c6 = new C6(); // (very) tight coupling

    void doC7Stuff() {
        System.out.println("C7.doStuff");
        c6.doStuff();
    }
}
