package org.example.huelladecarbono.services;

import org.example.huelladecarbono.DAO.HuellaDAO;
import org.example.huelladecarbono.DAO.UsuarioDAO;
import org.example.huelladecarbono.model.Huella;
import org.example.huelladecarbono.model.Usuario;

import java.util.List;

public class HuellaService {
    private HuellaDAO huellaDAO = new HuellaDAO();

    public List<Huella> getHuellas() {
        return huellaDAO.getHuellas();
    }

    public boolean addHuella(Huella huella) {
        return huellaDAO.addHuella(huella);
    }
    public void actualizarHuella(Huella huella) {
        huellaDAO.actualizarHuella(huella);
    }
    public void eliminarHuella(Huella huella){
        huellaDAO.eliminarHuella(huella);
    }

}