package org.example.huelladecarbono.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.huelladecarbono.HelloApplication;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class PantallaPrincipalController {


    public Label lblUsuario;
    public Label lblHuellaMes;
    public Label lblCategoriaMayor;
    public Label lblRecomendacion1;
    public Label lblRecomendacion2;
    public Label lblRecomendacion3;

    public void verHuellas(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaHuellas.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("HuellaCarbono");
            stage.setScene(scene);

            Stage owner = (Stage) ((MenuItem) actionEvent.getSource()).getParentPopup().getOwnerWindow();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(owner);

            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void verHabitos(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaHabitos.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("HuellaCarbono");
            stage.setScene(scene);

            Stage owner = (Stage) ((MenuItem) actionEvent.getSource()).getParentPopup().getOwnerWindow();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(owner);

            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void verImpacto(ActionEvent actionEvent) {


    }

    public void verRecomendaciones(ActionEvent actionEvent) {


    }

    public void cerrarSesion(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar cierre de sesión");
        alert.setHeaderText(null);
        alert.setContentText("¿Seguro que quieres cerrar sesión?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            UsuarioActualController.getInstance().setUsuario(null);

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaLogin.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage;
                if (actionEvent.getSource() instanceof MenuItem menuItem) {
                    stage = (Stage) menuItem.getParentPopup().getOwnerWindow();
                } else {
                    stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                }
                stage.setTitle("HuellaCarbono");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void miPerfil(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaMiPerfil.fxml"));
            Scene scene = null;
            scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            Stage owner = (Stage) ((MenuItem) actionEvent.getSource()).getParentPopup().getOwnerWindow();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(owner);
            stage.setTitle("HuellaCarbono");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
