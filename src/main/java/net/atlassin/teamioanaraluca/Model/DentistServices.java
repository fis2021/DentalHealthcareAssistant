package net.atlassin.teamioanaraluca.Model;

public class DentistServices {
    private static String username;
    private static String name;
    private String description;

    public DentistServices(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static String getUsername() {
        return username;
    }

    public static String getName() {
        return name;
    }

    public static void setUsername(String username) {
        DentistServices.username = username;
    }

    public static void setName(String name) {
        DentistServices.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
