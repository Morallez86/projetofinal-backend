package aor.paj.projetofinalbackend.utils;

import jakarta.ejb.Singleton;
import org.mindrot.jbcrypt.BCrypt;

@Singleton
public class EncryptHelper {

    public String encryptPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
