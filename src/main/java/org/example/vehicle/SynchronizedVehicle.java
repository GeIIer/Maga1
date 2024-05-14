package org.example.vehicle;

import org.example.exception.DuplicateModelNameException;
import org.example.exception.NoSuchModelNameException;
import org.example.vehicle.utils.visitor.Visitor;

public class SynchronizedVehicle implements Vehicle {
    private volatile Vehicle vehicle;

    public SynchronizedVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public synchronized String getMark() {
        return vehicle.getMark();
    }

    @Override
    public synchronized void setMark(String mark) {
        vehicle.setMark(mark);
    }

    @Override
    public synchronized int lengthModels() {
        return vehicle.lengthModels();
    }

    @Override
    public synchronized String[] getAllName() {
        return vehicle.getAllName();
    }

    @Override
    public synchronized double getPriceByName(String name) throws NoSuchModelNameException {
        return vehicle.getPriceByName(name);
    }

    @Override
    public synchronized void setNewNameByOldName(String newName, String oldName) throws NoSuchModelNameException, DuplicateModelNameException {
        vehicle.setNewNameByOldName(newName,oldName);
    }

    @Override
    public synchronized void setPriceByName(String name, double newPrice) throws NoSuchModelNameException {
        vehicle.setPriceByName(name, newPrice);
    }

    @Override
    public synchronized double[] getAllPrice() {
        return vehicle.getAllPrice();
    }

    @Override
    public synchronized void addNewModel(String modelName, double price) throws DuplicateModelNameException {
        vehicle.addNewModel(modelName, price);
    }

    @Override
    public synchronized void deleteModel(String modelName) throws NoSuchModelNameException {
        vehicle.deleteModel(modelName);
    }

    @Override
    public synchronized Object clone() {
        return vehicle.clone();
    }

    @Override
    public synchronized void accept(Visitor visitor) {
        vehicle.accept(visitor);
    }
}
