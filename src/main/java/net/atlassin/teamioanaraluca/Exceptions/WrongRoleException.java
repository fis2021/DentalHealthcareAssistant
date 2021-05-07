package net.atlassin.teamioanaraluca.Exceptions;

public class WrongRoleException extends Exception{
    public WrongRoleException() {
        super(String.format("The selected role is incorrect! "));
    }
}
