package net.atlassin.teamioanaraluca.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.atlassin.teamioanaraluca.Exceptions.EmptyTextfieldsException;
import net.atlassin.teamioanaraluca.Model.Appointment;
import net.atlassin.teamioanaraluca.Model.DentistServices;
import net.atlassin.teamioanaraluca.Model.User;
import net.atlassin.teamioanaraluca.Model.WhoIsLoggedInfo;
import net.atlassin.teamioanaraluca.Services.AppointmentsService;
import net.atlassin.teamioanaraluca.Services.DentistFacilitiesService;
import net.atlassin.teamioanaraluca.Services.UserService;
import org.dizitart.no2.objects.ObjectRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TodaysAppointmentsController {
    private static ObjectRepository<Appointment> appointmentsRepository = AppointmentsService.getAppointmentsRepository();
    private String patient;
    @FXML
    public ListView<String> todaysAppointmentListView = new ListView<String>();
    @FXML
    public Text appointmentInfo;
    @FXML
    public Text finishMessage;
    @FXML
    public Button finishButton;
    @FXML
    public Text prescriptionMessage;
    @FXML
    private TextField prescriptionDescription;
    @FXML
    public Button sendPrescription;

    public void initialize() throws IOException {
        prescriptionDescription.setVisible(false);
        prescriptionMessage.setVisible(false);
        sendPrescription.setVisible(false);
        finishButton.setVisible(true);
        updateWithTodaysAppointmentsListView();
        todaysAppointmentListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (todaysAppointmentListView.getSelectionModel().getSelectedItem()!=null){
                String usernamePatientClicked = todaysAppointmentListView.getSelectionModel().getSelectedItem().toString();
                appointmentInfo.setText(getInfoPatient(usernamePatientClicked));
                }
            }
        });
    }

    public String getInfoPatient(String usernamePatient){
        String information = "";
        //go through user repository
        for(User user:UserService.userRepository.find()){
            if (user.getRole().equals("Customer") && user.getUsername().equals(usernamePatient)){
                information = information + "Name: "+user.getName();
                information = information + "\nE-mail: "+user.getEmail();
                information = information + "\nPhone: "+user.getPhoneNumber();
            }
        }



        //go through appointment repository
        String currentDate = returnCurrentDate();
        for (Appointment appointment : appointmentsRepository.find()) {
            if (appointment.getUsernamePatient().equals(usernamePatient) && appointment.getUsernameDoctor().equals(WhoIsLoggedInfo.getLoggedUsername()))
                if (appointment.getStatus().equals("accepted"))
                    if (appointment.getDate().equals(currentDate)) {
                        information = information + "\nDate: "+appointment.getDate();
                       information = information + "\nHour: "+appointment.getHour();
                    }
        }

        return information;
    }

    public void handleFinishAppointment() throws IOException{
        if (todaysAppointmentListView.getSelectionModel().getSelectedItem()==null){
            finishMessage.setText("Please select a user!");
        }
        else {
        String currentDate = returnCurrentDate();
        this.patient = todaysAppointmentListView.getSelectionModel().getSelectedItem().toString();
        AppointmentsService.finishAppointment(patient,WhoIsLoggedInfo.getLoggedUsername(),currentDate);
        finishButton.setVisible(false);
        prescriptionDescription.setVisible(true);
        prescriptionMessage.setVisible(true);
        sendPrescription.setVisible(true);
        }
        //updateWithTodaysAppointmentsListView();
    }

    public void handleSendPrescription() throws IOException{
        try{
            String currentDate = returnCurrentDate();
            AppointmentsService.addPrescription(patient,WhoIsLoggedInfo.getLoggedUsername(),currentDate,prescriptionDescription.getText());
            prescriptionDescription.setVisible(false);
            prescriptionMessage.setVisible(false);
            sendPrescription.setVisible(false);
            finishButton.setVisible(true);
            todaysAppointmentListView.getItems().clear();
            updateWithTodaysAppointmentsListView();
        }
        catch (EmptyTextfieldsException e){
            prescriptionMessage.setText(e.getMessage());
        }
    }

    public void updateWithTodaysAppointmentsListView(){
        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfMonth();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        LocalDate ld = LocalDate.of( currentYear , currentMonth, currentDay );
        DateTimeFormatter f = DateTimeFormatter.ofPattern( "dd/MM/uuuu" );
        String dateFormat = ld.format( f );

        ObservableList<String> items = FXCollections.observableArrayList();
        for (Appointment appointment : appointmentsRepository.find()) {
            if (appointment.getUsernameDoctor().equals(WhoIsLoggedInfo.getLoggedUsername()))
                if (appointment.getStatus().equals("accepted"))
                    if (appointment.getDate().equals(dateFormat)) {
                    items.add(appointment.getUsernamePatient());
                    todaysAppointmentListView.setItems(items);
            }
        }
    }

    public String returnCurrentDate(){
        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfMonth();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        LocalDate ld = LocalDate.of( currentYear , currentMonth, currentDay );
        DateTimeFormatter f = DateTimeFormatter.ofPattern( "dd/MM/uuuu" );
        String dateFormat = ld.format( f );
        return dateFormat;
    }

    public void goBackToDentistGUI(javafx.event.ActionEvent goToDentist) throws IOException {
        Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("DentistGUI.fxml"));
        Stage window = (Stage) ((Node) goToDentist.getSource()).getScene().getWindow();
        window.setTitle("CustomerGUI");
        window.setScene(new Scene(root1, 600, 406));
        window.show();
    }
}
