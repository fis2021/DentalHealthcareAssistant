package net.atlassin.teamioanaraluca.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TodaysAppointmentsController {

    public void goBackToDentistGUI(javafx.event.ActionEvent goToDentist) throws IOException {
        Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("DentistGUI.fxml"));
        Stage window = (Stage) ((Node) goToDentist.getSource()).getScene().getWindow();
        window.setTitle("CustomerGUI");
        window.setScene(new Scene(root1, 600, 406));
        window.show();
    }
}
