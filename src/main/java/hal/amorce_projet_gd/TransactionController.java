package hal.amorce_projet_gd;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class TransactionController {
    @FXML
    private TextField amountField;

    @FXML
    private ComboBox<String> transactionType; // Assuming the values are "BUY" and "SELL"

    private TransactionManager transactionManager = new TransactionManager();

    @FXML
    protected void handleTransaction() {
        double amount = Double.parseDouble(amountField.getText());
        String type = transactionType.getValue();

        TransactionType transactionTypeEnum = TransactionType.valueOf(type);
        Transaction transaction = transactionManager.executeTransaction("currencyId", amount, transactionTypeEnum);

        // Update the UI or alert the user based on the result of the transaction
        // ...
    }
}
