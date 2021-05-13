package net.atlassin.teamioanaraluca.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.atlassin.teamioanaraluca.Model.Appointment;
import net.atlassin.teamioanaraluca.Model.WhoIsLoggedInfo;
import net.atlassin.teamioanaraluca.Services.AppointmentsService;

import java.io.IOException;
import java.util.Objects;

public class PendingAppointmentController {
    @FXML
    public ListView appointmentsList;
    @FXML
    public TextField appointmentName;
    @FXML
    public Button acceptAppointment;
    @FXML
    public Button rejectAppointment;


    public void setAppointmentsList() {

        String aux;

        for (Appointment appointment : AppointmentsService.appointmentsRepository.find()) {
            if (Objects.equals(appointment.getStatus(), "pending") && Objects.equals(appointment.getUsernameDoctor(), WhoIsLoggedInfo.getLoggedUsername())) {

                aux = "Patient: " + appointment.getUsernamePatient();
                appointmentsList.getItems().add(aux);
            }
        }
    }

    public void handleAcceptAppointment(ActionEvent acceptAppointment) {

    }

    public void handleRejectAppointment(ActionEvent rejectAppointment) {

    }

    public void handleGoBackToDentistGUI(ActionEvent goBackToDentistGUI) throws IOException {
        Parent root2 = FXMLLoader.load(getClass().getClassLoader().getResource("DentistGUI.fxml"));
        Stage window = (Stage) ((Node) goBackToDentistGUI.getSource()).getScene().getWindow();
        window.setTitle("DentistGUI");
        window.setScene(new Scene(root2, 600, 460));
        window.show();
    }
}