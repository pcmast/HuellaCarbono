package org.example.huelladecarbono.DAO;


import org.example.huelladecarbono.connection.Connection;
import org.example.huelladecarbono.model.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UsuarioDAO {

    //Metodo que coge un usuario de la base de datos dependiendo del email introducido
    public Usuario getUsuario(String email) {
        try (Session session = Connection.getInstance().getSession()) {
            return session.createQuery("FROM Usuario u WHERE u.email = :email", Usuario.class).setParameter("email", email).uniqueResult();
        }
    }

    //Metodo que añade un usuario en la base de datos
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

    //Metodo que actualiza un usuario de la base de datos
    public boolean updateUsuario(Usuario usuario) {
        boolean updated = false;
        Transaction tx = null;

        try (Session session = Connection.getInstance().getSession()) {
            Usuario existingUser = session.get(Usuario.class, usuario.getId());
            if (existingUser != null) {
                tx = session.beginTransaction();

                existingUser.setNombre(usuario.getNombre());
                existingUser.setEmail(usuario.getEmail());
                existingUser.setContraseña(usuario.getContraseña());

                session.merge(existingUser); // Actualiza el objeto
                updated = true;
            } else {
                System.out.println("Usuario con ID " + usuario.getId() + " no existe.");
            }
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        }

        return updated;
    }

}
