package org.example.vehicle.factory;

import org.example.vehicle.Bike;
import org.example.vehicle.Vehicle;

public class BikeFactory implements VehicleFactory {
    @Override
    public Vehicle createInstant(String mark, int size) {
        return new Bike(mark, size);
    }
}
