module org.example.huelladecarbono {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;


    opens org.example.huelladecarbono to javafx.fxml;
    exports org.example.huelladecarbono;
}