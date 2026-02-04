package com.trevisan.CalculadorMIcroServices.Exceptions;

public class InvalidInputFormatException extends RuntimeException {
    public InvalidInputFormatException() {
        super("Invalid format value!");
    }
}
