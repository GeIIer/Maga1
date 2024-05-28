package org.example.vehicle.utils;

import org.example.exception.VehicleWriterException;
import org.example.vehicle.SynchronizedVehicle;
import org.example.vehicle.Vehicle;
import org.example.vehicle.factory.CarFactory;
import org.example.vehicle.factory.VehicleFactory;
import org.example.vehicle.utils.command.VehicleColumnWriteCommand;
import org.example.vehicle.utils.command.VehicleCommand;
import org.example.vehicle.utils.dao.ParserDao;
import org.example.vehicle.utils.dao.ParserDaoFactory;
import org.example.vehicle.utils.dao.VehicleTextParserFactory;
import org.example.vehicle.utils.writers.VehicleWriter;

import java.io.OutputStreamWriter;

public class VehicleUtil {
    private static VehicleFactory factory = new CarFactory();
    private static VehicleWriter vehicleWriter;
    private static VehicleCommand vehicleCommand = new VehicleColumnWriteCommand();
    private static ParserDaoFactory<Vehicle> daoFactory = new VehicleTextParserFactory();

    public static void setFactory(VehicleFactory factory) {
        VehicleUtil.factory = factory;
    }

    public static void setDaoFactory(ParserDaoFactory<Vehicle> daoFactory) {
        VehicleUtil.daoFactory = daoFactory;
    }

    public static void setVehicleWriter(VehicleWriter vehicleWriter) {
        VehicleUtil.vehicleWriter = vehicleWriter;
    }

    public static void setVehicleCommand(VehicleCommand vehicleCommand) {
        VehicleUtil.vehicleCommand = vehicleCommand;
    }

    public static void printInStream(Vehicle vehicle, OutputStreamWriter outputStream) {
        vehicleCommand.execute(vehicle, outputStream);
    }

    public static void writeToFile(Vehicle vehicle) {
        try {
            vehicleWriter.writeToFile(vehicle);
        } catch (VehicleWriterException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static Vehicle createInstance(String mark, int size){
        return factory.createInstant(mark, size);
    }
    public static ParserDao<Vehicle> createDao() {
        return daoFactory.createDao();
    }

    public static double getAvg(Vehicle vehicle) {
        double result = 0;
        for (double price : vehicle.getAllPrice()) {
            result += price;
        }
        return result/vehicle.lengthModels();
    }

    public static void printVehicle(Vehicle vehicle) {
        System.out.println(vehicle.toString());
    }

    public static SynchronizedVehicle synchronizedVehicle(Vehicle vehicle) {
        return new SynchronizedVehicle(vehicle);
    }
}
