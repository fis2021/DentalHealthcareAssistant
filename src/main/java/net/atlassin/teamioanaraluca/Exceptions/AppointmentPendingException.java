package net.atlassin.teamioanaraluca.Exceptions;

public class AppointmentPendingException extends Exception{
    public AppointmentPendingException() {
        super(String.format("There already is a pending appointment to that doctor!"));
    }
}
