package org.example.vehicle;

import org.example.exception.DuplicateModelNameException;
import org.example.exception.NoSuchModelNameException;
import org.example.vehicle.utils.visitor.Visitor;

import java.io.Serializable;

public interface Vehicle extends Serializable, Cloneable {
    String getMark();

    void setMark(String mark);

    int lengthModels();

    String[] getAllName();

    double getPriceByName(String name) throws NoSuchModelNameException;

    void setNewNameByOldName(String newName, String oldName) throws NoSuchModelNameException, DuplicateModelNameException;

    void setPriceByName(String name, double newPrice) throws NoSuchModelNameException;

    double[] getAllPrice();

    void addNewModel(String modelName, double price) throws DuplicateModelNameException;

    void deleteModel(String modelName) throws NoSuchModelNameException;

    String toString();

    boolean equals(Object obj);

    int hashCode();

    Object clone();

    void accept(Visitor visitor);

}
