package net.atlassin.teamioanaraluca.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.atlassin.teamioanaraluca.Exceptions.AppointmentPendingException;
import net.atlassin.teamioanaraluca.Exceptions.DentistServiceExistsException;
import net.atlassin.teamioanaraluca.Exceptions.EmptyTextfieldsException;
import net.atlassin.teamioanaraluca.Model.DentistServices;
import net.atlassin.teamioanaraluca.Model.WhatDoctorAmIAppointedTo;
import net.atlassin.teamioanaraluca.Model.WhoIsLoggedInfo;
import net.atlassin.teamioanaraluca.Services.AppointmentsService;
import net.atlassin.teamioanaraluca.Services.DentistFacilitiesService;
import org.dizitart.no2.objects.ObjectRepository;

import java.io.IOException;
import java.util.Objects;

public class CabinetsServicesController {


    public static ObjectRepository<DentistServices> servicesRepository;
    @FXML
    public Button cabinetsList;
    @FXML
    public ListView servicesList;
    @FXML
    public Text addAppMessage;

    public void Set(){

        for(DentistServices service:DentistFacilitiesService.servicesRepository.find()){
            if(Objects.equals(service.getUsername(), WhatDoctorAmIAppointedTo.getDoctorName())){
                servicesList.getItems().add(service.getDescription());
            }
        }

    }

    public void handleGoBackToCabinetsList(ActionEvent returnToCabinets) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("ViewCabinetsGUI.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        ViewCabinetsController controller = loader.getController();
        controller.Set();
        Stage stage = (Stage) (cabinetsList.getScene().getWindow());
        stage.setTitle("Cabinets List");
        stage.setScene(scene);
        stage.show();

    }

    public void handleMakeAnAppointment(ActionEvent makeAnAppointment) {
        try{
            AppointmentsService.addAppointment(WhoIsLoggedInfo.getLoggedUsername(),WhatDoctorAmIAppointedTo.getDoctorName(),"","","pending","","");
            addAppMessage.setText("Appointment successfully made!");
        }
        catch (AppointmentPendingException e){
            addAppMessage.setText(e.getMessage());
        }
    }

}
