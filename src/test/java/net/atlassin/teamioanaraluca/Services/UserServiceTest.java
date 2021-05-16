package net.atlassin.teamioanaraluca.Services;

import net.atlassin.teamioanaraluca.Exceptions.*;
import net.atlassin.teamioanaraluca.Model.User;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

class UserServiceTest {

    public static final String TEST = "test";
    public static final String USERNAME = "tester";
    public static final String PASSWORD = "123456A7";
    public static final String PHONENUMBER = "0756386146";

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before Class");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After Class");
    }

    @BeforeEach
    void setUp() throws IOException {
        FileSystemService.APPLICATION_FOLDER = ".Test-DHA-database";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        UserService.closeDatabase();
    }


    @Test
    @DisplayName("Database is initialized and there are no users")
    void testDatabaseIsInitializedAndNoUserIsPersisted() {

        assertThat(UserService.getAllUsers()).isNotNull();
        assertThat(UserService.getAllUsers()).isEmpty();
    }

    @Test
    @DisplayName("User is successfully added to the database")
    void testUserIsAddedToDatabase() {

        assertDoesNotThrow(() -> {
            UserService.addUser(USERNAME, PASSWORD, TEST, TEST, TEST, PHONENUMBER);
        });

        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user = UserService.getAllUsers().get(0);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(USERNAME);
        assertThat(PasswordAuthentication.authenticate(PASSWORD, user.getPassword())).isEqualTo(true);
        assertThat(user.getRole()).isEqualTo(TEST);
        assertThat(user.getName()).isEqualTo(TEST);
        assertThat(user.getEmail()).isEqualTo(TEST);
        assertThat(user.getPhoneNumber()).isEqualTo(PHONENUMBER);
    }

    @Test
    @DisplayName("User can not be added twice in the database")
    void testUserCanNotBeAddedTwice() {

        assertThrows(UsernameAlreadyExistsException.class, () -> {
            UserService.addUser(USERNAME, PASSWORD, TEST, TEST, TEST, PHONENUMBER);
            UserService.addUser(USERNAME, PASSWORD, TEST, TEST, TEST, PHONENUMBER);
        });
    }

    @Test
    @DisplayName("A username should not be empty")
    void testUsernameIsNotEmpty() {

        assertThrows(InvalidCredentialsException.class, () -> {
            UserService.addUser("", PASSWORD, TEST, TEST, TEST, PHONENUMBER);
        });
    }

    @Test
    @DisplayName("A username should at least 6 characters long")
    void testUsernameIsAtLeast6CharactersLong() {

        assertThrows(InvalidCredentialsException.class, () -> {
            UserService.addUser("test", PASSWORD, TEST, TEST, TEST, PHONENUMBER);
        });
    }

    @Test
    @DisplayName("A username should not contain special characters")
    void testUsernameDoesNotContainSpecialCharacters() {

        assertThrows(InvalidCredentialsException.class, () -> {
            UserService.addUser("tester@", PASSWORD, TEST, TEST, TEST, PHONENUMBER);
        });
    }

    @Test
    @DisplayName("A password should not be empty")
    void testPasswordIsNotEmpty() {

        assertThrows(InvalidCredentialsException.class, () -> {
            UserService.addUser(USERNAME, "", TEST, TEST, TEST, PHONENUMBER);
        });
    }

    @Test
    @DisplayName("A password should at least 8 characters long")
    void testPasswordIsAtLeast6CharactersLong() {

        assertThrows(InvalidCredentialsException.class, () -> {
            UserService.addUser(USERNAME, "test", TEST, TEST, TEST, PHONENUMBER);
        });
    }

    @Test
    @DisplayName("A password should contain at least one upper character and one number")
    void testPasswordMustContainAtLeastOneUpperCharacterAndOneNumber() {

        assertThrows(InvalidCredentialsException.class, () -> {
            UserService.addUser(USERNAME, "testtest", TEST, TEST, TEST, PHONENUMBER);
        });
    }

    @Test
    @DisplayName("The customer email should have @yahoo.com or @gmail.com extension")
    void testWrongEmailAddressCustomer() {

        assertThrows(InvalidCustomerEmailException.class, () -> {
            UserService.addUser(USERNAME, PASSWORD, "Customer", TEST, "abc@li.com", PHONENUMBER);
        });
    }

    @Test
    @DisplayName("The dentist email should have @doctor.com extension")
    void testWrongEmailAddressDentist() {

        assertThrows(InvalidDoctorEmailException.class, () -> {
            UserService.addUser(USERNAME, PASSWORD, "Dentist", TEST, "abc@yahoo.com", PHONENUMBER);
        });
    }

    @Test
    @DisplayName("The phone number must be exactly 10 characters long")
    void testWrongPhoneNumber() {

        assertThrows(InvalidCredentialsException.class, () -> {
            UserService.addUser(USERNAME, PASSWORD, TEST, TEST, TEST, "123456789");
        });
    }

    @Test
    @DisplayName("The username field can't be empty when you log in")
    void testEmptyUsernameFieldLogin() {

        assertThrows(EmptyFieldUsernameLoginException.class, () -> {
            UserService.checkUserCredentials("", PASSWORD, TEST);
        });
    }

    @Test
    @DisplayName("The password field can't be empty when you log in")
    void testEmptyPasswordFieldLogin() {

        assertThrows(EmptyFieldPasswordLoginException.class, () -> {
            UserService.checkUserCredentials(USERNAME, "", TEST);
        });
    }

    @Test
    @DisplayName("Test a failed log in due to wrong username")
    void testLoginFailedBecauseOfWrongUsername() {

        assertDoesNotThrow(() -> {
            UserService.addUser(USERNAME, PASSWORD, TEST, TEST, TEST, PHONENUMBER);
        });

        assertThrows(UsernameDoesNotExistException.class, () -> {
            UserService.checkUserCredentials("wrong", PASSWORD, TEST);
        });
    }

    @Test
    @DisplayName("Test a failed log in due to wrong password")
    void testLoginFailedBecauseOfWrongPassword() {

        assertDoesNotThrow(() -> {
            UserService.addUser(USERNAME, PASSWORD, TEST, TEST, TEST, PHONENUMBER);
        });

        assertThrows(WrongPasswordException.class, () -> {
            UserService.checkUserCredentials(USERNAME, "wrong", TEST);
        });
    }

    @Test
    @DisplayName("Test a failed log in due to wrong role")
    void testLoginFailedBecauseOfWrongRole() {

        assertDoesNotThrow(() -> {
            UserService.addUser(USERNAME, PASSWORD, TEST, TEST, TEST, PHONENUMBER);
        });

        assertThrows(WrongRoleException.class, () -> {
            UserService.checkUserCredentials(USERNAME, PASSWORD, "wrong");
        });
    }
}