package utility;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Session implements Serializable {

    public String username;
    public String password;
    public byte[] hashPassword;
    public Session(String aUsername, String aPassword) {
        username = aUsername;
        password = aPassword;
        String pepper = "123%$3";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            if (password !=null) {
                password += pepper;
                password = md.digest(aPassword.getBytes(StandardCharsets.UTF_8)).toString();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
