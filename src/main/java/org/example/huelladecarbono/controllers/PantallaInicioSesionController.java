package org.example.huelladecarbono.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.huelladecarbono.HelloApplication;
import org.example.huelladecarbono.model.Usuario;
import org.example.huelladecarbono.services.UsuarioService;

import java.io.IOException;

public class PantallaInicioSesionController {
    public TextField txtEmail;
    public PasswordField txtPassword;
    public Label datosIncorrectos;

    public void iniciarSesion(ActionEvent actionEvent) {
        UsuarioService usuarioService = new UsuarioService();

        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();

        Usuario usuario = usuarioService.getUsuario(email);

        if (usuario != null && usuario.getContraseña().trim().equals(password.trim())) {
            datosIncorrectos.setText("");
            UsuarioActualController.getInstance().setUsuario(usuario);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaInicioUsuario.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("HuellaCarbono");
                stage.setScene(scene);
                stage.setResizable(true);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            datosIncorrectos.setText("Datos de sesión incorrectos");
        }
    }

    public void irRegistro(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaRegistro.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = null;
            scene = new Scene(fxmlLoader.load());
            stage.setTitle("HuellaCarbono");
            stage.setScene(scene);
            stage.setResizable(false);
            //stage.initModality(Modality.WINDOW_MODAL); Pone la pantalla como padre
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
