package net.atlassin.teamioanaraluca.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.atlassin.teamioanaraluca.Exceptions.UsernameDoesNotExistException;
import net.atlassin.teamioanaraluca.Exceptions.WrongPasswordException;
import net.atlassin.teamioanaraluca.Exceptions.WrongRoleException;
import net.atlassin.teamioanaraluca.Services.UserService;

import java.io.IOException;

public class DentistController {

    @FXML
    public Button appointmentRequest;

    @FXML
    public void handleMyServicesAction(javafx.event.ActionEvent myServices) throws Exception {
        Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("MyServicesGUI.fxml"));
        Stage window = (Stage) ((Node) myServices.getSource()).getScene().getWindow();
        window.setTitle("My Services");
        window.setScene(new Scene(root1, 600, 406));
        window.show();
    }

    public void handleGoToPendingAppointments(ActionEvent goToPendingAppointments) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("PendingAppointmentsGUI.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        PendingAppointmentController controller = loader.getController();
        controller.setAppointmentsList();
        Stage stage = (Stage) (appointmentRequest.getScene().getWindow());
        stage.setTitle("Pending Appointments List");
        stage.setScene(scene);
        stage.show();
    }
}