package agh.ii.prinjava.lab04.exc04_01;

public class Pair<F,S> implements Cloneable {
    private F first;
    private S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }
    public void setFirst(F first) {
        this.first = first;
    }
    public S getSecond() {
        return second;
    }
    public void setSecond(S second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "Pair{" + "first=" + first + ", second=" + second + '}';
    }

    @Override
    public boolean equals(Object o) {
        // 1. Check if they are the exact same instance in memory
        if (this == o) return true;

        // 2. Check if the other object is null or a different class
        if (o == null || getClass() != o.getClass()) return false;

        // 3. Cast the object to Pair (using raw type here is okay for the check)
        Pair<?, ?> pair = (Pair<?, ?>) o;

        // 4. Compare both fields (handling potential nulls)
        if (first != null ? !first.equals(pair.first) : pair.first != null) return false;
        return second != null ? second.equals(pair.second) : pair.second == null;
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        // Multiply by a prime number (like 31) to reduce collisions
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }

    @Override
    public Pair<F, S> clone() {
        try {
            // Casting the Object returned by super.clone() to our specific Pair type
            return (Pair<F, S>) super.clone();
        } catch (CloneNotSupportedException e) {
            // This should never happen since we implement Cloneable
            throw new AssertionError();
        }
    }

}
