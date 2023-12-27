package hal.amorce_projet_gd;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UserLoginApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("User Login");

        // Login Form Setup
        GridPane loginGrid = new GridPane();
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.setHgap(10);
        loginGrid.setVgap(10);
        loginGrid.setPadding(new Insets(25, 25, 25, 25));

        Label userNameLabel = new Label("User Name:");
        TextField userNameTextField = new TextField();
        Label pwLabel = new Label("Password:");
        PasswordField pwBox = new PasswordField();
        Button btn = new Button("Sign in");

        loginGrid.add(userNameLabel, 0, 0);
        loginGrid.add(userNameTextField, 1, 0);
        loginGrid.add(pwLabel, 0, 1);
        loginGrid.add(pwBox, 1, 1);
        loginGrid.add(btn, 1, 2);

        btn.setOnAction(e -> {
            try {
                // Here you would validate user credentials
                // For now, let's just open the chart view after "login"
                new PortefeuilleApplication().start(new Stage());
                primaryStage.hide(); // Hide the login window
            } catch (Exception ex) {
                ex.printStackTrace();  // Or handle the exception more gracefully
            }
        });

        // Set and show the scene
        Scene scene = new Scene(loginGrid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
