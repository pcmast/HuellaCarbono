package org.example.huelladecarbono.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.huelladecarbono.HelloApplication;
import org.example.huelladecarbono.model.Usuario;
import org.example.huelladecarbono.services.UsuarioService;
import org.example.huelladecarbono.utils.Utilidades;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

public class PantallaRegistroController {
    public TextField txtNombre;
    public TextField txtEmail;
    public PasswordField txtPassword;
    public Label datosIncorrectos;

    /*
    * Metod que registra un usuario en la base de datos  en el caso de existir ese usuario no lo registrara
    * */
    public void registrarUsuario(ActionEvent actionEvent) {
        UsuarioService usuarioService = new UsuarioService();

        Usuario usuario = usuarioService.getUsuario(txtEmail.getText());
        boolean registrado = false;
        boolean emailIncorrecto = false;
        emailIncorrecto = Utilidades.esCorreoElectronicoValido(txtEmail.getText());
        if (!txtNombre.getText().isEmpty() && !txtPassword.getText().isEmpty() && !txtEmail.getText().isEmpty()) {
            if (emailIncorrecto) {
                if (usuario == null) {
                    Usuario usuario1 = new Usuario();
                    usuario1.setNombre(txtNombre.getText());
                    usuario1.setEmail(txtEmail.getText());
                    usuario1.setContraseña(txtPassword.getText());
                    usuario1.setFechaRegistro(Instant.now());
                    registrado = usuarioService.addUsuario(usuario1);
                }else{
                    datosIncorrectos.setText("Usuario ya existe");
                }
                if (registrado) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaLogin.fxml"));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = null;
                        scene = new Scene(fxmlLoader.load(), 640, 478);
                        stage.setTitle("HuellaCarbono");
                        stage.setScene(scene);
                        File imagenURL = new File("imagenes/IconoHuella.png");
                        Image image = new Image(imagenURL.toURI().toString());
                        stage.getIcons().add(image);
                        stage.centerOnScreen();
                        stage.setResizable(false);
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                datosIncorrectos.setText("Introduce un email correcto");
            }
        }else {
            datosIncorrectos.setText("Debes introducir los datos");
        }
    }

    //Metodo que vuelve al login
    public void volverLogin(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaLogin.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = null;
            scene = new Scene(fxmlLoader.load(), 640, 478);
            stage.setTitle("HuellaCarbono");
            stage.setScene(scene);
            File imagenURL = new File("imagenes/IconoHuella.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

