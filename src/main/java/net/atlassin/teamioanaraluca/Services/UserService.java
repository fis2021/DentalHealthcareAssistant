package net.atlassin.teamioanaraluca.Services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import net.atlassin.teamioanaraluca.Exceptions.UsernameAlreadyExistsException;
import net.atlassin.teamioanaraluca.Model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static net.atlassin.teamioanaraluca.Services.FileSystemService.getPathToFile;

public class UserService {

    private static ObjectRepository<User> userRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile(" .db").toFile())
                .openOrCreate("test", "test");

        userRepository = database.getRepository(User.class);
    }

    public static void addUser(String username, String password, String role, String name, String email, String phoneNumber) throws UsernameAlreadyExistsException {
        checkUserDoesNotAlreadyExist(username);
        userRepository.insert(new User(username, encodePassword(password), role, name, email, phoneNumber));
    }

    private static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                throw new UsernameAlreadyExistsException(username);
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