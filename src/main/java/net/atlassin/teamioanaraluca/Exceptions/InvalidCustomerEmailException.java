package net.atlassin.teamioanaraluca.Exceptions;

public class InvalidCustomerEmailException extends Exception{

    public InvalidCustomerEmailException()
    {
        super(String.format("The email address is not valid"));
    }
}
