package org.example.vehicle.utils.dao;

import org.example.vehicle.Vehicle;

public class VehicleTextParserFactory implements ParserDaoFactory<Vehicle> {
    @Override
    public ParserDao<Vehicle> createDao() {
        return new VehicleTextParser();
    }
}
