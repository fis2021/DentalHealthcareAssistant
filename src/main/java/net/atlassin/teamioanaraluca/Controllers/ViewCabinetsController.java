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
import net.atlassin.teamioanaraluca.Model.DentistServices;
import net.atlassin.teamioanaraluca.Model.User;
import net.atlassin.teamioanaraluca.Services.DentistFacilitiesService;
import net.atlassin.teamioanaraluca.Services.UserService;
import org.dizitart.no2.objects.ObjectRepository;

import java.util.Objects;

public class ViewCabinetsController {

    public static ObjectRepository<User> userRepository;

    @FXML
    public TextField cabinetName;
    @FXML
    public Button accessCabinetServices;
    @FXML
    public ListView cabinetsList;

    public void Set() {
        String aux;
        for (User user : UserService.userRepository.find()) {
            if (Objects.equals(user.getRole(), "Dentist")) {
                aux = "Doctor's " + user.getUsername() + " cabinet";
                cabinetsList.getItems().add(aux);
            }
        }
    }

    public void handleGoBackToPatientGUI(javafx.event.ActionEvent myServices) throws Exception {
        Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("PatientGUI.fxml"));
        Stage window = (Stage) ((Node) myServices.getSource()).getScene().getWindow();
        window.setTitle("CustomerGUI");
        window.setScene(new Scene(root1, 600, 406));
        window.show();
    }

    public void handleAccessCabinetServices(ActionEvent accessCabinetServices) {
    }
}