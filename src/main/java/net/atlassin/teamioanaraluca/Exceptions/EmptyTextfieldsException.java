package net.atlassin.teamioanaraluca.Exceptions;

public class EmptyTextfieldsException extends Exception{
    public EmptyTextfieldsException() {
        super(String.format("Cannot be empty!"));
    }
}
