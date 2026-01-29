package org.example.huelladecarbono.services;

import org.example.huelladecarbono.DAO.RecomendacionesDAO;
import org.example.huelladecarbono.model.Categoria;
import org.example.huelladecarbono.model.Recomendacion;

import java.util.List;

public class RecomendacionService {
    RecomendacionesDAO recomendacionesDAO = new RecomendacionesDAO();
    public List<Recomendacion> getRecomendaciones() {
        return recomendacionesDAO.getRecomendaciones();
    }
    public List<Recomendacion> getRecomendacionesPorCategoria(Categoria categoria) {
        return recomendacionesDAO.getRecomendacionesPorCategoria(categoria);
    }


}
