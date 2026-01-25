package org.example.huelladecarbono.controllers;

import org.example.huelladecarbono.model.Usuario;

public class UsuarioActualController {
    private static UsuarioActualController instance;
    private Usuario usuario;

    /**
     * Obtiene la instancia única del controlador de usuario actual (Singleton).
     * Si la instancia aún no ha sido creada, la crea.
     *
     * @return La instancia única de UsuarioActualController.
     */
    public static UsuarioActualController getInstance() {
        if (instance == null) {
            instance = new UsuarioActualController();
        }
        return instance;
    }


    public Usuario getUsuario() {
        return usuario;
    }


    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
