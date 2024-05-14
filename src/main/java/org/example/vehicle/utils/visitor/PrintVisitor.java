package org.example.vehicle.utils.visitor;

import org.example.vehicle.Bike;
import org.example.vehicle.Car;
import org.example.vehicle.utils.VehicleUtil;
import org.example.vehicle.utils.command.VehicleColumnWriteCommand;
import org.example.vehicle.utils.command.VehicleRowWriteCommand;

import java.io.OutputStreamWriter;

public class PrintVisitor implements Visitor {
    @Override
    public void visit(Car car) {
        VehicleUtil.setVehicleCommand(new VehicleRowWriteCommand());
        VehicleUtil.printInStream(car, new OutputStreamWriter(System.out));
    }

    @Override
    public void visit(Bike bike) {
        VehicleUtil.setVehicleCommand(new VehicleColumnWriteCommand());
        VehicleUtil.printInStream(bike, new OutputStreamWriter(System.out));
    }
}
