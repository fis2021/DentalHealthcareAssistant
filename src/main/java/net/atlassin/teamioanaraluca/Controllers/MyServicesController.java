package net.atlassin.teamioanaraluca.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.atlassin.teamioanaraluca.Exceptions.DentistServiceExistsException;
import net.atlassin.teamioanaraluca.Exceptions.EmptyTextfieldsException;
import net.atlassin.teamioanaraluca.Model.DentistServices;
import net.atlassin.teamioanaraluca.Model.WhoIsLoggedInfo;
import net.atlassin.teamioanaraluca.Services.DentistFacilitiesService;
import org.dizitart.no2.objects.ObjectRepository;

import java.io.IOException;

public class MyServicesController {
    private static ObjectRepository<DentistServices> servicesRepository = DentistFacilitiesService.getServicesRepository();

    @FXML
    private TextField serviceDescription;

    @FXML
    public ListView<String> serviceListView = new ListView<String>();
    @FXML
    public Text addMessage;

    @FXML
    public void initialize() throws IOException {
        updateListView();
    }

    public void handleAddServiceAction() throws Exception{
        try{
            DentistFacilitiesService.addService(WhoIsLoggedInfo.getLoggedUsername(),serviceDescription.getText());
            updateListView();
            addMessage.setText("Service added successfully !");
        }
        catch (EmptyTextfieldsException e){
            addMessage.setText(e.getMessage());
        }
        catch(DentistServiceExistsException e){
            addMessage.setText(e.getMessage());
        }
    }
    public void goToDentistGUI(javafx.event.ActionEvent dentistGUI) throws Exception{
        Parent root4 = FXMLLoader.load(getClass().getClassLoader().getResource("DentistGUI.fxml"));
        Stage window = (Stage)((Node)dentistGUI.getSource()).getScene().getWindow();
        window.setTitle("DentistGUI");
        window.setScene(new Scene(root4,600,460));
        window.show();

    }
    public void handleDeleteServiceAction() throws Exception{
        DentistFacilitiesService.deleteService(WhoIsLoggedInfo.getLoggedUsername(),serviceListView.getSelectionModel().getSelectedItem().toString());
        updateListView();
    }

    public void updateListView(){
        ObservableList<String> items = FXCollections.observableArrayList();
        for (DentistServices service : servicesRepository.find()) {
            if (WhoIsLoggedInfo.getLoggedUsername().equals(service.getUsername())) { //////AICI!!!!??
                items.add(service.getDescription());
                serviceListView.setItems(items);
            }
        }
    }
}
