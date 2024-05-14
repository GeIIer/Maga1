package org.example.vehicle.utils.dao;

import java.util.List;

public interface ParserDao<T> {
    List<T> readAll(String filename);
    boolean isValidFile(String fileType);
}
