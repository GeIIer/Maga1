package org.example.vehicle.utils.dao;

import org.example.vehicle.Car;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class CarByteParser implements ParserDao<Car> {
    @Override
    public List<Car> readAll(String filename) {
        try (FileInputStream fIS = new FileInputStream(filename);
             ObjectInputStream oIS = new ObjectInputStream(fIS)) {
            return (List<Car>) oIS.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isValidFile(String fileType) {
        return "BIN".equals(fileType);
    }
}
