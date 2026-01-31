package org.example.huelladecarbono.DAO;

import org.example.huelladecarbono.connection.Connection;
import org.example.huelladecarbono.model.Huella;
import org.example.huelladecarbono.model.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HuellaDAO {
    //Metodo que obtiene todas las huellas de la base de datos
    public List<Huella> getHuellas() {
        try (Session session = Connection.getInstance().getSession()) {
            return session.createQuery("FROM Huella", Huella.class).list();
        }
    }
    //Metodo que obtiene todas las huellas por usuario concreto
    public List<Huella> getHuellasPorUsuario(Usuario usuario) {
        try (Session session = Connection.getInstance().getSession()) {
            return session.createQuery(
                            "FROM Huella h WHERE h.idUsuario.id = :usuarioId", Huella.class)
                    .setParameter("usuarioId", usuario.getId())
                    .list();
        }
    }

    //Metodo que añade una huella a la base de datos
    public boolean addHuella(Huella huella) {
        boolean inserted = false;
        Transaction tx = null;

        try (Session session = Connection.getInstance().getSession()) {
            tx = session.beginTransaction();
            session.persist(huella);
            tx.commit();
            inserted = true;
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        }
        return inserted;
    }

    //Metodo que actualiza una huella de la base de datos
    public boolean actualizarHuella(Huella huella) {
        boolean updated = false;
        Transaction tx = null;

        try (Session session = Connection.getInstance().getSession()) {
            tx = session.beginTransaction();
            session.merge(huella);
            tx.commit();
            updated = true;
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        }

        return updated;
    }

    //Metodo que elimina una huella de la base de datos
    public boolean eliminarHuella(Huella huella) {
        boolean eliminado = false;
        Transaction tx = null;

        try (Session session = Connection.getInstance().getSession()) {
            tx = session.beginTransaction();
            session.remove(huella);
            tx.commit();
            eliminado = true;
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        }

        return eliminado;
    }

    //Metodo que coge todas las huellas por mes ademas tambien puse para que me coga las actividades y categoria para no hacer muchas consultas tanto
    //en mensual anual y diario
    public List<Huella> getHuellasMes(Usuario usuario, int mes, int anio) {
        try (Session session = Connection.getInstance().getSession()) {
            return session.createQuery(
                            "SELECT h FROM Huella h " +
                                    "JOIN FETCH h.idActividad a " +
                                    "JOIN FETCH a.idCategoria " +
                                    "WHERE h.idUsuario.id = :usuarioId " +
                                    "AND MONTH(h.fecha) = :mes " +
                                    "AND YEAR(h.fecha) = :anio",
                            Huella.class
                    )
                    .setParameter("usuarioId", usuario.getId())
                    .setParameter("mes", mes)
                    .setParameter("anio", anio)
                    .list();
        }
    }
    //Metodo que coge todas las huellas por dia ademas tambien puse para que me coga las actividades y categoria para no hacer muchas consultas
    public List<Huella> getHuellasDia(Usuario usuario, LocalDate fecha) {
        try (Session session = Connection.getInstance().getSession()) {
            return session.createQuery(
                            "SELECT h FROM Huella h " +
                                    "JOIN FETCH h.idActividad a " +
                                    "JOIN FETCH a.idCategoria " +
                                    "WHERE h.idUsuario.id = :usuarioId " +
                                    "AND h.fecha = :fecha",
                            Huella.class
                    )
                    .setParameter("usuarioId", usuario.getId())
                    .setParameter("fecha", fecha)
                    .list();
        }
    }
    //Metodo que coge todas las huellas por año ademas tambien puse para que me coga las actividades y categoria para no hacer muchas consultas
    public List<Huella> getHuellasAnio(Usuario usuario, int anio) {
        try (Session session = Connection.getInstance().getSession()) {
            return session.createQuery(
                            "SELECT h FROM Huella h " +
                                    "JOIN FETCH h.idActividad a " +
                                    "JOIN FETCH a.idCategoria " +
                                    "WHERE h.idUsuario.id = :usuarioId " +
                                    "AND YEAR(h.fecha) = :anio",
                            Huella.class
                    )
                    .setParameter("usuarioId", usuario.getId())
                    .setParameter("anio", anio)
                    .list();
        }
    }
    //Metodo que coge todas las huellas por semana ademas tambien puse para que me coga las actividades y categoria para no hacer muchas consultas
    public List<Huella> getHuellasSemana(Usuario usuario, LocalDate inicio, LocalDate fin) {
        try (Session session = Connection.getInstance().getSession()) {
            return session.createQuery(
                            "SELECT h FROM Huella h " +
                                    "JOIN FETCH h.idActividad a " +
                                    "JOIN FETCH a.idCategoria " +
                                    "WHERE h.idUsuario.id = :usuarioId " +
                                    "AND h.fecha BETWEEN :inicio AND :fin",
                            Huella.class
                    )
                    .setParameter("usuarioId", usuario.getId())
                    .setParameter("inicio", inicio)
                    .setParameter("fin", fin)
                    .list();
        }
    }

}
