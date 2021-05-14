package net.atlassin.teamioanaraluca.Model;

public class PatientUsernameSchedule {

    private static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        PatientUsernameSchedule.username = username;
    }
}