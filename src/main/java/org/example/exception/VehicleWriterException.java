package org.example.exception;

public class VehicleWriterException extends Exception {
    public String attrName;
    public VehicleWriterException (String message) {
        super("Обработчик не найден " + message);
        attrName = message;
    }
}
