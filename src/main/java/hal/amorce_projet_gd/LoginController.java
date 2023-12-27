package hal.amorce_projet_gd;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label loginMessageLabel;

    public void handleLogin(ActionEvent event) {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Assuming UserManager.authenticateUser(username, password) is your authentication method.
            if (UserManager.authenticateUser(username, password)) {
                // If login is successful, load the dashboard or next view
                loadDashboardView();
            } else {
                // Set error message or handle failed login
                System.out.println("Login Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDashboardView() {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            // Ensure this path correctly points to the dashboard.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void switchToRegister(ActionEvent event) {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            // Adjust this path to match where your register.fxml file is located.
            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
