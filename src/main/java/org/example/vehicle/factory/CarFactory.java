package org.example.vehicle.factory;

import org.example.vehicle.Car;
import org.example.vehicle.Vehicle;

public class CarFactory implements VehicleFactory {
    @Override
    public Vehicle createInstant(String mark, int size) {
        return new Car(mark, size);
    }
}
