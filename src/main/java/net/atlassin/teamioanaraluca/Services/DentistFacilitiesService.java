package net.atlassin.teamioanaraluca.Services;

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


}
