package net.atlassin.teamioanaraluca.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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
            if (validateRegisterCredentials(passwordField.getText())) {
                UserService.addUser(usernameField.getText(), passwordField.getText(), (String) role.getValue(), nameField.getText(), emailField.getText(), phoneNumberField.getText());
                registrationMessage.setText("Account created successfully!");
            }
        } catch (UsernameAlreadyExistsException e1) {
            registrationMessage.setText(e1.getMessage());
        } catch (InvalidDoctorEmailException e2) {
            registrationMessage.setText(e2.getMessage());
        } catch (InvalidCustomerEmailException e3) {
            registrationMessage.setText(e3.getMessage());
        }
    }

    public boolean validateRegisterCredentials(String password) {
        if (password.equals("")) {
            registrationMessage.setText("Password cannot be empty!");
            return false;
        } else if (password.length()<8){
            registrationMessage.setText("Password should be at least 8 characters!");
            return false;
        } else if (!( password.matches(".*[0-9]{1,}.*") && password.matches(".*[A-Z]{1,}.*"))){
            registrationMessage.setText("Password should contain at least one digit and one upper case character!");
            return false;
        }
        return true;
    }
}
