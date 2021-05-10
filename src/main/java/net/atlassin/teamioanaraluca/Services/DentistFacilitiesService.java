package net.atlassin.teamioanaraluca.Services;

import net.atlassin.teamioanaraluca.Exceptions.DentistServiceExistsException;
import net.atlassin.teamioanaraluca.Exceptions.EmptyTextfieldsException;
import net.atlassin.teamioanaraluca.Model.DentistServices;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

import java.util.Objects;

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

    private static void checkEmptyTextFields(String description) throws EmptyTextfieldsException {
        if (description.equals(""))
            throw new EmptyTextfieldsException();
    }

    private static void checkServiceExists(String description)  throws DentistServiceExistsException {

        int ok = 0;
        for (DentistServices service : servicesRepository.find()){
            if (DentistServices.getWhoIsLogged().equals(service.getUsername()))
                if (description.equals(service.getDescription()))
                ok = 1;
        }

        if (ok == 1) throw new DentistServiceExistsException();

    }

}
