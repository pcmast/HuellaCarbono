package org.example.huelladecarbono.services;

import org.example.huelladecarbono.DAO.HabitoDAO;
import org.example.huelladecarbono.DAO.HuellaDAO;
import org.example.huelladecarbono.model.Habito;
import org.example.huelladecarbono.model.Huella;
import org.example.huelladecarbono.model.Usuario;

import java.util.List;

public class HabitoService {

    private HabitoDAO habitoDAO = new HabitoDAO();

    public List<Habito> getHabito() {
        return habitoDAO.getHabitos();
    }

    public boolean addHabito(Habito habito) {
        return habitoDAO.addHabito(habito);
    }
    public void actualizarHabito(Habito habito) {
        habitoDAO.actualizarHabito(habito);
    }
    public void eliminarHabito(Habito habito) {
        habitoDAO.eliminarHabito(habito);
    }
    public List<Habito> getHabitosPorUsuario(Usuario usuario) {
        return habitoDAO.getHabitosPorUsuario(usuario);
    }
}
