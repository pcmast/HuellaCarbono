package org.example.huelladecarbono;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 478);
        stage.setTitle("Huella de Carbono");
        File imagenURL = new File("imagenes/IconoHuella.png");
        Image image = new Image(imagenURL.toURI().toString());
        stage.getIcons().add(image);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
