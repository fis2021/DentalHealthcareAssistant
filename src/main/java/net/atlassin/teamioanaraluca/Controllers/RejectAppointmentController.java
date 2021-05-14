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
import net.atlassin.teamioanaraluca.Services.AppointmentsService;
import net.atlassin.teamioanaraluca.Exceptions.EmptyRejectionFieldException;

import java.io.IOException;

public class RejectAppointmentController {

    @FXML
    public Button goBack;
    @FXML
    public TextField rejection;
    @FXML
    public Text rejectionMessage;

    public void handleGoBackToPendingAppointments(ActionEvent goBackToPendingAppointments) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("PendingAppointmentsGUI.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        PendingAppointmentController controller = loader.getController();
        controller.setAppointmentsList();
        Stage stage = (Stage) (goBack.getScene().getWindow());
        stage.setTitle("Pending appointments list");
        stage.setScene(scene);
        stage.show();
    }

    public void handleRejectAppointment() {

        try {
            AppointmentsService.rejectAppointment(rejection.getText());
            rejectionMessage.setText("Appointment rejected successfully!");
        } catch (EmptyRejectionFieldException e1) {
            rejectionMessage.setText(e1.getMessage());
        }

    }

}
