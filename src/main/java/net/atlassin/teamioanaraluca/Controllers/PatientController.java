package net.atlassin.teamioanaraluca.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PatientController {
    @FXML
    public void handleViewCabinetsAction(javafx.event.ActionEvent myServices) throws Exception {
        Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("ViewCabinetsGUI.fxml"));
        Stage window = (Stage) ((Node) myServices.getSource()).getScene().getWindow();
        window.setTitle("View Cabinets List");
        window.setScene(new Scene(root1, 600, 406));
        window.show();
    }
}
