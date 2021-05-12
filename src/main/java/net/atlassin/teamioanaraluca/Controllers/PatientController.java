package net.atlassin.teamioanaraluca.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PatientController {

    @FXML
    public Button cabintesList;

    @FXML
    public void handleViewCabinetsAction(javafx.event.ActionEvent myServices) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("ViewCabinetsGUI.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        ViewCabinetsController controller = loader.getController();
        controller.Set();
        Stage stage = (Stage) (cabintesList.getScene().getWindow());
        stage.setTitle("Cabinets List");
        stage.setScene(scene);
        stage.show();
    }
}