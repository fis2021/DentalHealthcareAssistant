package net.atlassin.teamioanaraluca.Model;

public class DentistServices {
    private String username;
    private String description;
    private static String whoIsLogged;

    public DentistServices() {
    }

    public DentistServices(String username, String description) {
        this.username = username;

        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public  String getUsername() {
        return username;
    }



    public void setUsername(String username) {
        this.username = username;
    }



    public void setDescription(String description) {
        this.description = description;
    }

    public static void setWhoIsLogged(String whoIsLogged) {
        DentistServices.whoIsLogged = whoIsLogged;
    }

    public static String getWhoIsLogged() {
        return whoIsLogged;
    }
}
