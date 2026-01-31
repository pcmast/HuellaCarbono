package org.example.huelladecarbono.DAO;

import org.example.huelladecarbono.connection.Connection;
import org.example.huelladecarbono.model.Categoria;
import org.example.huelladecarbono.model.Habito;
import org.example.huelladecarbono.model.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HabitoDAO {
    //Metodo que recoge todos los habitos de la base de datos
    public List<Habito> getHabitos() {
        try (Session session = Connection.getInstance().getSession()) {
            return session.createQuery("FROM Habito", Habito.class).list();
        }
    }

    //Metodo que recoge todos los habitos de un usuario en concreto
    public List<Habito> getHabitosPorUsuario(Usuario usuario) {
        try (Session session = Connection.getInstance().getSession()) {
            return session.createQuery(
                            "FROM Habito h WHERE h.idUsuario.id = :usuarioId", Habito.class)
                    .setParameter("usuarioId", usuario.getId())
                    .list();
        }
    }
    //Metodo que recoge las categorias por habitos del usuario actual del sistema (Para las recomendaciones)
    public List<Categoria> getCategoriasPorHabitosUsuario(Usuario usuario) {
        try (Session session = Connection.getInstance().getSession()) {

            return session.createQuery(
                            "SELECT DISTINCT a.idCategoria " +
                                    "FROM Habito h " +
                                    "JOIN h.idActividad a " +
                                    "WHERE h.idUsuario.id = :usuarioId",
                            Categoria.class
                    ).setParameter("usuarioId", usuario.getId())
                    .list();
        }
    }

    //Metodo que añade un habito a la base de datos
    public boolean addHabito(Habito habito) {
        boolean inserted = false;
        Transaction tx = null;
        Session session = null;

        try {
            session = Connection.getInstance().getSession();
            tx = session.beginTransaction();

            session.save(habito);

            tx.commit();
            inserted = true;

        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            ex.printStackTrace();

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return inserted;
    }

    //Metodo que actualiza un habito de la base de datos
    public boolean actualizarHabito(Habito habito) {
        boolean updated = false;
        Transaction tx = null;

        try (Session session = Connection.getInstance().getSession()) {
            tx = session.beginTransaction();
            session.merge(habito);
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

    //Metodo que elimina un habito de la base de datos
    public boolean eliminarHabito(Habito habito) {
        boolean eliminado = false;
        Transaction tx = null;

        try (Session session = Connection.getInstance().getSession()) {
            tx = session.beginTransaction();
            session.remove(habito);
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
}