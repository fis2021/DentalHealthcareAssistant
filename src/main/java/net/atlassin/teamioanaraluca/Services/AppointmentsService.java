package net.atlassin.teamioanaraluca.Services;

import net.atlassin.teamioanaraluca.Exceptions.*;
import net.atlassin.teamioanaraluca.Model.Appointment;
import net.atlassin.teamioanaraluca.Model.DentistServices;
import net.atlassin.teamioanaraluca.Model.PatientUsernameSchedule;
import net.atlassin.teamioanaraluca.Model.WhoIsLoggedInfo;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

import static org.dizitart.no2.objects.filters.ObjectFilters.and;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

import java.util.Objects;

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

    public static void addAppointment(String usernamePatient, String usernameDoctor, String hour, String date, String status, String rejectionReason, String prescription) throws AppointmentPendingException {
        checkAppointmentPendingExists(usernamePatient, usernameDoctor);
        appointmentsRepository.insert(new Appointment(usernamePatient, usernameDoctor, hour, date, status, rejectionReason, prescription));
    }

    private static void checkAppointmentPendingExists(String usernamePatient, String usernameDoctor) throws AppointmentPendingException {
        //This checks if the patient already has a pending appointment to that doctor
        int ok = 0;
        for (Appointment appointment : appointmentsRepository.find()) {
            if (appointment.getStatus().equals("pending"))
                if (appointment.getUsernamePatient().equals(usernamePatient) && appointment.getUsernameDoctor().equals(usernameDoctor))
                    ok = 1;
        }

        if (ok == 1) throw new AppointmentPendingException();
    }

    private static void checkDateAndTime(String date, String time) throws WrongDateException, WrongTimeException {

        if (date.charAt(2) != '/' || date.charAt(5) != '/') {
            throw new WrongDateException();
        } else {
            String aux[] = date.split("/");

            if (Integer.parseInt(aux[0]) < 1 || Integer.parseInt(aux[0]) > 31) {
                throw new WrongDateException();
            } else {
                if (Integer.parseInt(aux[1]) < 1 || Integer.parseInt(aux[1]) > 12) {
                    throw new WrongDateException();
                } else {
                    if (Integer.parseInt(aux[2]) < 2021) {
                        throw new WrongDateException();
                    }
                }
            }
        }
        if (time.charAt(2) != ':') {
            throw new WrongTimeException();
        } else {
            String aux[] = time.split(":");

            if (Integer.parseInt(aux[0]) < 8 || Integer.parseInt(aux[0]) > 16) {
                throw new WrongTimeException();
            } else {
                if (Integer.parseInt(aux[1]) < 0 || Integer.parseInt(aux[1]) > 60) {
                    throw new WrongTimeException();
                }
            }
        }
    }


    private static void checkEmptyFieldsAcceptAppointment(String date, String time) throws EmptyDateFieldException, EmptyTimeFieldException {

        if (date == "") {
            throw new EmptyDateFieldException();
        } else {
            if (time == "") {
                throw new EmptyTimeFieldException();
            }
        }
    }


    public static void acceptAppointment(String date, String hour) throws WrongDateException, WrongTimeException, EmptyDateFieldException, EmptyTimeFieldException {

        checkEmptyFieldsAcceptAppointment(date, hour);
        checkDateAndTime(date, hour);

        Appointment newAppointment = new Appointment();
        for (Appointment appointment : appointmentsRepository.find()) {
            if (Objects.equals(appointment.getUsernamePatient(), PatientUsernameSchedule.getUsername()) && Objects.equals(appointment.getUsernameDoctor(), WhoIsLoggedInfo.getLoggedUsername())) {
                newAppointment = appointment;
            }
        }

        newAppointment.setStatus("accepted");
        newAppointment.setDate(date);
        newAppointment.setHour(hour);
        appointmentsRepository.update(and(eq("usernamePatient", PatientUsernameSchedule.getUsername()), eq("usernameDoctor", WhoIsLoggedInfo.getLoggedUsername()), eq("status", "pending")), newAppointment);

    }
    private static void checkEmptyFieldRejectAppointment(String rejection) throws EmptyRejectionFieldException{

        if (rejection == "") {
            throw new EmptyRejectionFieldException();
        }
    }
    public static void rejectAppointment(String rejection) throws EmptyRejectionFieldException {

        checkEmptyFieldRejectAppointment(rejection);
        Appointment newAppointment = new Appointment();
        for (Appointment appointment : appointmentsRepository.find()) {
            if (Objects.equals(appointment.getUsernamePatient(), PatientUsernameSchedule.getUsername()) && Objects.equals(appointment.getUsernameDoctor(), WhoIsLoggedInfo.getLoggedUsername())) {
                newAppointment = appointment;
            }
        }

        newAppointment.setStatus("rejected");
        newAppointment.setRejectionReason(rejection);

        appointmentsRepository.update(and(eq("usernamePatient", PatientUsernameSchedule.getUsername()), eq("usernameDoctor", WhoIsLoggedInfo.getLoggedUsername()), eq("status", "pending")), newAppointment);

    }

    public static void finishAppointment(String userPatient, String userDoctor, String date){
        //checkEmptyUser
        Appointment newAppointment = new Appointment();
        for (Appointment appointment : appointmentsRepository.find()) {
            if (appointment.getUsernamePatient().equals(userPatient)&&appointment.getUsernameDoctor().equals(userDoctor)&&appointment.getDate().equals(date)) {
                newAppointment = appointment;
            }
        }

        newAppointment.setStatus("finished");

        appointmentsRepository.update(and(eq("usernamePatient", userPatient), eq("usernameDoctor", userDoctor), eq("date",date)), newAppointment);

    }


}