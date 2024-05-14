package org.example.exception;

public class NoSuchModelNameException extends Exception {
    public String attrName;
    public NoSuchModelNameException (String message) {
        super("Модель с именем " + message + " не найдена");
        attrName = message;
    }
}
