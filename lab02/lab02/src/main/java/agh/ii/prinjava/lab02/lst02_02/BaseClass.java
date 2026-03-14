class BaseClass {
    public BaseClass() {
        System.out.println("Initialisation of BaseClass...");

        fM1(); // OK, fM1 is final

        m2(); // Not recommended!
        m3(); // As above

        fM4(); // OK, fM1 is final
        m5(); // OK, m3 is private -> cannot be overridden in subclasses
    }

    public final void fM1() {
        System.out.println("BaseClass.fM1(): performing init phase (1)");
    }

    public void m2() {
        System.out.println("BaseClass.m1(): performing init phase (2)");
    }

    protected void m3() {
        System.out.println("BaseClass.21(): performing init phase (3)");
    }

    public final void fM4() {
        System.out.println("BaseClass.fM2(): performing init phase (4)");
    }

    private void m5() {
        System.out.println("BaseClass.m3(): performing init phase (5)");
    }
}

class DerivedClass extends BaseClass {

    @Override
    public void m2() {
        System.out.println("Breaking the consistent state of the inherited part (2)");
    }

    @Override
    protected void m3() {
        System.out.println("Breaking the consistent state of the inherited part (3)");
    }

    // 'fM1()' cannot override 'fM1()' in 'BaseClass'; overridden method is final
    // @Override public final void fM1() {}
}