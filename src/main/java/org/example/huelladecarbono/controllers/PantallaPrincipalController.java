package org.example.huelladecarbono.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.huelladecarbono.HelloApplication;
import org.example.huelladecarbono.model.Categoria;
import org.example.huelladecarbono.model.Habito;
import org.example.huelladecarbono.model.Huella;
import org.example.huelladecarbono.model.Recomendacion;
import org.example.huelladecarbono.services.HabitoService;
import org.example.huelladecarbono.services.HuellaService;
import org.example.huelladecarbono.services.RecomendacionService;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class PantallaPrincipalController {


    public Label lblUsuario;
    public Label lblHuellaMes;
    public Label lblCategoriaMayor;
    public Label lblRecomendacion1;
    public Label lblRecomendacion2;
    public Label lblRecomendacion3;

    //Metodo que al inicializar el controlador carga el impacto mensual y las recomendaciones que tiene el usuario en este año
    public void initialize() {
        lblUsuario.setText("Bienvenido, " + UsuarioActualController.getInstance().getUsuario().getNombre());
        cargarImpactoMensual();
        cargarRecomendaciones();
    }

    //Metodo que carga dependiendo de las huellas las recomendaciones para el usuario
    public void cargarRecomendaciones() {

        HabitoService habitoService = new HabitoService();
        RecomendacionService recomendacionService = new RecomendacionService();

        List<Categoria> categorias = habitoService.getCategoriasHabitosUsuario(UsuarioActualController.getInstance().getUsuario());

        if (categorias.isEmpty()) {
            lblRecomendacion1.setText("No hay recomendaciones disponibles");
            lblRecomendacion2.setText("");
            lblRecomendacion3.setText("");
            return;
        }

        List<Recomendacion> recomendaciones = new ArrayList<>();

        for (Categoria c : categorias) {
            List<Recomendacion> recs = recomendacionService.getRecomendacionesPorCategoria(c);
            for (Recomendacion r : recs) {
                if (recomendaciones.size() < 3 && !recomendaciones.contains(r)) {
                    recomendaciones.add(r);
                }
            }
            if (recomendaciones.size() >= 3) break;
        }

        lblRecomendacion1.setText(recomendaciones.size() > 0 ? "• " + recomendaciones.get(0).getDescripcion() : "");
        lblRecomendacion2.setText(recomendaciones.size() > 1 ? "• " + recomendaciones.get(1).getDescripcion() : "");
        lblRecomendacion3.setText(recomendaciones.size() > 2 ? "• " + recomendaciones.get(2).getDescripcion() : "");
    }


    //Metodo que carga el impacto mensual para mostrarlo en la pantalla de inicio
    public void cargarImpactoMensual() {

        HuellaService huellaService = new HuellaService();

        LocalDate hoy = LocalDate.now();
        int mes = hoy.getMonthValue();
        int anio = hoy.getYear();

        List<Huella> huellas = huellaService.getHuellasPorMes(UsuarioActualController.getInstance().getUsuario(), mes, anio);

        ObservableList<Huella> datos = FXCollections.observableArrayList(huellas);


        BigDecimal impactoMensual = BigDecimal.ZERO;
        for (Huella h : huellas) {
            BigDecimal valor = new BigDecimal(h.getValor());
            BigDecimal factor = h.getIdActividad().getIdCategoria().getFactorEmision();
            impactoMensual = impactoMensual.add(valor.multiply(factor));
        }

        lblHuellaMes.setText(String.format("Huella total este mes: %.2f kg CO₂", impactoMensual));
    }

    //Metodo que carga la pantalla de huellas
    public void verHuellas(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaHuellas.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("HuellaCarbono");
            stage.setScene(scene);
            File imagenURL = new File("imagenes/IconoHuella.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);
            Stage owner = (Stage) ((MenuItem) actionEvent.getSource()).getParentPopup().getOwnerWindow();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(owner);

            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Metodo que carga la pantalla de los habitos
    public void verHabitos(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaHabitos.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("HuellaCarbono");
            File imagenURL = new File("imagenes/IconoHuella.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);
            stage.setScene(scene);
            Stage owner = (Stage) ((MenuItem) actionEvent.getSource()).getParentPopup().getOwnerWindow();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(owner);

            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //Metodo que carga la pantalla del impacto ambiental
    public void verImpacto(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaImpacto.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("HuellaCarbono");
            File imagenURL = new File("imagenes/IconoHuella.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);
            stage.setScene(scene);
            Stage owner = (Stage) ((MenuItem) actionEvent.getSource()).getParentPopup().getOwnerWindow();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(owner);

            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //Metodo que cierra la sesion del usuario actual
    public void cerrarSesion(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar cierre de sesión");
        alert.setHeaderText(null);
        alert.setContentText("¿Seguro que quieres cerrar sesión?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            UsuarioActualController.getInstance().setUsuario(null);

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaLogin.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 640, 478);
                Stage stage;
                if (actionEvent.getSource() instanceof MenuItem menuItem) {
                    stage = (Stage) menuItem.getParentPopup().getOwnerWindow();
                } else {
                    stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                }
                stage.setTitle("HuellaCarbono");
                File imagenURL = new File("imagenes/IconoHuella.png");
                Image image = new Image(imagenURL.toURI().toString());
                stage.getIcons().add(image);
                stage.setMaximized(false);
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //Metodo que abre la ventana del perfil del usuario actual
    public void miPerfil(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaMiPerfil.fxml"));
            Scene scene = null;
            scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            Stage owner = (Stage) ((MenuItem) actionEvent.getSource()).getParentPopup().getOwnerWindow();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(owner);
            stage.setTitle("HuellaCarbono");
            File imagenURL = new File("imagenes/IconoHuella.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
