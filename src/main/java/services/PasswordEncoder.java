package services;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {
    public static String encode(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    public static boolean matches(String password, String hash) {
        hash = "$2a$" + hash.substring(4);
        return BCrypt.checkpw(password, hash);
    }
}