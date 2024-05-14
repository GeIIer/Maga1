package org.example.vehicle.utils.visitor;

import org.example.vehicle.Bike;
import org.example.vehicle.Car;

public interface Visitor {
    void visit(Car car);
    void visit(Bike bike);
}
