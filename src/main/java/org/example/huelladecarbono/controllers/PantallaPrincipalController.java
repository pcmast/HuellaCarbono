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
import org.example.huelladecarbono.model.Huella;
import org.example.huelladecarbono.model.Recomendacion;
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


    public void initialize() {
        lblUsuario.setText("Bienvenido, " + UsuarioActualController.getInstance().getUsuario().getNombre());
        cargarImpactoMensual();
        cargarRecomendaciones();
    }

    public void cargarRecomendaciones() {

        HuellaService huellaService = new HuellaService();
        RecomendacionService recomendacionService = new RecomendacionService();
        LocalDate hoy = LocalDate.now();
        int mes = hoy.getMonthValue();
        int anio = hoy.getYear();

        List<Huella> huellas = huellaService.getHuellasPorMes(
                UsuarioActualController.getInstance().getUsuario(), mes, anio);

        if (huellas.isEmpty()) {
            lblRecomendacion1.setText("No hay recomendaciones disponibles");
            lblRecomendacion2.setText("");
            lblRecomendacion3.setText("");
            return;
        }

        Map<Categoria, Long> frecuenciaPorCategoria = new HashMap<>();
        for (Huella h : huellas) {
            Categoria cat = h.getIdActividad().getIdCategoria();
            frecuenciaPorCategoria.put(cat, frecuenciaPorCategoria.getOrDefault(cat, 0L) + 1);
        }

        long maxHuellas = 0;
        for (Long count : frecuenciaPorCategoria.values()) {
            if (count > maxHuellas) maxHuellas = count;
        }

        List<Categoria> categoriasTop = new ArrayList<>();
        for (Map.Entry<Categoria, Long> entry : frecuenciaPorCategoria.entrySet()) {
            if (entry.getValue() == maxHuellas) categoriasTop.add(entry.getKey());
        }

        List<Recomendacion> recomendacionesFinales = new ArrayList<>();

        for (Categoria cat : categoriasTop) {
            List<Recomendacion> recs = recomendacionService.getRecomendacionesPorCategoria(cat);
            for (Recomendacion r : recs) {
                if (recomendacionesFinales.size() < 3 && !recomendacionesFinales.contains(r)) {
                    recomendacionesFinales.add(r);
                }
            }
        }

        if (recomendacionesFinales.size() < 3) {
            List<Categoria> otrasCategorias = new ArrayList<>(frecuenciaPorCategoria.keySet());
            otrasCategorias.removeAll(categoriasTop);

            for (Categoria cat : otrasCategorias) {
                List<Recomendacion> recs = recomendacionService.getRecomendacionesPorCategoria(cat);
                for (Recomendacion r : recs) {
                    if (recomendacionesFinales.size() < 3 && !recomendacionesFinales.contains(r)) {
                        recomendacionesFinales.add(r);
                    }
                }
                if (recomendacionesFinales.size() >= 3) break;
            }
        }

        lblRecomendacion1.setText(recomendacionesFinales.size() > 0 ? "• " + recomendacionesFinales.get(0).getDescripcion() : "");
        lblRecomendacion2.setText(recomendacionesFinales.size() > 1 ? "• " + recomendacionesFinales.get(1).getDescripcion() : "");
        lblRecomendacion3.setText(recomendacionesFinales.size() > 2 ? "• " + recomendacionesFinales.get(2).getDescripcion() : "");
    }



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


    public void verHuellas(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaHuellas.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("HuellaCarbono");
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

    public void verHabitos(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaHabitos.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("HuellaCarbono");
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

    public void verImpacto(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaImpacto.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("HuellaCarbono");
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

    public void verRecomendaciones(ActionEvent actionEvent) {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaImpacto.fxml"));
//            Scene scene = new Scene(fxmlLoader.load());
//            Stage stage = new Stage();
//            stage.setTitle("HuellaCarbono");
//            stage.setScene(scene);
//            Stage owner = (Stage) ((MenuItem) actionEvent.getSource()).getParentPopup().getOwnerWindow();
//            stage.initModality(Modality.WINDOW_MODAL);
//            stage.initOwner(owner);
//
//            stage.setResizable(false);
//            stage.show();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

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
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
