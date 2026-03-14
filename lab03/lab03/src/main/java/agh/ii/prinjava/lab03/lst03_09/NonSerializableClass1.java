package agh.ii.prinjava.lab03.lst03_09;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

class NonSerializableClass1 {
    private String name;

    public NonSerializableClass1(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NotSerializableClass1{" + "name='" + name + '\'' + '}';
    }
}