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

public class RegisterController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label registrationMessageLabel;

    public void handleRegister(ActionEvent event) throws Exception {
        String username = usernameField.getText(); // Get username from TextField
        String password = passwordField.getText(); // Get password from PasswordField

        if (UserManager.registerUser(username, password)) {
            registrationMessageLabel.setText("Registration successful!");
            switchToLogin(event); // Navigate back to login view or automatically log the user in
        } else {
            registrationMessageLabel.setText("Registration failed! User may already exist.");
        }
    }

    public void switchToLogin(ActionEvent event) throws Exception {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        // Ensure this path correctly points to the login.fxml file
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

}
