package org.example.huelladecarbono.DAO;

import org.example.huelladecarbono.connection.Connection;
import org.example.huelladecarbono.model.Categoria;
import org.example.huelladecarbono.model.Recomendacion;
import org.hibernate.Session;

import java.util.List;

public class RecomendacionesDAO {

    public List<Recomendacion> getRecomendaciones() {
        try (Session session = Connection.getInstance().getSession()) {
            return session.createQuery("FROM Recomendacion", Recomendacion.class).list();
        }
    }

    public List<Recomendacion> getRecomendacionesPorCategoria(Categoria categoria) {
        try (Session session = Connection.getInstance().getSession()) {
            return session.createQuery(
                            "FROM Recomendacion r WHERE r.idCategoria = :categoria",
                            Recomendacion.class
                    )
                    .setParameter("categoria", categoria)
                    .list();
        }
    }
}