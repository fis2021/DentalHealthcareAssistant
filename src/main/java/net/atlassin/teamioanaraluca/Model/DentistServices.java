package net.atlassin.teamioanaraluca.Model;


import org.dizitart.no2.RemoveOptions;

public class DentistServices extends RemoveOptions {
    private String username;
    private String description;
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


}
