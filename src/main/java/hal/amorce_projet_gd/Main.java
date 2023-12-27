package hal.amorce_projet_gd;

public class Main {
    public static void main(String[] args) {
        Utilisateur user = new Utilisateur("1", "Alice", "alice@example.com", "password123");

        // Use seConnecter for validating credentials
        if (user.seConnecter("alice@example.com", "password123")) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed!");
        }

        // Continue with portfolio and profile modifications as needed
    }
}
