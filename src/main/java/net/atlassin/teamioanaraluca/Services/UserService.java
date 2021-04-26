package net.atlassin.teamioanaraluca.Services;

import net.atlassin.teamioanaraluca.Exceptions.InvalidCredentialsException;
import net.atlassin.teamioanaraluca.Exceptions.InvalidCustomerEmailException;
import net.atlassin.teamioanaraluca.Exceptions.InvalidDoctorEmailException;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import net.atlassin.teamioanaraluca.Exceptions.UsernameAlreadyExistsException;
import net.atlassin.teamioanaraluca.Model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.regex.*;
import static net.atlassin.teamioanaraluca.Services.FileSystemService.getPathToFile;

public class UserService {

    private static ObjectRepository<User> userRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("registration-example.db").toFile())
                .openOrCreate("test", "test");

        userRepository = database.getRepository(User.class);
    }

    public static void addUser(String username, String password, String role, String name, String email, String phoneNumber) throws UsernameAlreadyExistsException, InvalidDoctorEmailException, InvalidCustomerEmailException, InvalidCredentialsException {
        checkUsername(username);
        checkUserDoesNotAlreadyExist(username);
        if(Objects.equals(role, "Dentist"))
        {
            checkEmailAddressDoctor(email);
        }
        else
        {
            checkEmailAddressCustomer(email);
        }
        checkPhoneNumber(phoneNumber);
        userRepository.insert(new User(username, encodePassword(password), role, name, email, phoneNumber));
    }

    private static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }
    private static void checkUsername(String username) throws InvalidCredentialsException{
        int ok = 1;
        String message = "";
        if (username.equals("")) {
            ok = 0;
            message = "Username cannot be empty!";
        } else if (username.length()<6){
            message = "Username should be at least 6 characters!";
            ok = 0;
        }else{
            String regex = "^[A-Za-z]\\w{5,}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(username);
            if (!m.matches()){
                ok = 0;
                message = "Username should contain only letters, digits and underscore(_) and should start with a letter!";
            }
        }


        if (ok==0){
            throw new InvalidCredentialsException(message);
        }
    }

    private static void checkEmailAddressDoctor(String email) throws InvalidDoctorEmailException {
        String extensionDoctor = "@doctor.com";
        int i, j, k, sw = 0;
        for(i = 0; i < email.length(); i++)
        {
            if(email.charAt(i) == '@')
            {
                k = 0;
                for(j = i; j < email.length(); j++)
                {
                    if(extensionDoctor.charAt(k) != email.charAt(j))
                    {
                        sw = 1;
                        break;
                    }
                    k++;
                }
                break;
            }
        }
        if(sw == 1)
        {
            throw new InvalidDoctorEmailException();
        }
    }
    private static void checkEmailAddressCustomer(String email) throws InvalidCustomerEmailException {
        String extensionCustomer1 = "@yahoo.com";
        String extensionCustomer2 = "@gmail.com";
        int i, j, k, sw1 = 0, sw2 = 0;
        for(i = 0; i < email.length(); i++)
        {
            if(email.charAt(i) == '@')
            {
                k = 0;
                for(j = i; j < email.length(); j++)
                {
                    if(extensionCustomer1.charAt(k) != email.charAt(j))
                    {
                        sw1 = 1;
                        break;
                    }
                    k++;
                }
                break;
            }
        }
        for(i = 0; i < email.length(); i++)
        {
            if(email.charAt(i) == '@')
            {
                k = 0;
                for(j = i; j < email.length(); j++)
                {
                    if(extensionCustomer2.charAt(k) != email.charAt(j))
                    {
                        sw2 = 1;
                        break;
                    }
                    k++;
                }
                break;
            }
        }
        if(sw1 == 1 && sw2 == 1)
        {
            throw new InvalidCustomerEmailException();
        }
    }

    private static void checkPhoneNumber(String phone) throws InvalidCredentialsException{
        int ok = 1;
        String message = "";
        String regex = "\\d{10}";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        if (!m.matches()){
            ok = 0;
            message = "Invalid phone number! Must contain only 10 digits.";
        }
        if (ok==0){
            throw new InvalidCredentialsException(message);
        }
    }
    /**
     * Hashing the password for storage using PBKDF2.
     *
     * @return a secure authentication token to be stored for later authentication
     */
    private static String encodePassword(String password) {
        PasswordAuthentication passwordAuthenticationObject = new PasswordAuthentication();
        return passwordAuthenticationObject.hash(password.toCharArray());
    }




}