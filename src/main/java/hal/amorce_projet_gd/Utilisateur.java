package hal.amorce_projet_gd;

import java.util.ArrayList;
import java.util.List;

public class Utilisateur {
    private String id;  // Unique identifier
    private String name;  // User's name
    private String email;  // User's email
    private String password;  // User's password
    private List<Portfolio> portfolios;  // User's portfolios

    // Constructor
    public Utilisateur(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.portfolios = new ArrayList<>();
    }

    // Connect user
    public boolean seConnecter(String email, String password) {
        // Simple authentication: match email and password
        return this.email.equals(email) && this.password.equals(password);
    }

    // Disconnect user
    public void seDeconnecter() {
        // Handle logout process if needed
        System.out.println(name + " has been logged out.");
    }

    // Modify profile
    public void modifierProfil(String name, String email, String password) {
        // Update user details
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Add portfolio to the user
    public void addPortfolio(Portfolio portfolio) {
        this.portfolios.add(portfolio);
    }

    // Getters and setters for all fields
    // ...

    // Example of a getter
    public String getEmail() {
        return this.email;
    }

    // ... More getters and setters

}
