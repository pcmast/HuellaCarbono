package org.example.huelladecarbono.services;

import org.example.huelladecarbono.DAO.UsuarioDAO;
import org.example.huelladecarbono.model.Usuario;

public class UsuarioService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario getUsuario(String email) {
        return usuarioDAO.getUsuario(email);
    }

    public boolean addUsuario(Usuario usuario) {
        return usuarioDAO.addUsuario(usuario);
    }
    public boolean updateUsuario(Usuario usuario) {
        return usuarioDAO.updateUsuario(usuario);
    }
}
