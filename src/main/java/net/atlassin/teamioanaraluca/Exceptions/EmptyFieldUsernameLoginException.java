package net.atlassin.teamioanaraluca.Exceptions;

public class EmptyFieldUsernameLoginException extends Exception{
    public EmptyFieldUsernameLoginException() {
        super(String.format("The username field can not be empty!"));
    }
}
