package agh.ii.prinjava.lab02.lst02_06;
abstract class Shape {
    abstract double area();

    public Shape() {
    }
}

class Circle extends Shape {
    private double r;

    @Override
    double area() {
        System.out.println("Circle.area()");
        return Math.PI * r * r;
    }

    public Circle(double r) {
        this.r = r;
    }
}