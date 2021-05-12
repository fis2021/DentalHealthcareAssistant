package net.atlassin.teamioanaraluca.Model;

public class WhatDoctorAmIAppointedTo {

    private static String doctorName;

    public static String getDoctorName() {
        return doctorName;
    }

    public static void setDoctorName(String doctorName) {
        WhatDoctorAmIAppointedTo.doctorName = doctorName;
    }
}