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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.atlassin.teamioanaraluca.Exceptions.CabinetDoesNotExist;
import net.atlassin.teamioanaraluca.Model.DentistServices;
import net.atlassin.teamioanaraluca.Model.User;
import net.atlassin.teamioanaraluca.Model.WhatDoctorAmIAppointedTo;
import net.atlassin.teamioanaraluca.Services.DentistFacilitiesService;
import net.atlassin.teamioanaraluca.Services.UserService;
import org.dizitart.no2.objects.ObjectRepository;

import java.io.IOException;
import java.util.Objects;
public class ViewCabinetsController {

    public static ObjectRepository<User> userRepository;
    public static ObjectRepository<DentistServices> servicesRepository;

    @FXML
    public TextField cabinetName;
    @FXML
    public ListView cabinetsList;
    @FXML
    public Text errorMessage;
    @FXML
    public Button cabinetServices;

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

        WhatDoctorAmIAppointedTo.setDoctorName(cabinetName.getText());


        try {
            DentistFacilitiesService.checkCabinetExists(cabinetName.getText());

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("CabinetsServices.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            CabinetsServicesController controller = loader.getController();
            controller.Set();
            Stage stage = (Stage) (cabinetServices.getScene().getWindow());
            stage.setTitle("Service List");
            stage.setScene(scene);
            stage.show();

        } catch (CabinetDoesNotExist | IOException e1) {
            errorMessage.setText(e1.getMessage());
        }
    }
}
