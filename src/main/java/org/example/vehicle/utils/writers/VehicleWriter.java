package org.example.vehicle.utils.writers;

import org.example.exception.VehicleWriterException;
import org.example.vehicle.Vehicle;

public abstract class VehicleWriter {
    protected final String path;
    private VehicleWriter next;

    public VehicleWriter(String path) {
        this.path = path;
    }

    public static VehicleWriter link(VehicleWriter first, VehicleWriter... chain) {
        VehicleWriter head = first;
        for (VehicleWriter nextInChain: chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public abstract boolean writeToFile(Vehicle vehicle) throws VehicleWriterException;

    protected boolean checkNext(Vehicle vehicle) throws VehicleWriterException {
        if (next == null) {
            throw new VehicleWriterException(vehicle.getMark());
        }
        return next.writeToFile(vehicle);
    }
}
