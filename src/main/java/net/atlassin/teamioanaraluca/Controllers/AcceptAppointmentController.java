package net.atlassin.teamioanaraluca.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.atlassin.teamioanaraluca.Exceptions.EmptyDateFieldException;
import net.atlassin.teamioanaraluca.Exceptions.EmptyTimeFieldException;
import net.atlassin.teamioanaraluca.Exceptions.WrongDateException;
import net.atlassin.teamioanaraluca.Exceptions.WrongTimeException;
import net.atlassin.teamioanaraluca.Services.AppointmentsService;

import java.io.IOException;

public class AcceptAppointmentController {

    @FXML
    public Button goBack;
    @FXML
    public TextField date;
    @FXML
    public TextField time;
    @FXML
    public Text appointmentMessage;

    public void handleGoBackToPendingAppointments(ActionEvent goBackToPendingAppointments) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("PendingAppointmentsGUI.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        PendingAppointmentController controller = loader.getController();
        controller.setAppointmentsList();
        Stage stage = (Stage) (goBack.getScene().getWindow());
        stage.setTitle("Cabinets List");
        stage.setScene(scene);
        stage.show();
    }

    public void handleScheduleAppointment() {

        try {
            AppointmentsService.acceptAppointment(date.getText(), time.getText());
            appointmentMessage.setText("Appointment scheduled successfully");
        } catch (EmptyDateFieldException e1) {
            appointmentMessage.setText(e1.getMessage());
        } catch (EmptyTimeFieldException e2) {
            appointmentMessage.setText(e2.getMessage());
        } catch (WrongDateException e3) {
            appointmentMessage.setText(e3.getMessage());
        } catch (WrongTimeException e4) {
            appointmentMessage.setText(e4.getMessage());
        }

    }
}