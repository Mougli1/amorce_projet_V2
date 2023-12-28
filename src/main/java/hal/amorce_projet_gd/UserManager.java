package hal.amorce_projet_gd;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private static final String USER_DATA_FILE = "users.txt";
    private static Map<String, String> users = new HashMap<>();

    static {
        loadUsers();
    }

    public static boolean registerUser(String username, String password) {
        // Check if user already exists
        if (users.containsKey(username)) {
            return false; // User already exists
        }

        // Register new user
        users.put(username, password);
        saveUsers();
        return true;
    }

    public static boolean authenticateUser(String username, String password) {
        String correctPassword = users.get(username);
        return correctPassword != null && correctPassword.equals(password);
    }

    private static void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            // Handle exceptions or create a new file if doesn't exist
            System.out.println("No existing user data. A new file will be created.");
        }
    }

    private static void saveUsers() {
        try (PrintWriter out = new PrintWriter(new FileWriter(USER_DATA_FILE))) {
            for (Map.Entry<String, String> entry : users.entrySet()) {
                out.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
