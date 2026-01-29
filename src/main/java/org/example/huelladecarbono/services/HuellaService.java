package org.example.huelladecarbono.services;

import org.example.huelladecarbono.DAO.HuellaDAO;
import org.example.huelladecarbono.DAO.UsuarioDAO;
import org.example.huelladecarbono.model.Huella;
import org.example.huelladecarbono.model.Usuario;

import java.time.LocalDate;
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
    public List<Huella> getHuellasPorUsuario(Usuario usuario) {
        return  huellaDAO.getHuellasPorUsuario(usuario);
    }
    public List<Huella> getHuellasPorMes(Usuario usuario, int mes, int anio) {
        return huellaDAO.getHuellasMes(usuario, mes, anio);
    }
    public List<Huella> getHuellasPorDia(Usuario usuario, LocalDate fecha) {
        return huellaDAO.getHuellasDia(usuario, fecha);
    }
    public List<Huella> getHuellasAnio(Usuario usuario, int anio){
        return huellaDAO.getHuellasAnio(usuario, anio);
    }
    public List<Huella> getHuellasSemana(Usuario usuario, LocalDate inicio, LocalDate fin) {
        return huellaDAO.getHuellasSemana(usuario, inicio, fin);
    }


}