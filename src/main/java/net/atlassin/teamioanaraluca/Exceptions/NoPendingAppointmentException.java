package net.atlassin.teamioanaraluca.Exceptions;

public class NoPendingAppointmentException extends Exception {
    public NoPendingAppointmentException() {
        super(String.format("No pending appointment for that patient!"));
    }
}
