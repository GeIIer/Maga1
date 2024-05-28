package org.example.vehicle.utils.dao;

import org.example.vehicle.Vehicle;

public class VehicleByteParserFactory implements ParserDaoFactory<Vehicle> {
    @Override
    public ParserDao<Vehicle> createDao() {
        return new VehicleByteParser();
    }
}
