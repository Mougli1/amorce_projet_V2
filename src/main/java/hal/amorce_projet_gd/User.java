package hal.amorce_projet_gd;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    private String username;
    private String password;
    private double balance;
    private Map<String, Double> portfolio;

    public User(String username, String password, double balance) {
        this.username = username;
        this.password = password; // Ideally, hash the password
        this.balance = balance;
        this.portfolio = new HashMap<>();
    }

    // Add a method to hash the password (use a real library in practice)
    // For simplicity, I am not changing the hashing method here.
    // In a real-world application, you should use a strong hashing algorithm like BCrypt or SCrypt.
    private String hashPassword(String password) {
        // Simple hash function, use a real one in production (like BCrypt)
        return Integer.toString(password.hashCode());
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public Map<String, Double> getPortfolio() { return portfolio; }
    public void setPortfolio(Map<String, Double> portfolio) { this.portfolio = portfolio; }
    public void addToPortfolio(String cryptoId, double quantity) { portfolio.put(cryptoId, portfolio.getOrDefault(cryptoId, 0.0) + quantity); }
    public void removeFromPortfolio(String cryptoId, double quantity) { double currentQty = portfolio.getOrDefault(cryptoId, 0.0); if (currentQty > quantity) { portfolio.put(cryptoId, currentQty - quantity); } else { portfolio.remove(cryptoId); } }
}
