package agh.ii.prinjava.lab03.lst03_09;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Serializable because of the parent class (which is serializable)
 */
class SerializableCls4 extends SerializableCls1 {
    public SerializableCls4(String name) {
        super(name);
    }
}