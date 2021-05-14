package net.atlassin.teamioanaraluca.Exceptions;

public class EmptyDateFieldException extends Exception {

    public EmptyDateFieldException() {
        super(String.format("The date field is empty!"));
    }
}
