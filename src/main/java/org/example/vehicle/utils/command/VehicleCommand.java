package org.example.vehicle.utils.command;

import org.example.vehicle.Vehicle;

import java.io.OutputStreamWriter;

public interface VehicleCommand {
    void execute(Vehicle vehicle, OutputStreamWriter outputStream);
}
