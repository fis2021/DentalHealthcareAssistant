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
import net.atlassin.teamioanaraluca.Exceptions.InvalidCredentialsException;
import net.atlassin.teamioanaraluca.Exceptions.InvalidCustomerEmailException;
import net.atlassin.teamioanaraluca.Exceptions.InvalidDoctorEmailException;
import net.atlassin.teamioanaraluca.Exceptions.UsernameAlreadyExistsException;
import net.atlassin.teamioanaraluca.Services.UserService;

public class RegistrationController {

    @FXML
    private Text registrationMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private ChoiceBox role;

    @FXML
    public void initialize() {
        role.getItems().addAll("Dentist", "Customer");
    }

    @FXML
    public void handleRegisterAction() {
        try {
                UserService.addUser(usernameField.getText(), passwordField.getText(), (String) role.getValue(), nameField.getText(), emailField.getText(), phoneNumberField.getText());
                registrationMessage.setText("Account created successfully!");

        } catch (UsernameAlreadyExistsException e1) {
            registrationMessage.setText(e1.getMessage());
        } catch (InvalidDoctorEmailException e2) {
            registrationMessage.setText(e2.getMessage());
        } catch (InvalidCustomerEmailException e3) {
            registrationMessage.setText(e3.getMessage());
        }catch (InvalidCredentialsException e4){
            registrationMessage.setText(e4.getMessage());
        }
    }

    public void goToLoginScene(javafx.event.ActionEvent login) throws Exception{
        Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
        Stage window = (Stage)((Node)login.getSource()).getScene().getWindow();
        window.setTitle("Login");
        window.setScene(new Scene(root1,600,460));
        window.show();

    }


}
