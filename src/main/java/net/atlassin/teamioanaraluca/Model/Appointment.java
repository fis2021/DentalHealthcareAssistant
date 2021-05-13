package net.atlassin.teamioanaraluca.Model;

public class Appointment {
    String usernamePatient;
    String usernameDoctor;
    String hour;
    String date; //store it as DD.MM.YYYY
    String status; //pending, accepted/rejected or finished
    String rejectionReason;
    String prescription;
    public Appointment(){};
    public Appointment(String usernamePatient, String usernameDoctor, String hour, String date, String status, String rejectionReason, String prescription) {
        this.usernamePatient = usernamePatient;
        this.usernameDoctor = usernameDoctor;
        this.hour = hour;
        this.date = date;
        this.status = status;
        this.rejectionReason = rejectionReason;
        this.prescription = prescription;
    }

    public String getUsernamePatient() {
        return usernamePatient;
    }

    public String getUsernameDoctor() {
        return usernameDoctor;
    }

    public String getHour() {
        return hour;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setUsernamePatient(String usernamePatient) {
        this.usernamePatient = usernamePatient;
    }

    public void setUsernameDoctor(String usernameDoctor) {
        this.usernameDoctor = usernameDoctor;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }
}
