package net.atlassin.teamioanaraluca.Exceptions;

public class WrongTimeException extends Exception {

    public WrongTimeException() {
        super(String.format("The time format is incorrect!"));
    }
}