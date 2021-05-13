package net.atlassin.teamioanaraluca.Exceptions;

public class CabinetDoesNotExist extends Exception {

    public CabinetDoesNotExist() {
        super(String.format("The selected cabinet does not exist in the database!"));
    }
}
