package agh.ii.prinjava.lab04.lst04_01;

class GenBox<T> {
    private T x;
    public GenBox(T x) { this.x = x; }
    public void setX(T x) { this.x = x; }
    public T getX() { return x; }
}