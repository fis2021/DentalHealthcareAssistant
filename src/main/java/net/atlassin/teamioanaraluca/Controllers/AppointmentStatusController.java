package net.atlassin.teamioanaraluca.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import net.atlassin.teamioanaraluca.Model.Appointment;
import net.atlassin.teamioanaraluca.Services.AppointmentsService;

import java.io.IOException;
import java.util.Objects;

public class AppointmentStatusController {


    @FXML
    public ListView appointmentStatusList;

    public void setAppointmentStatusList() {

        String aux;

        for (Appointment appointment : AppointmentsService.appointmentsRepository.find()) {
            if (Objects.equals(appointment.getStatus(), "accepted")) {

                aux = appointment.getStatus() + ": Doctor " + appointment.getUsernameDoctor() + " " + appointment.getDate() + " " + appointment.getHour();
                appointmentStatusList.getItems().add(aux);
            }
        }

        for (Appointment appointment : AppointmentsService.appointmentsRepository.find()) {
            if (Objects.equals(appointment.getStatus(), "pending")) {

                aux = appointment.getStatus() + ": Doctor " + appointment.getUsernameDoctor();
                appointmentStatusList.getItems().add(aux);
            }
        }

        for (Appointment appointment : AppointmentsService.appointmentsRepository.find()) {
            if (Objects.equals(appointment.getStatus(), "rejected")) {

                aux = appointment.getStatus() + ": Doctor " + appointment.getUsernameDoctor() + " " + appointment.getRejectionReason();
                appointmentStatusList.getItems().add(aux);
            }
        }

        for (Appointment appointment : AppointmentsService.appointmentsRepository.find()) {
            if (Objects.equals(appointment.getStatus(), "finished")) {

                aux = appointment.getStatus() + ": Doctor " + appointment.getUsernameDoctor() + " " + appointment.getPrescription();
                appointmentStatusList.getItems().add(aux);
            }
        }
    }

    public void handleGoBackToPatientGUI(ActionEvent goBackToPatientGUI) throws IOException {

        Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("PatientGUI.fxml"));
        Stage window = (Stage) ((Node) goBackToPatientGUI.getSource()).getScene().getWindow();
        window.setTitle("CustomerGUI");
        window.setScene(new Scene(root1, 600, 406));
        window.show();
    }
}
