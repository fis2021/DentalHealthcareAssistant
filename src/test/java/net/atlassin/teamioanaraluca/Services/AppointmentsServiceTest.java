package net.atlassin.teamioanaraluca.Services;

import net.atlassin.teamioanaraluca.Exceptions.*;
import net.atlassin.teamioanaraluca.Model.Appointment;
import net.atlassin.teamioanaraluca.Model.DentistServices;
import net.atlassin.teamioanaraluca.Model.PatientUsernameSchedule;
import net.atlassin.teamioanaraluca.Model.WhoIsLoggedInfo;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AppointmentsServiceTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before All");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After All");
    }

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".Test-DHA-database";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        AppointmentsService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        AppointmentsService.getDatabase().close();
    }

    @Test
    @DisplayName("Database for appointments is initialized, and there are no appointments")
    void testDatabaseIsInitializedAndNoTripIsPersisted() {
        assertThat(AppointmentsService.getAllAppointments()).isNotNull();
        assertThat(AppointmentsService.getAllAppointments()).isEmpty();
    }

    @Test
    @DisplayName("(Pending)Appointment is successfully added to the database.")
    void testAppointmentIsAddedToDatabase() throws Exception {
        //Add an appointment
        AppointmentsService.addAppointment("user", "doctor", "", "", "pending", "", "");
        assertThat(AppointmentsService.getAllAppointments()).isNotNull();
        assertThat(AppointmentsService.getAllAppointments()).size().isEqualTo(1);
        Appointment appointment = AppointmentsService.getAllAppointments().get(0);
    }


    @Test
    @DisplayName("A patient can only have one pending appointment at a time to a certain doctor")
    void testCheckPendingAppointmentUniqueAtTheSameDoctor() {
        assertThrows(AppointmentPendingException.class, () -> {
            AppointmentsService.addAppointment("user", "doctor", "", "", "pending", "", "");
            AppointmentsService.addAppointment("user", "doctor", "", "", "pending", "", "");
        });
    }


    @Test
    @DisplayName("When accepting an appointment the date should not be empty")
    void testCheckDateIsNotEmptyWhenAcceptingAppointment() {
        assertThrows(EmptyDateFieldException.class, () -> {
            AppointmentsService.addAppointment("user", "doctor", "", "", "pending", "", "");
            AppointmentsService.acceptAppointment("", "19:00");
        });
    }


    @Test
    @DisplayName("When accepting an appointment the time should not be empty")
    void testCheckTimeIsNotEmptyWhenAcceptingAppointment() {
        assertThrows(EmptyTimeFieldException.class, () -> {
            AppointmentsService.addAppointment("user", "doctor", "", "", "pending", "", "");
            AppointmentsService.acceptAppointment("20/06/2021", "");
        });
    }

    @Test
    @DisplayName("The date should be written as DD/MM/YYYY")
    void testCheckDateIsCorrectWhenAcceptingAppointment() {
        assertThrows(WrongDateException.class, () -> {
            AppointmentsService.addAppointment("user", "doctor", "", "", "pending", "", "");
            AppointmentsService.acceptAppointment("20.06.2021", "12:00");
        });
    }

    @Test
    @DisplayName("The date should be written as DD/MM/YYYY")
    void testCheckDateIsCorrectWhenAcceptingAppointment1() {
        assertThrows(WrongDateException.class, () -> {
            AppointmentsService.addAppointment("user", "doctor", "", "", "pending", "", "");
            AppointmentsService.acceptAppointment("06.23.2021", "12:00");
        });
    }

    @Test
    @DisplayName("The date should be written as DD/MM/YYYY")
    void testCheckDateIsCorrectWhenAcceptingAppointment2() {
        assertThrows(WrongDateException.class, () -> {
            AppointmentsService.addAppointment("user", "doctor", "", "", "pending", "", "");
            AppointmentsService.acceptAppointment("2021.06.20", "12:00");
        });
    }

    @Test
    @DisplayName("The time should be written as hh:mm")
    void testCheckTimeIsCorrectWhenAcceptingAppointment() {
        assertThrows(WrongTimeException.class, () -> {
            AppointmentsService.addAppointment("user", "doctor", "", "", "pending", "", "");
            AppointmentsService.acceptAppointment("15/06/2021", "12-00");
        });
    }

    @Test
    @DisplayName("The time should be only between 8 am and 16 pm")
    void testCheckTimeIsCorrectWhenAcceptingAppointment2() {
        assertThrows(WrongTimeException.class, () -> {
            AppointmentsService.addAppointment("user", "doctor", "", "", "pending", "", "");
            AppointmentsService.acceptAppointment("15/06/2021", "18:00");
        });
    }

    @Test
    @DisplayName("The minutes should be between 0 and 60 ")
    void testCheckTimeIsCorrectWhenAcceptingAppointment3() {
        assertThrows(WrongTimeException.class, () -> {
            AppointmentsService.addAppointment("user", "doctor", "", "", "pending", "", "");
            AppointmentsService.acceptAppointment("15/06/2021", "12:61");
        });
    }

    @Test
    @DisplayName("Appointment is successfully accepted")
    void testAcceptingAppointment() throws Exception{
        PatientUsernameSchedule.setUsername("user");
        WhoIsLoggedInfo.setLoggedUsername("doctor");
        //Add a pending appointment service
        AppointmentsService.addAppointment("user", "doctor", "", "", "pending", "", "");
        assertThat(AppointmentsService.getAllAppointments()).isNotNull();
        assertThat(AppointmentsService.getAllAppointments()).size().isEqualTo(1);

        //Accept the appointment
        AppointmentsService.acceptAppointment("12/06/2021","12:00");
        assertThat(AppointmentsService.getAllAppointments()).isNotNull();
        assertThat(AppointmentsService.getAllAppointments()).size().isEqualTo(1);
    }

    @Test
    @DisplayName("Appointment is successfully rejected")
    void testRejectingAppointment() throws Exception{
        PatientUsernameSchedule.setUsername("user");
        WhoIsLoggedInfo.setLoggedUsername("doctor");
        //Add a pending appointment
        AppointmentsService.addAppointment("user", "doctor", "", "", "pending", "", "");
        assertThat(AppointmentsService.getAllAppointments()).isNotNull();
        assertThat(AppointmentsService.getAllAppointments()).size().isEqualTo(1);

        //Reject the appointment
        AppointmentsService.rejectAppointment("Sorry!");
        assertThat(AppointmentsService.getAllAppointments()).isNotNull();
        assertThat(AppointmentsService.getAllAppointments()).size().isEqualTo(1);
    }


    @Test
    @DisplayName("When rejecting an appointment the rejection reason should not be empty")
    void testRejectionReasonIsNotEmptyWhenRejectingAppointment() {
        assertThrows(EmptyRejectionFieldException.class, () -> {
            AppointmentsService.addAppointment("user", "doctor", "", "", "pending", "", "");
            AppointmentsService.rejectAppointment("");
        });
    }


    @Test
    @DisplayName("Appointment is successfully finished")
    void testFinishingAppointment() throws Exception{
        PatientUsernameSchedule.setUsername("user");
        WhoIsLoggedInfo.setLoggedUsername("doctor");

        //Add appointment
        AppointmentsService.addAppointment("user", "doctor", "", "", "pending", "", "");
        assertThat(AppointmentsService.getAllAppointments()).isNotNull();
        assertThat(AppointmentsService.getAllAppointments()).size().isEqualTo(1);

        //Accept appointment
        AppointmentsService.acceptAppointment("12/06/2021","12:00");
        assertThat(AppointmentsService.getAllAppointments()).isNotNull();
        assertThat(AppointmentsService.getAllAppointments()).size().isEqualTo(1);

        //Finish appointment
        AppointmentsService.finishAppointment("user","doctor","12/06/2021");
        assertThat(AppointmentsService.getAllAppointments()).isNotNull();
        assertThat(AppointmentsService.getAllAppointments()).size().isEqualTo(1);
    }

    @Test
    @DisplayName("Appointment is added a prescription")
    void testWritePrescriptionAppointment() throws Exception{
        PatientUsernameSchedule.setUsername("user");
        WhoIsLoggedInfo.setLoggedUsername("doctor");

        //Add appointment
        AppointmentsService.addAppointment("user", "doctor", "", "", "pending", "", "");
        assertThat(AppointmentsService.getAllAppointments()).isNotNull();
        assertThat(AppointmentsService.getAllAppointments()).size().isEqualTo(1);

        //Accept appointment
        AppointmentsService.acceptAppointment("12/06/2021","12:00");
        assertThat(AppointmentsService.getAllAppointments()).isNotNull();
        assertThat(AppointmentsService.getAllAppointments()).size().isEqualTo(1);

        //Finish appointment
        AppointmentsService.finishAppointment("user","doctor","12/06/2021");
        assertThat(AppointmentsService.getAllAppointments()).isNotNull();
        assertThat(AppointmentsService.getAllAppointments()).size().isEqualTo(1);

        //Add prescription
        AppointmentsService.addPrescription("user","doctor","12/06/2021","Okay!");
        assertThat(AppointmentsService.getAllAppointments()).isNotNull();
        assertThat(AppointmentsService.getAllAppointments()).size().isEqualTo(1);

    }


    @Test
    @DisplayName("When writing a prescription the prescription should not be empty")
    void testPrescriptionIsNotEmptyWhenFinishingAppointment() throws Exception {
        assertThrows(EmptyTextfieldsException.class, () -> {
            AppointmentsService.addAppointment("user", "doctor", "12:00", "12/06/2021", "accepted", "", "");
            AppointmentsService.addPrescription("user","doctor","12/06/2021","");
        });
    }





}