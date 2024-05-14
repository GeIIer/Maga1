package org.example.vehicle.utils.writers;

import org.example.exception.VehicleWriterException;
import org.example.vehicle.Vehicle;
import org.example.vehicle.utils.command.VehicleRowWriteCommand;

import java.io.FileWriter;
import java.io.IOException;

public class VehicleRowWriter extends VehicleWriter {
    private final VehicleRowWriteCommand vehicleRowWriteCommand = new VehicleRowWriteCommand();
    private final int vehicleCount;

    public VehicleRowWriter(int vehicleCount, String path) {
        super(path);
        this.vehicleCount = vehicleCount;
    }

    @Override
    public boolean writeToFile(Vehicle vehicle) throws VehicleWriterException {
        if (vehicle.lengthModels() <= vehicleCount) {
            try (FileWriter out = new FileWriter(path)) {
                vehicleRowWriteCommand.execute(vehicle, out);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return true;
        }
        return checkNext(vehicle);
    }
}
