package org.example.huelladecarbono.DAO;

import org.example.huelladecarbono.connection.Connection;
import org.example.huelladecarbono.model.Actividad;
import org.example.huelladecarbono.model.Huella;
import org.hibernate.Session;

import java.util.List;

public class ActividadesDAO {

    //Metodo que recoge todas las actividades del sistema
    public List<Actividad> getActividades() {
        try (Session session = Connection.getInstance().getSession()) {
            return session.createQuery("FROM Actividad", Actividad.class).list();
        }
    }




}
