package org.example.vehicle.utils.writers;

import org.example.exception.VehicleWriterException;
import org.example.vehicle.Vehicle;
import org.example.vehicle.utils.command.VehicleColumnWriteCommand;

import java.io.FileWriter;
import java.io.IOException;

public class VehicleColumnWriter extends VehicleWriter {
    private static final VehicleColumnWriteCommand vehicleColumnWriteCommand = new VehicleColumnWriteCommand();
    private final int vehicleCount;

    public VehicleColumnWriter(int vehicleCount, String path) {
        super(path);
        this.vehicleCount = vehicleCount;
    }

    @Override
    public boolean writeToFile(Vehicle vehicle) throws VehicleWriterException {
        if (vehicle.lengthModels() > vehicleCount) {
            try (FileWriter out = new FileWriter(path)) {
                vehicleColumnWriteCommand.execute(vehicle, out);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return true;
        }
        return checkNext(vehicle);
    }
}
