package hal.amorce_projet_gd;

import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.impl.CoinGeckoApiClientImpl;

import java.util.Map;

public class Monnaie {
    private String id; // Unique identifier for the currency
    private String name; // Name of the currency
    private String type; // Type of the currency (crypto/action)

    // Constructor
    public Monnaie(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    // Fetch the exchange rate
    public double getExchangeRate(String toCurrencyId) {
        CoinGeckoApiClient client = new CoinGeckoApiClientImpl();
        Map<String, Map<String, Double>> rates = client.getPrice(this.id, toCurrencyId);
        if (rates != null && rates.containsKey(this.id) && rates.get(this.id).containsKey(toCurrencyId)) {
            return rates.get(this.id).get(toCurrencyId);
        }
        System.err.println("Exchange rate data for " + this.id + " to " + toCurrencyId + " is not available.");
        return 0.0;
    }

    // Convert amount from this currency to another currency
    public double convertTo(String toCurrencyId, double amount) {
        double rate = getExchangeRate(toCurrencyId);
        return amount * rate;
    }

    // Add any additional methods or logic as necessary

    // Getters and setters
    public String getName() {
        return name;
    }
    // Add other getters and setters as necessary
}
