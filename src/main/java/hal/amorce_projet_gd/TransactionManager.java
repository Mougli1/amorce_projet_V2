package hal.amorce_projet_gd;
import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.impl.CoinGeckoApiClientImpl;

import java.time.LocalDateTime;

public class TransactionManager {
    private CoinGeckoApiClient client = new CoinGeckoApiClientImpl();

    public Transaction executeTransaction(String currencyId, double amount, TransactionType type) {
        double rate = getExchangeRate(currencyId); // Get the current rate using your API client
        String transactionId = generateTransactionId(); // Generate a unique transaction ID

        // Create and return a new Transaction object
        return new Transaction(
                transactionId,
                LocalDateTime.now(),
                currencyId,
                amount,
                rate,
                type
        );
    }

    // Utility method to get exchange rate
    private double getExchangeRate(String currencyId) {
        // Implement API call to get current exchange rate
        // Placeholder return value
        return 1.0;
    }

    // Utility method to generate unique transaction IDs
    private String generateTransactionId() {
        // Implement logic to generate a unique ID
        // Placeholder return value
        return "TXN" + System.currentTimeMillis();
    }
}
