package net.atlassin.teamioanaraluca.Exceptions;

public class EmptyRejectionFieldException extends Exception{
    public EmptyRejectionFieldException() {
        super(String.format("The rejection field is empty!"));
    }
}
