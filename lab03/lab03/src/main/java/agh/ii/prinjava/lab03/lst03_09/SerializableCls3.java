package agh.ii.prinjava.lab03.lst03_09;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

class SerializableCls3 implements Serializable {
    private String name;
    private transient NonSerializableClass1 nsc; // <- since it's not serializable, it must be transient

    public SerializableCls3(String name, NonSerializableClass1 nsc) {
        this.name = name;
        this.nsc = nsc;
    }

    @Override
    public String toString() {
        return "SerializableCls3{" + "name='" + name + '\'' + ", nsc=" + nsc + '}';
    }
}