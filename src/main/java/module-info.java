module org.example.huelladecarbono {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires mysql.connector.j;
    requires java.naming;
    requires javafx.base;


    opens org.example.huelladecarbono to javafx.fxml;
    opens org.example.huelladecarbono.model to javafx.fxml, org.hibernate.orm.core;
    opens org.example.huelladecarbono.controllers to javafx.fxml;
    exports org.example.huelladecarbono;
}