package agh.ii.prinjava.lab03.lst03_09;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
/**
 * <i>Serialization</i> - a process/mechanism of converting object's state along with its class information
 * into a byte stream (sequence of bytes), as well as the process of rebuilding those bytes into
 * a live object at some future time (often called <i>deserialization</i>).
 *
 * <p>Note: serialization used to be important, but in contemporary applications is not used very often
 *
 * <p>{@link Serializable} is only a marker interface - it simply allows the serialization mechanism to verify
 * that the class can be persisted
 */
class SerializableCls1 implements Serializable {
    private String name;

    public SerializableCls1(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SerializableCls1{" + "name='" + name + '\'' + '}';
    }
}