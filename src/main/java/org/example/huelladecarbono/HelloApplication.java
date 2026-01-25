package org.example.huelladecarbono;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 478);
        stage.setTitle("Huella de Carbono");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }
}
