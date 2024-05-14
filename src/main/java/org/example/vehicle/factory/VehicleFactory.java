package org.example.vehicle.factory;

import org.example.vehicle.Vehicle;

public interface VehicleFactory {
    Vehicle createInstant(String mark, int size);
}
