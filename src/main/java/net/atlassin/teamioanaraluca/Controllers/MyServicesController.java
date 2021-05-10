package net.atlassin.teamioanaraluca.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.atlassin.teamioanaraluca.Exceptions.DentistServiceExistsException;
import net.atlassin.teamioanaraluca.Exceptions.EmptyTextfieldsException;
import net.atlassin.teamioanaraluca.Model.DentistServices;
import net.atlassin.teamioanaraluca.Model.WhoIsLoggedInfo;
import net.atlassin.teamioanaraluca.Services.DentistFacilitiesService;
import org.dizitart.no2.objects.ObjectRepository;

import java.io.IOException;
import java.util.Optional;

public class MyServicesController {
    private static ObjectRepository<DentistServices> servicesRepository = DentistFacilitiesService.getServicesRepository();

    @FXML
    private TextField serviceDescription;

    @FXML
    public ListView<String> serviceListView = new ListView<String>();
    @FXML
    public Text addMessage;
    @FXML
    public Text editMessage;
    @FXML
    private TextField serviceEdit;
    @FXML
    private Button saveChangesButton;
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
        if (serviceListView.getSelectionModel().getSelectedItem()==null){
            addMessage.setText("No service selected!");
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Are you sure you want to delete the service?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get().equals(ButtonType.OK)) {
                DentistFacilitiesService.deleteService(WhoIsLoggedInfo.getLoggedUsername(),serviceListView.getSelectionModel().getSelectedItem().toString());

                addMessage.setText("Service deleted successfully !");
            }
        }
        updateListView();
    }

    public void handleEditServiceAction() throws Exception{
        if (serviceListView.getSelectionModel().getSelectedItem()==null){
            addMessage.setText("No service selected!");
        }
        else{
            editMessage.setVisible(true);
            serviceEdit.setVisible(true);
            saveChangesButton.setVisible(true);
            editMessage.setText("Type new service below:");
        }
    }

    public void handleSaveEditChanges() throws Exception{
        try{
            DentistFacilitiesService.editService(WhoIsLoggedInfo.getLoggedUsername(),serviceListView.getSelectionModel().getSelectedItem().toString(),serviceEdit.getText());
            updateListView();
            addMessage.setText("Service edited successfully !");
            editMessage.setVisible(false);
            serviceEdit.setVisible(false);
            saveChangesButton.setVisible(false);

        }
        catch (EmptyTextfieldsException e){
            editMessage.setText(e.getMessage());
        }
        catch(DentistServiceExistsException e){
            editMessage.setText(e.getMessage());
        }
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
