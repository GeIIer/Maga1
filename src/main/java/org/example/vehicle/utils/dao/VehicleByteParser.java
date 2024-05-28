package org.example.vehicle.utils.dao;

import org.example.vehicle.Vehicle;

import java.io.*;
import java.util.List;

public class VehicleByteParser implements ParserDao<Vehicle> {
    @Override
    public void write(String filename, List<Vehicle> objects) {
        try (FileOutputStream fOS = new FileOutputStream(filename);
             ObjectOutputStream ooS = new ObjectOutputStream(fOS)) {
            ooS.writeObject(objects);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Vehicle> readAll(String filename) {
        try (FileInputStream fIS = new FileInputStream(filename);
             ObjectInputStream oIS = new ObjectInputStream(fIS)) {
            return (List<Vehicle>) oIS.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isValidFile(String fileType) {
        return "BIN".equals(fileType);
    }
}
