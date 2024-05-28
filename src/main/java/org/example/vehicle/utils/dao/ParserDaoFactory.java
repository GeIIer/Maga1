package org.example.vehicle.utils.dao;

public interface ParserDaoFactory<T> {
    ParserDao<T> createDao();
}
