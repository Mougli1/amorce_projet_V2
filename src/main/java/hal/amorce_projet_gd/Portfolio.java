// Portfolio.java
package hal.amorce_projet_gd;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {
    private String id;
    private List<Transaction> transactions;  // Changed from Monnaie to Transaction

    public Portfolio(String id) {
        this.id = id;
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    // Additional methods to calculate portfolio value, etc.
}
