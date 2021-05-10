package net.atlassin.teamioanaraluca.Services;

import net.atlassin.teamioanaraluca.Exceptions.DentistServiceExistsException;
import net.atlassin.teamioanaraluca.Exceptions.EmptyTextfieldsException;
import net.atlassin.teamioanaraluca.Model.DentistServices;
import net.atlassin.teamioanaraluca.Model.WhoIsLoggedInfo;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

import java.util.Objects;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;
public class DentistFacilitiesService {
    private static ObjectRepository<DentistServices> servicesRepository;

    public static ObjectRepository<DentistServices> getServicesRepository() {
        return servicesRepository;
    }

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(FileSystemService.getPathToFile("dental-healthcare-services.db").toFile())
                .openOrCreate("test", "test");

        servicesRepository = database.getRepository(DentistServices.class);
    }

    public static void addService(String username, String description) throws EmptyTextfieldsException, DentistServiceExistsException{
        checkEmptyTextFields(description);
        checkServiceExists(description);
        servicesRepository.insert(new DentistServices(username,description));
    }

    public static void deleteService(String username,String description){
        DentistServices service_aux = new DentistServices();

        for (DentistServices service : servicesRepository.find()){
            if (username.equals(service.getUsername())&&description.equals(service.getDescription())) {
                service_aux = service;
            }
        }

        servicesRepository.remove(eq("description",description),service_aux);
    }
    private static void checkEmptyTextFields(String description) throws EmptyTextfieldsException {
        if (description.equals(""))
            throw new EmptyTextfieldsException();
    }

    private static void checkServiceExists(String description)  throws DentistServiceExistsException {

        int ok = 0;
        for (DentistServices service : servicesRepository.find()){
            if (WhoIsLoggedInfo.getLoggedUsername().equals(service.getUsername()))
                if (description.equals(service.getDescription()))
                ok = 1;
        }

        if (ok == 1) throw new DentistServiceExistsException();

    }

}
