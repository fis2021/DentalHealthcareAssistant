package net.atlassin.teamioanaraluca.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.atlassin.teamioanaraluca.Exceptions.EmptyTextfieldsException;
import net.atlassin.teamioanaraluca.Exceptions.NoPendingAppointmentException;
import net.atlassin.teamioanaraluca.Model.Appointment;
import net.atlassin.teamioanaraluca.Model.PatientUsernameSchedule;
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
    @FXML
    public Label pendingAppointmentMessage;

    public void setAppointmentsList() {

        String aux;

        for (Appointment appointment : AppointmentsService.appointmentsRepository.find()) {
            if (Objects.equals(appointment.getStatus(), "pending") && Objects.equals(appointment.getUsernameDoctor(), WhoIsLoggedInfo.getLoggedUsername())) {

                aux = "Patient: " + appointment.getUsernamePatient();
                appointmentsList.getItems().add(aux);
            }
        }
    }

    public void handleAcceptAppointment(ActionEvent acceptAppointment) throws IOException {
        try{
        checkEmptyTextFieldsPendingAppointment(appointmentName.getText());
        checkPatientHasPendingAppointment(appointmentName.getText());
        PatientUsernameSchedule.setUsername(appointmentName.getText());
        Parent root2 = FXMLLoader.load(getClass().getClassLoader().getResource("AcceptAppointment.fxml"));
        Stage window = (Stage) ((Node) acceptAppointment.getSource()).getScene().getWindow();
        window.setTitle("DentistGUI");
        window.setScene(new Scene(root2, 600, 460));
        window.show();
        }
        catch(EmptyTextfieldsException e){
            pendingAppointmentMessage.setText(e.getMessage());
        }
        catch(NoPendingAppointmentException e){
            pendingAppointmentMessage.setText(e.getMessage());
        }

    }

    public void handleRejectAppointment(ActionEvent rejectAppointment) throws IOException {
        try{
        checkEmptyTextFieldsPendingAppointment(appointmentName.getText());
        checkPatientHasPendingAppointment(appointmentName.getText());
        PatientUsernameSchedule.setUsername(appointmentName.getText());
        Parent root2 = FXMLLoader.load(getClass().getClassLoader().getResource("RejectAppointment.fxml"));
        Stage window = (Stage) ((Node) rejectAppointment.getSource()).getScene().getWindow();
        window.setTitle("RejectAppointment");
        window.setScene(new Scene(root2, 600, 460));
        window.show();
        }catch (EmptyTextfieldsException e){
            pendingAppointmentMessage.setText(e.getMessage());
        }catch(NoPendingAppointmentException e){
            pendingAppointmentMessage.setText(e.getMessage());
        }
    }

    private static void checkEmptyTextFieldsPendingAppointment(String description) throws EmptyTextfieldsException {
        if (description.equals(""))
            throw new EmptyTextfieldsException();
    }

    public static void checkPatientHasPendingAppointment(String patientUsername) throws NoPendingAppointmentException {
        int exists = 0;
        for (Appointment appointment : AppointmentsService.appointmentsRepository.find()) {
            if (Objects.equals(appointment.getStatus(), "pending") && Objects.equals(appointment.getUsernameDoctor(), WhoIsLoggedInfo.getLoggedUsername())) {
                if (patientUsername.equals(appointment.getUsernamePatient()))
                    exists = 1;
            }
        }

        if (exists == 0)
            throw new NoPendingAppointmentException();
    }

    public void handleGoBackToDentistGUI(ActionEvent goBackToDentistGUI) throws IOException {
        Parent root2 = FXMLLoader.load(getClass().getClassLoader().getResource("DentistGUI.fxml"));
        Stage window = (Stage) ((Node) goBackToDentistGUI.getSource()).getScene().getWindow();
        window.setTitle("DentistGUI");
        window.setScene(new Scene(root2, 600, 460));
        window.show();
    }
}