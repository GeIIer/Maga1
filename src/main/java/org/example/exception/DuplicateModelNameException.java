package org.example.exception;

public class DuplicateModelNameException extends Exception {
    public String attrName;
    public DuplicateModelNameException (String message) {
        super("Модель с именем " + message + " уже существует");
        attrName = message;
    }
}
