package org.example.huelladecarbono.services;

import org.example.huelladecarbono.DAO.ActividadesDAO;
import org.example.huelladecarbono.model.Actividad;
import org.example.huelladecarbono.model.Huella;

import java.util.List;

public class ActividadService {
    ActividadesDAO actividadesDAO = new ActividadesDAO();
    public List<Actividad> getActividades() {
        return actividadesDAO.getActividades();
    }

}
