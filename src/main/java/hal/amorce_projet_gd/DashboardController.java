package hal.amorce_projet_gd;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class DashboardController {
    // Add methods to handle button clicks, navigating to different parts of the application
    public void viewExchangeRates(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("exchange-rates.fxml")); // Ensure the FXML file exists
        stage.setScene(new Scene(root));
        stage.show();
    }



    public void viewPortfolio(ActionEvent event) throws Exception {
        // Logic to switch to Portfolio View
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("portefeuille-view.fxml")); // Ensure this file exists and is correct
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void openTransactionManagement(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("transaction-management.fxml")); // Make sure the FXML file is correct
        stage.setScene(new Scene(root));
        stage.show();
    }

}
