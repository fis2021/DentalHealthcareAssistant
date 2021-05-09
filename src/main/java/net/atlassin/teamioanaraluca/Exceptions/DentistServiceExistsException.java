package net.atlassin.teamioanaraluca.Exceptions;

public class DentistServiceExistsException extends Exception{
    public DentistServiceExistsException() {
        super(String.format("Already exists!"));
    }
}
