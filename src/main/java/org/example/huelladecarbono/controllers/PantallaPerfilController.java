package org.example.huelladecarbono.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import org.example.huelladecarbono.model.Usuario;
import org.example.huelladecarbono.services.UsuarioService;

import java.util.Optional;

public class PantallaPerfilController {
    public TextField txtNombre;
    public TextField txtEmail;
    public PasswordField txtPassword;
    public Label lblMensaje;
    public Label fechaRegistro;
    public PasswordField txtProbarPassword;
    private boolean usuarioActualizado = false;

    public void initialize() {
        txtNombre.setText(UsuarioActualController.getInstance().getUsuario().getNombre());
        txtEmail.setText(UsuarioActualController.getInstance().getUsuario().getEmail());
        txtPassword.setDisable(true);
        fechaRegistro.setText(UsuarioActualController.getInstance().getUsuario().getFechaRegistro().toString());
    }

    public void guardarCambios(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar cambios");
        alert.setHeaderText("¿Seguro que quieres cambiar los datos del usuario?");
        alert.setContentText("Esta acción actualizará tu información.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
        Usuario usuario = new Usuario();
        usuario.setId(UsuarioActualController.getInstance().getUsuario().getId());
        usuario.setNombre(txtNombre.getText());
        usuario.setEmail(txtEmail.getText());
        if (!txtPassword.getText().isEmpty()) {
            usuario.setContraseña(txtPassword.getText());
        }else {
            usuario.setContraseña(UsuarioActualController.getInstance().getUsuario().getContraseña());
        }
        usuario.setFechaRegistro(UsuarioActualController.getInstance().getUsuario().getFechaRegistro());
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.updateUsuario(usuario);
        }
    }

    public void probarContra(ActionEvent actionEvent) {
        String input = txtProbarPassword.getText().trim();
        if (input.equals(UsuarioActualController.getInstance().getUsuario().getContraseña())) {
            txtPassword.setDisable(false);
        }

    }
}
