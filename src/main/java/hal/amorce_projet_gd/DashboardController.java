package hal.amorce_projet_gd;

import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.impl.CoinGeckoApiClientImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.impl.CoinGeckoApiClientImpl;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
public class DashboardController {
    @FXML
    private StackPane contentArea; // Ensure this matches the fx:id of the placeholder in FXML

    @FXML
    private TableView<CryptoAsset> portfolioTableView;
    @FXML
    private BorderPane mainPane;
    @FXML
    private Label balanceLabel;
    private LineChart<String, Number> lineChart; // Make sure to add this LineChart to your FXML
    private Portfolio portfolio = new Portfolio();
    private CoinGeckoApiClient client = new CoinGeckoApiClientImpl();
    private String username;
    private double balance = 1000.0; // Assume starting balance

    public void initialize() {
        setupPortfolioTable();
        updateBalanceLabel();
    }

    private void setupPortfolioTable() {
        TableColumn<CryptoAsset, String> cryptoColumn = new TableColumn<>("Crypto");
        cryptoColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<CryptoAsset, Double> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        portfolioTableView.getColumns().addAll(cryptoColumn, quantityColumn);
    }

    private void updatePortfolioDisplay() {
        portfolioTableView.setItems(FXCollections.observableArrayList(portfolio.getAssetsList()));
    }


    private void updateBalanceLabel() {
        balanceLabel.setText(String.format("Solde actuel: %.2f USD", balance));
    }
    @FXML
    protected void handleHistory() {
        // Implement your history view logic here
    }

    @FXML
    protected void handleBTC() throws Exception {
        // Chargez la vue du graphe Bitcoin
        Node portefeuilleView = FXMLLoader.load(getClass().getResource("portefeuille-view.fxml"));
        contentArea.getChildren().setAll(portefeuilleView); // Remplacez le contenu de l'emplacement par le graphe

        // Cachez le TableView
        portfolioTableView.setVisible(false);
    }


    @FXML
    protected void handleETH() {
        // Implement ETH handling logic here
    }

    @FXML
    protected void handleSOL() {
        // Implement SOL handling logic here
    }

    @FXML
    protected void handlePortfolio() {
        updateBalanceLabel();
        List<String> choices = Arrays.asList("Recharger", "Acheter", "Vendre");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Recharger", choices);
        dialog.setTitle("Mon Portefeuille");
        dialog.setHeaderText("Choisissez une action pour votre portefeuille");
        dialog.setContentText("Sélectionnez :");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(this::executePortfolioAction);
        // Rendre le TableView visible à nouveau
        portfolioTableView.setVisible(true);

        // Effacer le contenu dynamique précédent
        contentArea.getChildren().clear();
    }

    private void executePortfolioAction(String action) {
        switch (action) {
            case "Recharger":
                handleRecharge();
                break;
            case "Acheter":
                handleBuy();
                break;
            case "Vendre":
                handleSell();
                break;
            default:
                break;
        }
    }

    private void handleRecharge() {
        TextInputDialog dialog = new TextInputDialog("100");
        dialog.setTitle("Recharger le Portefeuille");
        dialog.setHeaderText("Rechargement du portefeuille");
        dialog.setContentText("Entrez le montant à recharger:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(amountStr -> {
            try {
                double amount = Double.parseDouble(amountStr);
                balance += amount;
                updateBalanceLabel();
                UserManager.updateUserBalance(username, balance);
            } catch (NumberFormatException e) {
                // Error handling for invalid input
            }
        });
    }

    private void handleBuy() {
        List<String> cryptoChoices = Arrays.asList("bitcoin", "ethereum", "litecoin"); // Add more as needed
        ChoiceDialog<String> cryptoChoiceDialog = new ChoiceDialog<>("bitcoin", cryptoChoices);
        cryptoChoiceDialog.setTitle("Choose Cryptocurrency");
        cryptoChoiceDialog.setHeaderText("Choose which cryptocurrency to buy");
        cryptoChoiceDialog.setContentText("Available cryptocurrencies:");
        Optional<String> cryptoResult = cryptoChoiceDialog.showAndWait();
        if (!cryptoResult.isPresent()) return; // User cancelled the dialog

        String chosenCrypto = cryptoResult.get();
        fetchAndBuyCrypto(chosenCrypto);
    }

    private void fetchAndBuyCrypto(String cryptoId) {
        try {
            Map<String, Map<String, Double>> prices = client.getPrice(cryptoId, "usd");
            Double currentPrice = prices.get(cryptoId).get("usd");
            promptForPurchase(cryptoId, currentPrice);
        } catch (Exception e) {
            e.printStackTrace();
            // Consider better error handling and user feedback
        }
    }




    private void promptForPurchase(String crypto, double price) {
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Buy Cryptocurrency");
        dialog.setHeaderText("Current price of " + crypto + " is $" + price);
        dialog.setContentText("Enter amount to buy:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(amountStr -> {
            try {
                double unitsToBuy = Double.parseDouble(amountStr);
                double cost = unitsToBuy * price;
                if (balance >= cost) {
                    balance -= cost;
                    portfolio.addToPortfolio(crypto, unitsToBuy);
                    updateBalanceLabel();
                    updatePortfolioDisplay();
                    UserManager.updateUserBalance(username, balance);
                    // Optionally update user's portfolio in persistent storage
                } else {
                    // Not enough balance
                }
            } catch (NumberFormatException e) {
                // Invalid input
            }
        });
    }









    private void handleSell() {
        // Your existing handleSell method
    }

    // Implement other methods as needed
}
