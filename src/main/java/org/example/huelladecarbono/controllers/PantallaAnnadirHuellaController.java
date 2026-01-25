package org.example.huelladecarbono.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.huelladecarbono.DAO.ActividadesDAO;
import org.example.huelladecarbono.model.Actividad;
import org.example.huelladecarbono.model.Huella;
import org.example.huelladecarbono.services.ActividadService;
import org.example.huelladecarbono.services.HuellaService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PantallaAnnadirHuellaController {
    public TextField textValor;
    public TextField textUnidad;
    public DatePicker textFecha;
    public ComboBox<Actividad> actividades;
    public Label datosIntroducidos;
    public Button botonActualizar;
    public Button botonAnnadir;

    private Huella huellaActual;

    public void initialize() {
        botonActualizar.setDisable(true);
        botonActualizar.setVisible(false);
        botonAnnadir.setVisible(true);
        ComboBox<Actividad> actividadesCombo = actividades;

        ActividadService actividadService = new ActividadService();
        List<Actividad> list = actividadService.getActividades();
        actividadesCombo.getItems().addAll(list);

        actividadesCombo.setCellFactory(cb -> new javafx.scene.control.ListCell<Actividad>() {
            @Override
            protected void updateItem(Actividad item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNombre());
            }
        });

        actividadesCombo.setButtonCell(new javafx.scene.control.ListCell<Actividad>() {
            @Override
            protected void updateItem(Actividad item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNombre());
            }
        });
    }

    public void modoActualizar(Huella huella) {
        this.huellaActual = huella;

        textValor.setText(huella.getValor());
        textUnidad.setText(huella.getUnidad());
        textFecha.setValue(huella.getFecha());


        Actividad actividadReal = actividades.getItems().stream().filter(a -> a.getId() == huella.getIdActividad().getId()).findFirst().orElse(null);
        actividades.setValue(actividadReal);

        botonActualizar.setDisable(false);
        botonActualizar.setVisible(true);
        botonAnnadir.setVisible(false);
        botonAnnadir.setDisable(true);
    }

    public void annadirHuella(MouseEvent mouseEvent) {
        HuellaService huellaService = new HuellaService();
        Huella huella = new Huella();
        if (!textValor.getText().equals("") || !textUnidad.getText().equals("") || textFecha.getValue() != null || actividades.getValue() != null) {
            datosIntroducidos.setText("");
            huella.setIdUsuario(UsuarioActualController.getInstance().getUsuario());
            huella.setIdActividad(actividades.getValue());
            huella.setUnidad(textUnidad.getText());
            huella.setValor(textValor.getText());
            huella.setFecha(textFecha.getValue());
            huellaService.addHuella(huella);
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.close();

        } else {
            datosIntroducidos.setText("Introduce todos los datos");
        }
    }

    public void actualizarHuella(MouseEvent mouseEvent) {
        if (huellaActual == null) {

        } else {
            huellaActual.setValor(textValor.getText());
            huellaActual.setUnidad(textUnidad.getText());
            huellaActual.setFecha(textFecha.getValue());
            huellaActual.setIdActividad(actividades.getValue());

            HuellaService huellaService = new HuellaService();
            huellaService.actualizarHuella(huellaActual);

            ((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow()).close();
        }
    }
}
