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

    /*
     * Metodo que al inicializar el controlador coge los datos del usuarioactual y los muestra en el perfil en el caso
     * de que se quiera modificar esos datos
     * */
    public void initialize() {
        txtNombre.setText(UsuarioActualController.getInstance().getUsuario().getNombre());
        txtEmail.setText(UsuarioActualController.getInstance().getUsuario().getEmail());
        txtPassword.setDisable(true);
        fechaRegistro.setText(UsuarioActualController.getInstance().getUsuario().getFechaRegistro().toString());
    }

    //Metodo que actualizara los datos de usuario del usuario registrado en ese momento en el caso de no cambiar nada
    //dejara los datos tal cual
    public void guardarCambios(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar cambios");
        alert.setHeaderText("¿Seguro que quieres cambiar los datos del usuario?");
        alert.setContentText("Esta acción actualizará tu información.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Usuario usuario = UsuarioActualController.getInstance().getUsuario();
            usuario.setId(UsuarioActualController.getInstance().getUsuario().getId());
            usuario.setNombre(txtNombre.getText());
            usuario.setEmail(txtEmail.getText());
            if (!txtPassword.getText().isEmpty()) {
                usuario.setContraseña(txtPassword.getText());
            } else {
                usuario.setContraseña(UsuarioActualController.getInstance().getUsuario().getContraseña());
            }
            usuario.setFechaRegistro(UsuarioActualController.getInstance().getUsuario().getFechaRegistro());
            UsuarioService usuarioService = new UsuarioService();
            usuarioService.updateUsuario(usuario);
        }
    }

    //Metodo que abre el text  para cambiar la contraseña en el caso de que el usuario la sepa
    public void probarContra(ActionEvent actionEvent) {
        String input = txtProbarPassword.getText().trim();
        if (input.equals(UsuarioActualController.getInstance().getUsuario().getContraseña())) {
            txtPassword.setDisable(false);
        }

    }
}
