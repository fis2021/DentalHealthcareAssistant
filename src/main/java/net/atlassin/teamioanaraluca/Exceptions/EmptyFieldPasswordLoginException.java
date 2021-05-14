package net.atlassin.teamioanaraluca.Exceptions;

public class EmptyFieldPasswordLoginException extends Exception{
    public EmptyFieldPasswordLoginException() {
        super(String.format("The password field can not be empty!"));
    }
}
