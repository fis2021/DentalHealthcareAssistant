package net.atlassin.teamioanaraluca.Exceptions;

public class EmptyTimeFieldException extends Exception {

    public EmptyTimeFieldException() {
        super(String.format("Time field is empty"));
    }
}