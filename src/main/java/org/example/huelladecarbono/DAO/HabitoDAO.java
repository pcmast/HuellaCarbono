package org.example.huelladecarbono.DAO;

import org.example.huelladecarbono.connection.Connection;
import org.example.huelladecarbono.model.Habito;
import org.example.huelladecarbono.model.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HabitoDAO {
    public List<Habito> getHabitos() {
        try (Session session = Connection.getInstance().getSession()) {
            return session.createQuery("FROM Habito", Habito.class).list();
        }
    }

    public List<Habito> getHabitosPorUsuario(Usuario usuario) {
        try (Session session = Connection.getInstance().getSession()) {
            return session.createQuery(
                            "FROM Habito h WHERE h.idUsuario.id = :usuarioId", Habito.class)
                    .setParameter("usuarioId", usuario.getId())
                    .list();
        }
    }

    public boolean addHabito(Habito habito) {
        boolean inserted = false;
        Transaction tx = null;
        Session session = null;

        try {
            session = Connection.getInstance().getSession();
            tx = session.beginTransaction();

            session.merge(habito);

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