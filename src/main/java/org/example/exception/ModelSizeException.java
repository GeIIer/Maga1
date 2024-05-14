package org.example.exception;

public class ModelSizeException extends RuntimeException {
    public ModelSizeException (String message) {
        super(message);
    }
}
