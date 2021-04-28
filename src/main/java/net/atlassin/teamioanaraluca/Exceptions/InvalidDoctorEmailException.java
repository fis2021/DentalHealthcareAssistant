package net.atlassin.teamioanaraluca.Exceptions;

public class InvalidDoctorEmailException extends Exception{

    public InvalidDoctorEmailException()
    {
        super(String.format("Invalid doctor email. The doctor email must contain the @doctor.com sequence"));
    }
}