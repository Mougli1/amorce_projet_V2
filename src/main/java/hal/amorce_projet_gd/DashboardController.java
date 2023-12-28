package hal.amorce_projet_gd;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;


public class DashboardController {
    @FXML
    private BorderPane mainPane;  // Reference to the main BorderPane of the dashboard
    @FXML
    private Label balanceLabel; // Label to display the balance

    private double balance = 1000.0; // Example starting balance

    // Existing methods...


    @FXML
    protected void handleHistory() {
        // Implement your history view logic here
    }

    @FXML
    protected void handleBTC() {
        // Implement BTC handling logic here
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
        // Update balance display
        balanceLabel.setText(String.format("Solde actuel: %.2f USD", balance));

        List<String> choices = Arrays.asList("Recharger", "Acheter", "Vendre");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Recharger", choices);
        dialog.setTitle("Mon Portefeuille");
        dialog.setHeaderText("Choisissez une action pour votre portefeuille");
        dialog.setContentText("Sélectionnez :");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(this::executePortfolioAction);
    }

    private void executePortfolioAction(String action) {
        switch (action) {
            case "Recharger":
                handleRecharge();
                break;
            case "Acheter":
                // Logique pour acheter
                break;
            case "Vendre":
                // Logique pour vendre
                break;
            default:
                // Gérer les cas non prévus
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
                balance += amount; // Add the amount to the balance
                balanceLabel.setText(String.format("Solde actuel: %.2f USD", balance)); // Update balance display
            } catch (NumberFormatException e) {
                // Handle invalid input
            }
        });
    }
}
