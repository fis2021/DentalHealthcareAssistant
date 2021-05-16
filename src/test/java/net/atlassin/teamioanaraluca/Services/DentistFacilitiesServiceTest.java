package net.atlassin.teamioanaraluca.Services;

import net.atlassin.teamioanaraluca.Exceptions.DentistServiceExistsException;
import net.atlassin.teamioanaraluca.Exceptions.EmptyTextfieldsException;
import net.atlassin.teamioanaraluca.Model.DentistServices;
import net.atlassin.teamioanaraluca.Model.WhoIsLoggedInfo;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;
class DentistFacilitiesServiceTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before All");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After All");
    }

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".Test-DHA-database";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        DentistFacilitiesService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        DentistFacilitiesService.getDatabase().close();
    }

    @Test
    @DisplayName("Database for services is initialized, and there are no services")
    void testDatabaseIsInitializedAndNoTripIsPersisted() {
        assertThat(DentistFacilitiesService.getAllServices()).isNotNull();
        assertThat(DentistFacilitiesService.getAllServices()).isEmpty();
    }

    @Test
    @DisplayName("Service is successfully added to the database")
    void testServiceIsAddedToDatabase() throws Exception {
        //Add a service
        DentistFacilitiesService.addService("doctor","consultation");
        assertThat(DentistFacilitiesService.getAllServices()).isNotNull();
        assertThat(DentistFacilitiesService.getAllServices()).size().isEqualTo(1);
        DentistServices service = DentistFacilitiesService.getAllServices().get(0);
    }

    @Test
    @DisplayName("When adding a service, description must be completed")
    void testCheckDescriptionFieldCompletedWhenAdding() {
        assertThrows(EmptyTextfieldsException.class, () -> {
            DentistFacilitiesService.addService("doctor","");
        });
    }

    @Test
    @DisplayName("When adding a service, the description must be unique")
    void testCheckDescriptionIsUniqueWhenAdding() {
        assertThrows(DentistServiceExistsException.class, () -> {
            WhoIsLoggedInfo.setLoggedUsername("doctor");
            DentistFacilitiesService.addService("doctor","consultation");
            DentistFacilitiesService.addService("doctor","consultation");
        });
    }

    @Test
    @DisplayName("Service is successfully deleted from the database")
    void testDeletingService() throws Exception{
        //Add a service
        DentistFacilitiesService.addService("doctor","consultation");
        assertThat(DentistFacilitiesService.getAllServices()).isNotNull();
        assertThat(DentistFacilitiesService.getAllServices()).size().isEqualTo(1);
        DentistServices service = DentistFacilitiesService.getAllServices().get(0);

        //Delete the service
        DentistFacilitiesService.deleteService("doctor","consultation");
        assertThat(DentistFacilitiesService.getAllServices()).isNotNull();
        assertThat(DentistFacilitiesService.getAllServices()).size().isEqualTo(0);
    }

    @Test
    @DisplayName("Service is successfully edited in the database")
    void testEditingService() throws Exception{
        WhoIsLoggedInfo.setLoggedUsername("doctor");
        //Add a service
        DentistFacilitiesService.addService("doctor","consultation");
        assertThat(DentistFacilitiesService.getAllServices()).isNotNull();
        assertThat(DentistFacilitiesService.getAllServices()).size().isEqualTo(1);
        DentistServices service = DentistFacilitiesService.getAllServices().get(0);

        //Edit the service
        DentistFacilitiesService.editService("doctor","consultation","consultationNew");
        assertThat(DentistFacilitiesService.getAllServices()).isNotNull();
        assertThat(DentistFacilitiesService.getAllServices()).size().isEqualTo(1);
    }

    @Test
    @DisplayName("When editing a service, the new description must be unique")
    void testCheckDescriptionIsUniqueWhenEditing() {
        assertThrows(DentistServiceExistsException.class, () -> {
            WhoIsLoggedInfo.setLoggedUsername("doctor");
            DentistFacilitiesService.addService("doctor","consultation");
            DentistFacilitiesService.editService("doctor","consultation","consultation");
        });
    }

    @Test
    @DisplayName("When adding a service, description must be completed")
    void testCheckDescriptionFieldCompletedWhenEditing() {
        assertThrows(EmptyTextfieldsException.class, () -> {
            DentistFacilitiesService.editService("doctor","consultation","");
        });
    }

    @Test
    @DisplayName("Edit succesfull for two distinct users but with the same description")
    void testEditWhenUsersHaveSameDescription() throws Exception{
        //Add service with the same description but different usernames
        WhoIsLoggedInfo.setLoggedUsername("doctor1");
        DentistFacilitiesService.addService("doctor1","consultation");
        WhoIsLoggedInfo.setLoggedUsername("doctor2");
        DentistFacilitiesService.addService("doctor2","consultation");
        assertThat(DentistFacilitiesService.getAllServices()).isNotNull();
        assertThat(DentistFacilitiesService.getAllServices()).size().isEqualTo(2);
        DentistServices service = DentistFacilitiesService.getAllServices().get(0);

        WhoIsLoggedInfo.setLoggedUsername("doctor2");
        //Edit the service
        DentistFacilitiesService.editService("doctor2","consultation","consultationNew");
        assertThat(DentistFacilitiesService.getAllServices()).isNotNull();
        assertThat(DentistFacilitiesService.getAllServices()).size().isEqualTo(2);
    }

    @Test
    @DisplayName("Edit succesfull for two distinct users but with the same description")
    void testDeleteWhenUsersHaveSameDescription() throws Exception{
        //Add service with the same description but different usernames
        WhoIsLoggedInfo.setLoggedUsername("doctor1");
        DentistFacilitiesService.addService("doctor1","consultation");
        WhoIsLoggedInfo.setLoggedUsername("doctor2");
        DentistFacilitiesService.addService("doctor2","consultation");
        assertThat(DentistFacilitiesService.getAllServices()).isNotNull();
        assertThat(DentistFacilitiesService.getAllServices()).size().isEqualTo(2);
        DentistServices service = DentistFacilitiesService.getAllServices().get(0);

        WhoIsLoggedInfo.setLoggedUsername("doctor2");
        //Edit the service
        DentistFacilitiesService.deleteService("doctor2","consultation");
        assertThat(DentistFacilitiesService.getAllServices()).isNotNull();
        assertThat(DentistFacilitiesService.getAllServices()).size().isEqualTo(1);
    }

}