package org.example.huelladecarbono.DAO;


import org.example.huelladecarbono.connection.Connection;
import org.example.huelladecarbono.model.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UsuarioDAO {

    public Usuario getUsuario(String email) {
        try (Session session = Connection.getInstance().getSession()) {
            return session.createQuery("FROM Usuario u WHERE u.email = :email", Usuario.class).setParameter("email", email).uniqueResult();
        }
    }

    public boolean addUsuario(Usuario usuario) {
        boolean inserted = false;
        Transaction tx = null;

        try (Session session = Connection.getInstance().getSession()) {
            tx = session.beginTransaction();
            session.persist(usuario);
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



}
