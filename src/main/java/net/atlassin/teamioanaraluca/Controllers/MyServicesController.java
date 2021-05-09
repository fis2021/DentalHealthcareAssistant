package net.atlassin.teamioanaraluca.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import net.atlassin.teamioanaraluca.Model.DentistServices;
import net.atlassin.teamioanaraluca.Services.DentistFacilitiesService;
import org.dizitart.no2.objects.ObjectRepository;

public class MyServicesController {
    private static ObjectRepository<DentistServices> servicesRepository = DentistFacilitiesService.getServicesRepository();
    @FXML
    public ListView<String> serviceListView = new ListView<>();
    @FXML
    private TextField serviceDescription;
}
