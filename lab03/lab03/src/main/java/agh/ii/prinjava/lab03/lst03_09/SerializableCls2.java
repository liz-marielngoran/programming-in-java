package agh.ii.prinjava.lab03.lst03_09;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
/**
 * {@code transient} modifier prevents an instance variable from being serialized
 *
 * <p>Note: certain instance variables should not be serialized, e.g., database connections that
 * are meaningless when an object is reconstituted. Also, when an object keeps a cache of values,
 * it might be better to drop the cache and recompute it instead of storing it.
 */
class SerializableCls2 implements Serializable {
    private String name;
    private transient int age; // <- a transient field, this will not be serialized (because it's not needed)

    public SerializableCls2(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "SerializableCls2{" + "name='" + name + '\'' + ", age=" + age + '}';
    }
}