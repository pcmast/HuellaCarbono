package org.example.huelladecarbono.DAO;

import org.example.huelladecarbono.connection.Connection;
import org.example.huelladecarbono.model.Huella;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HuellaDAO {

    public List<Huella> getHuellas() {
        try (Session session = Connection.getInstance().getSession()) {
            return session.createQuery("FROM Huella", Huella.class).list();
        }
    }

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

}
