package hal.amorce_projet_gd;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static Map<String, User> users = new HashMap<>();

    public static boolean registerUser(String username, String password) {
        if (!users.containsKey(username)) {
            users.put(username, new User(username, username, password));
            return true;
        }
        return false;
    }

    public static boolean authenticateUser(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    public static int getNumberOfUsers() {
        return users.size();
    }
}
