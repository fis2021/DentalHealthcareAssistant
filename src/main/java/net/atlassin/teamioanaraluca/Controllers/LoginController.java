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
import net.atlassin.teamioanaraluca.Model.DentistServices;
import net.atlassin.teamioanaraluca.Model.WhoIsLoggedInfo;
import net.atlassin.teamioanaraluca.Services.UserService;

public class LoginController {
    @FXML
    private Text loginUsernameMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private ChoiceBox role;

    private String userRole;

    @FXML
    public void initialize() {
        role.getItems().addAll("Dentist", "Customer");
    }

    @FXML
    public void handleLoginAction(javafx.event.ActionEvent login) throws Exception {
        try {
            UserService.checkUserCredentials(usernameField.getText(), passwordField.getText(), (String) role.getValue());
            loginUsernameMessage.setText("Login successfully!");
            userRole = (String) role.getValue();
            if (userRole.equals("Dentist")) {
                WhoIsLoggedInfo.setLoggedUsername(usernameField.getText()); //Added this to see who is logged (which username)
                Parent root2 = FXMLLoader.load(getClass().getClassLoader().getResource("DentistGUI.fxml"));
                Stage window = (Stage) ((Node) login.getSource()).getScene().getWindow();
                ;
                window.setTitle("DentistGUI");
                window.setScene(new Scene(root2, 600, 460));
                window.show();

            } else if (userRole.equals("Customer")) {
                WhoIsLoggedInfo.setLoggedUsername(usernameField.getText()); //Added this to see who is logged (which username)
                Parent root3 = FXMLLoader.load(getClass().getClassLoader().getResource("PatientGUI.fxml"));
                Stage window = (Stage) ((Node) login.getSource()).getScene().getWindow();
                window.setTitle("CustomerGUI");
                window.setScene(new Scene(root3, 600, 460));
                window.show();
            }
        } catch (UsernameDoesNotExistException e) {
            loginUsernameMessage.setText(e.getMessage());
        } catch (WrongRoleException e) {
            loginUsernameMessage.setText((e.getMessage()));
        } catch (WrongPasswordException e) {
            loginUsernameMessage.setText(e.getMessage());
        }
    }

    public void goBackToRegisterScene(javafx.event.ActionEvent login) throws Exception {
        Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        Stage window = (Stage) ((Node) login.getSource()).getScene().getWindow();
        ;
        window.setTitle("Registration");
        window.setScene(new Scene(root1, 600, 460));
        window.show();
    }
}
