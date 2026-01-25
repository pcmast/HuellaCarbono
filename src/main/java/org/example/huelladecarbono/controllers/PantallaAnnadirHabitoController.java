package org.example.huelladecarbono.controllers;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.huelladecarbono.model.Actividad;
import org.example.huelladecarbono.model.Habito;
import org.example.huelladecarbono.model.HabitoId;
import org.example.huelladecarbono.model.Huella;
import org.example.huelladecarbono.services.ActividadService;
import org.example.huelladecarbono.services.HabitoService;
import org.example.huelladecarbono.services.HuellaService;

import java.util.List;

public class PantallaAnnadirHabitoController {

    public DatePicker textFecha;
    public ComboBox<Actividad> actividades;
    public Label datosIntroducidos;
    public Button botonActualizar;
    public Button botonAnnadir;
    public TextField textFrecuencia;
    public ComboBox<String> tipo;

    private Habito habitoActual;

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
        tipo.getItems().addAll("DIARIO", "SEMANAL", "MENSUAL");
    }

    public void modoActualizar(Habito habito) {
        this.habitoActual = habito;

        textFrecuencia.setText(habitoActual.getFrecuencia().toString());
        textFecha.setValue(habito.getUltimaFecha());

        Actividad actividadReal = actividades.getItems().stream().filter(a -> a.getId() == habito.getIdActividad().getId()).findFirst().orElse(null);

        actividades.setValue(actividadReal);
        tipo.setValue(habito.getTipo());

        botonActualizar.setDisable(false);
        botonActualizar.setVisible(true);
        botonAnnadir.setVisible(false);
        botonAnnadir.setDisable(true);
    }

    public void annadirHabito(MouseEvent mouseEvent) {
        HabitoService habitoService = new HabitoService();
        Habito habito = new Habito();

        if (!textFrecuencia.getText().isEmpty() && tipo.getValue() != null && textFecha.getValue() != null && actividades.getValue() != null) {

            datosIntroducidos.setText("");
            HabitoId id = new HabitoId();
            id.setIdUsuario(UsuarioActualController.getInstance().getUsuario().getId());
            id.setIdActividad(actividades.getValue().getId());
            habito.setId(id);

            habito.setIdUsuario(UsuarioActualController.getInstance().getUsuario());
            habito.setIdActividad(actividades.getValue());
            habito.setFrecuencia(Integer.parseInt(textFrecuencia.getText()));
            habito.setTipo(tipo.getValue());
            habito.setUltimaFecha(textFecha.getValue());

            habitoService.addHabito(habito);

            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.close();

        } else {
            datosIntroducidos.setText("Introduce todos los datos");
        }
    }

    public void actualizarHabito(MouseEvent mouseEvent) {
        if (habitoActual == null) {
            return;
        }

        habitoActual.setFrecuencia(Integer.parseInt(textFrecuencia.getText()));
        habitoActual.setTipo(tipo.getValue());
        habitoActual.setUltimaFecha(textFecha.getValue());
        habitoActual.setIdActividad(actividades.getValue());

        HabitoService habitoService = new HabitoService();
        habitoService.actualizarHabito(habitoActual);

        ((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow()).close();
    }


}
