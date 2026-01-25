package org.example.huelladecarbono.connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Connection {
    //1. Atributo estatico de la misma clase (instance)
    private static Connection instance;
    //2. Atributo para el sessionFactory
    private SessionFactory sessionFactory;

    //3. Constructor privado
    private Connection() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al construir la SessionFactory");
        }
    }

    //4. Metodo publico y estático para devolver la instancia de la sesión
    public static Connection getInstance() {
        if (instance == null) {
            instance = new Connection();
        }
        return instance;
    }

    //5. Metodo public para abrir la sesión
    public Session getSession() {return sessionFactory.openSession();}

    //6. Metodo que cierra SessionFactory
    public void close(){
        if(sessionFactory!=null&&sessionFactory.isOpen()){
            sessionFactory.close();
        }
    }
}
