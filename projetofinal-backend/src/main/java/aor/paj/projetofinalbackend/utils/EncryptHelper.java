package aor.paj.projetofinalbackend.utils;

import org.mindrot.jbcrypt.BCrypt;

public class EncryptHelper {

    public static String encryptPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}

