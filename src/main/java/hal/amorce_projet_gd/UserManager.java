package hal.amorce_projet_gd;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static final String USER_DATA_FILE = "users.dat";
    private static Map<String, User> users = new HashMap<>();

    static {
        loadUsers();
    }

    public static boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, new User(username, password, 0.0));
        saveUsers();
        return true;
    }

    public static boolean authenticateUser(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password); // Change as per password hashing
    }

    private static void loadUsers() {
        File file = new File(USER_DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                users = (Map<String, User>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_DATA_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User getUser(String username) {
        return users.get(username);
    }

    public static void updateUser(String username, double newBalance, Map<String, Double> newPortfolio) {
        User user = users.get(username);
        if (user != null) {
            user.setBalance(newBalance);
            user.setPortfolio(newPortfolio);
            saveUsers();
        }
    }
    public static void updateUserBalance(String username, double newBalance) {
        User user = users.get(username);
        if (user != null) {
            user.setBalance(newBalance);
            saveUsers(); // Ensure users map is updated and saved
        }
    }
}
