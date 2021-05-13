package net.atlassin.teamioanaraluca.Services;

import net.atlassin.teamioanaraluca.Exceptions.AppointmentPendingException;
import net.atlassin.teamioanaraluca.Exceptions.DentistServiceExistsException;
import net.atlassin.teamioanaraluca.Exceptions.EmptyTextfieldsException;
import net.atlassin.teamioanaraluca.Model.Appointment;
import net.atlassin.teamioanaraluca.Model.DentistServices;
import net.atlassin.teamioanaraluca.Model.WhoIsLoggedInfo;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

public class AppointmentsService {
    public static ObjectRepository<Appointment> appointmentsRepository;

    public static ObjectRepository<Appointment> getAppointmentsRepository() {
        return appointmentsRepository;
    }

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(FileSystemService.getPathToFile("dental-healthcare-appointments.db").toFile())
                .openOrCreate("test", "test");

        appointmentsRepository = database.getRepository(Appointment.class);
    }

    public static void addAppointment(String usernamePatient, String usernameDoctor, String hour, String date, String status, String rejectionReason, String prescription) throws AppointmentPendingException{
        checkAppointmentPendingExists(usernamePatient,usernameDoctor);
        appointmentsRepository.insert(new Appointment(usernamePatient, usernameDoctor, hour, date, status, rejectionReason, prescription));
    }

    private static void checkAppointmentPendingExists(String usernamePatient,String usernameDoctor) throws AppointmentPendingException {
        //This checks if the patient already has a pending appointment to that doctor
        int ok = 0;
        for (Appointment appointment : appointmentsRepository.find()) {
            if (appointment.getStatus().equals("pending"))
                if (appointment.getUsernamePatient().equals(usernamePatient)&&appointment.getUsernameDoctor().equals(usernameDoctor))
                    ok = 1;
        }

        if (ok == 1) throw new AppointmentPendingException();
    }



}
