package org.example.vehicle.utils.command;

import org.example.exception.NoSuchModelNameException;
import org.example.vehicle.Vehicle;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.StringJoiner;

public class VehicleColumnWriteCommand implements VehicleCommand {
    @Override
    public void execute(Vehicle vehicle, OutputStreamWriter outputStream) {
        try {
            for (String name : vehicle.getAllName()) {
                String model = new StringJoiner(", ","[", "] \n")
                        .add("Название = '" + name + "'")
                        .add("Цена = " + vehicle.getPriceByName(name))
                        .toString();
                outputStream.write(model);
                outputStream.flush();
            }
        } catch (IOException | NoSuchModelNameException ex) {
            throw new RuntimeException(ex);
        }
    }
}
