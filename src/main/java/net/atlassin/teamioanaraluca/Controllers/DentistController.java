package net.atlassin.teamioanaraluca.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.atlassin.teamioanaraluca.Exceptions.UsernameDoesNotExistException;
import net.atlassin.teamioanaraluca.Exceptions.WrongPasswordException;
import net.atlassin.teamioanaraluca.Exceptions.WrongRoleException;
import net.atlassin.teamioanaraluca.Services.UserService;

public class DentistController {

    @FXML
    public void handleMyServicesAction(javafx.event.ActionEvent myServices) throws Exception {
        Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("MyServicesGUI.fxml"));
        Stage window = (Stage) ((Node) myServices.getSource()).getScene().getWindow();
        window.setTitle("My Services");
        window.setScene(new Scene(root1, 600, 406));
        window.show();
    }
}