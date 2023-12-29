package hal.amorce_projet_gd;

import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.impl.CoinGeckoApiClientImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.time.LocalDateTime;
import java.util.*;

import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.impl.CoinGeckoApiClientImpl;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Label;
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
    private List<Transaction> transactionHistory = new ArrayList<>();

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
                logTransaction("Recharge", "USD", amount, 1.0); // Price per unit for recharge can be 1 as it's a direct currency input
            } catch (NumberFormatException e) {
                // Error handling for invalid input
            }
        });
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
        dialog.setTitle("Acheter des cryptomonnaies");
        dialog.setHeaderText("Le prix actuel de " + crypto + " est $" + price);
        dialog.setContentText("Entrer le montant à acheter");
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
                    logTransaction("Acheter", crypto, unitsToBuy, price); // Using the 'crypto' parameter
                } else {
                    // Show insufficient funds message
                    showInsufficientFundsMessage();
                }
            } catch (NumberFormatException e) {
                // Handle invalid input
                showInvalidInputMessage();
            }
        });
    }


    private void showInsufficientFundsMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Erreur de Transaction");
        alert.setHeaderText("Solde insuffisant !");
        alert.setContentText("Vous n'avez pas assez de fonds pour réaliser cet achat !");
        alert.showAndWait();
    }

    private void showInvalidInputMessage() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Invalid Purchase Amount");
        alert.setContentText("Please enter a valid number.");
        alert.showAndWait();
    }


    @FXML
    protected void handleHistory() {
        contentArea.getChildren().clear();
        TableView<Transaction> historyTable = new TableView<>();

        TableColumn<Transaction, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Transaction, String> cryptoColumn = new TableColumn<>("Crypto");
        cryptoColumn.setCellValueFactory(new PropertyValueFactory<>("crypto"));

        TableColumn<Transaction, Number> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Transaction, Number> priceColumn = new TableColumn<>("Price/Unit");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerUnit"));

        TableColumn<Transaction, LocalDateTime> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        historyTable.getColumns().addAll(typeColumn, cryptoColumn, amountColumn, priceColumn, dateColumn);
        historyTable.setItems(FXCollections.observableArrayList(transactionHistory));

        contentArea.getChildren().add(historyTable);
    }


    // Log transactions
    private void logTransaction(String type, String crypto, double amount, double pricePerUnit) {
        transactionHistory.add(new Transaction(type, crypto, amount, pricePerUnit, LocalDateTime.now()));
        System.out.println("Logged: " + type + ", " + crypto + ", " + amount); // Debug line
    }



    private void handleBuy() {

        List<String> cryptoChoices = Arrays.asList("bitcoin", "ethereum", "litecoin"); // Add more as needed
        ChoiceDialog<String> cryptoChoiceDialog = new ChoiceDialog<>("bitcoin", cryptoChoices);
        cryptoChoiceDialog.setTitle("Choose Cryptocurrency");
        cryptoChoiceDialog.setHeaderText("Choose which cryptocurrency to buy");
        cryptoChoiceDialog.setContentText("Available cryptocurrencies:");
        Optional<String> cryptoResult = cryptoChoiceDialog.showAndWait();
        if (cryptoResult.isPresent()) {
            String chosenCrypto = cryptoResult.get(); // Declared and assigned here
            fetchAndBuyCrypto(chosenCrypto);

        } else {
            // User cancelled the dialog or closed the dialog without input
        }
    }
































    // Updated handleSell method
    @FXML
    protected void handleSell() {
        // Choose the cryptocurrency to sell
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", portfolio.getOwnedCryptoNames());
        dialog.setTitle("Sell Cryptocurrency");
        dialog.setHeaderText("Select a cryptocurrency to sell");
        dialog.setContentText("Available cryptocurrencies:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(this::executeSell);
    }

    // Execute selling process
    private void executeSell(String cryptoName) {
        TextInputDialog amountDialog = new TextInputDialog();
        amountDialog.setTitle("Sell " + cryptoName);
        amountDialog.setHeaderText("Enter the amount of " + cryptoName + " to sell");
        amountDialog.setContentText("Amount:");
        Optional<String> amountResult = amountDialog.showAndWait();
        amountResult.ifPresent(amountStr -> sellCrypto(cryptoName, amountStr));
    }

    // Process selling crypto
    private void sellCrypto(String cryptoName, String amountStr) {
        try {
            double amount = Double.parseDouble(amountStr);
            CryptoAsset asset = portfolio.getAsset(cryptoName);
            if (asset != null && amount <= asset.getQuantity()) {
                double currentPrice = getCurrentPrice(cryptoName); // Implement this method
                double transactionAmount = amount * currentPrice;
                portfolio.removeQuantity(cryptoName, amount);
                balance += transactionAmount;
                updateBalanceLabel();
                updatePortfolioDisplay();
                logTransaction("Sell", cryptoName, amount, currentPrice);
            } else {
                showInsufficientAssetMessage(cryptoName);
            }
        } catch (NumberFormatException e) {
            showInvalidInputMessage();
        }
    }
    private double getCurrentPrice(String cryptoId) {
        // Assuming you have a CoinGeckoApiClient instance named 'client'
        Map<String, Map<String, Double>> prices = client.getPrice(cryptoId, "usd");
        return prices.get(cryptoId).get("usd");  // get USD price of the given crypto
    }

    private void showInsufficientAssetMessage(String cryptoName) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error Selling Crypto");
        alert.setHeaderText("Insufficient " + cryptoName + "!");
        alert.setContentText("You don't have enough " + cryptoName + " to sell.");
        alert.showAndWait();
    }

    // Implement other methods as needed
}
