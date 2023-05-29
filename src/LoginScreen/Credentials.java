package LoginScreen;

import java.util.HashMap;
import java.util.Map;

public class Credentials {
    private Map<String, String> credentials;
//comment
    public Credentials() {
        credentials = new HashMap<>();
    }

    public void addCredential(String username, String password) {
        credentials.put(username, password);
    }

    public boolean isValidCredential(String username, String password) {
        String storedPassword = credentials.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }
}
