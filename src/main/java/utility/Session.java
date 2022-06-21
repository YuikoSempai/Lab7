package utility;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

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
                hashPassword = md.digest(aPassword.getBytes(StandardCharsets.UTF_8));
            }else{
                hashPassword = null;
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

    public byte[] getHashPassword() {
        return hashPassword;
    }
}
