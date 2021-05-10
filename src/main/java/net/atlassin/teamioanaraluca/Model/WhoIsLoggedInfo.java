package net.atlassin.teamioanaraluca.Model;

public class WhoIsLoggedInfo {
    public static String loggedUsername;

    public static String getLoggedUsername() {
        return loggedUsername;
    }

    public static void setLoggedUsername(String loggedUsername) {
        WhoIsLoggedInfo.loggedUsername = loggedUsername;
    }
}
