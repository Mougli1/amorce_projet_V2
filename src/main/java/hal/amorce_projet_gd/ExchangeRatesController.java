package hal.amorce_projet_gd;

import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.domain.ExchangeRates.ExchangeRates;
import com.litesoftwares.coingecko.domain.ExchangeRates.Rate;
import com.litesoftwares.coingecko.impl.CoinGeckoApiClientImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Map;

public class ExchangeRatesController {
    @FXML
    private Label btcRateLabel;
    @FXML
    private Label ethRateLabel;

    private CoinGeckoApiClient client = new CoinGeckoApiClientImpl();

    @FXML
    public void initialize() {
        updateExchangeRates();
    }

    private void updateExchangeRates() {
        try {
            ExchangeRates exchangeRates = client.getExchangeRates();
            // Assuming getRates() is a method in ExchangeRates class returning Map<String, Rate>
            Map<String, Rate> rates = exchangeRates.getRates();

            Rate btcRate = rates.get("btc");
            if (btcRate != null) {
                btcRateLabel.setText(String.format("BTC: %s %s", btcRate.getValue(), btcRate.getUnit()));
            }

            Rate ethRate = rates.get("eth");
            if (ethRate != null) {
                ethRateLabel.setText(String.format("ETH: %s %s", ethRate.getValue(), ethRate.getUnit()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
