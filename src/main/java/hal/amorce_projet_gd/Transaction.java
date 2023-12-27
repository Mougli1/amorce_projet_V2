package hal.amorce_projet_gd;

import java.time.LocalDateTime;  // Import the LocalDateTime class

public class Transaction {
    private String id;
    private LocalDateTime date;
    private String currencyId;
    private double amount;
    private double rate; // The exchange rate at the time of the transaction
    private TransactionType type; // This could be BUY or SELL

    // Constructor
    public Transaction(String id, LocalDateTime date, String currencyId, double amount, double rate, TransactionType type) {
        this.id = id;
        this.date = date;
        this.currencyId = currencyId;
        this.amount = amount;
        this.rate = rate;
        this.type = type;
    }

    // Getters and Setters
    // ... (implement all necessary getters and setters)

    // Additional methods as necessary
}
