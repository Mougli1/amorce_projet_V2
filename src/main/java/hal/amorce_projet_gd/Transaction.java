package hal.amorce_projet_gd;

import java.time.LocalDateTime;

public class Transaction {
    private String type; // "Recharge", "Buy", "Sell"
    private String crypto; // Cryptocurrency code, e.g., "BTC", "ETH"
    private double amount; // Amount of currency or crypto
    private double pricePerUnit; // Price per unit for buy/sell
    private LocalDateTime dateTime; // Date and time of the transaction

    public Transaction(String type, String crypto, double amount, double pricePerUnit, LocalDateTime dateTime) {
        this.type = type;
        this.crypto = crypto;
        this.amount = amount;
        this.pricePerUnit = pricePerUnit;
        this.dateTime = dateTime;
    }

    // Getters
    public String getType() { return type; }
    public String getCrypto() { return crypto; }
    public double getAmount() { return amount; }
    public double getPricePerUnit() { return pricePerUnit; }
    public LocalDateTime getDateTime() { return dateTime; }
}
