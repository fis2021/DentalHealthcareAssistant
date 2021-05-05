package net.atlassin.teamioanaraluca.Exceptions;

public class WrongPasswordException extends Exception{
    public WrongPasswordException() {
        super(String.format("Wrong password ! "));
    }
}
