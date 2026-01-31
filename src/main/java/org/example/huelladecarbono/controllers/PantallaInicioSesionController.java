package org.example.huelladecarbono.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.huelladecarbono.HelloApplication;
import org.example.huelladecarbono.model.Usuario;
import org.example.huelladecarbono.services.UsuarioService;

import java.io.File;
import java.io.IOException;

public class PantallaInicioSesionController {
    public TextField txtEmail;
    public PasswordField txtPassword;
    public Label datosIncorrectos;

    //Metodo que inicia sesion con un usuario existente en la base de datos usa usuarioService en el caso de que no exista
    //Mostrara un mensaje de que no existe usuario con esos datos
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
                File imagenURL = new File("imagenes/IconoHuella.png");
                Image image = new Image(imagenURL.toURI().toString());
                stage.getIcons().add(image);
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

    //Metodo que abre la ventada de ir al registro de usuario
    public void irRegistro(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaRegistro.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = null;
            scene = new Scene(fxmlLoader.load());
            stage.setTitle("HuellaCarbono");
            File imagenURL = new File("imagenes/IconoHuella.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
