package hal.amorce_projet_gd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PortefeuilleApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("portefeuille-view.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Crypto Portfolio");
        stage.setScene(scene);
        stage.show();
    }

}
