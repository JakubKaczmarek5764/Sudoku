package pl.comp.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Locale locale = new Locale("pl");
        ResourceBundle bundle = ResourceBundle.getBundle("MyBundle", locale);
        Parent root = FXMLLoader.load(getClass().getResource("/start.fxml"), bundle);
        Scene scene1 = new Scene(root);
        primaryStage.setScene(scene1);
        primaryStage.show();

    }
}
