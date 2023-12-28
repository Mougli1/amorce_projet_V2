package hal.amorce_projet_gd;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private static final String USER_DATA_FILE = "users.txt";
    private static Map<String, User> users = new HashMap<>();

    static {
        loadUsers();
    }

    public static boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, new User(username, password, 0.0)); // Assuming default balance is 0.0
        saveUsers();
        return true;
    }

    public static boolean authenticateUser(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    private static void loadUsers() {
        File file = new File(USER_DATA_FILE);
        if (!file.exists()) {
            System.out.println("User data file does not exist. A new file will be created.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 3);
                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    double balance;
                    try {
                        balance = Double.parseDouble(parts[2].replace(",", "."));
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid balance format for user " + username);
                        continue;
                    }
                    users.put(username, new User(username, password, balance));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user data: " + e.getMessage());
        }

    }

    public static void saveUsers() {
        try (PrintWriter out = new PrintWriter(new FileWriter(USER_DATA_FILE))) {
            for (Map.Entry<String, User> entry : users.entrySet()) {
                User user = entry.getValue();
                // Format might automatically be in 0.00 format but ensure consistency if changing locales
                out.printf("%s:%s:%.2f\n", user.getUsername(), user.getPassword(), user.getBalance());
            }
        } catch (IOException e) {
            System.err.println("Error saving user data: " + e.getMessage());
        }
    }

    public static void updateUserBalance(String username, double newBalance) {
        User user = users.get(username);
        if (user != null) {
            user.setBalance(newBalance);
            saveUsers();
        }
    }
}
