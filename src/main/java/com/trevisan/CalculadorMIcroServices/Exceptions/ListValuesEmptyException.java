package com.trevisan.CalculadorMIcroServices.Exceptions;

public class ListValuesEmptyException extends RuntimeException {
    public ListValuesEmptyException() {
        super("List values is empty!");
    }
}
