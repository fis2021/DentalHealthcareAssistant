package net.atlassin.teamioanaraluca.Exceptions;

public class WrongDateException extends Exception {

    public WrongDateException() {
        super(String.format("The date format is incorrect!"));
    }
}