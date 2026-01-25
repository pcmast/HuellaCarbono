package org.example.huelladecarbono.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import org.example.huelladecarbono.HelloApplication;
import org.example.huelladecarbono.model.Actividad;
import org.example.huelladecarbono.model.Huella;
import org.example.huelladecarbono.services.ActividadService;
import org.example.huelladecarbono.services.HuellaService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PantallaHuellasController {


    public TableView<Huella> tablaHuellas;
    public TableColumn<Huella, String> colActividad;
    public TableColumn<Huella, String> colValor;
    public TableColumn<Huella, String> colUnidad;
    public TableColumn<Huella, LocalDate> colFecha;
    private boolean modoEdicion = false;


    public void initialize() {
        for (TableColumn<?, ?> col : tablaHuellas.getColumns()) {
            col.setResizable(false);
        }
        HuellaService huellaService = new HuellaService();
        ActividadService actividadService = new ActividadService();

        List<Huella> huellas = huellaService.getHuellas();
        List<Actividad> actividades = actividadService.getActividades();


        Map<Integer, String> actividadPorId = actividades.stream().collect(Collectors.toMap(Actividad::getId, Actividad::getNombre));

        colActividad.setCellValueFactory(cellData -> {Huella huella = cellData.getValue();String nombre = actividadPorId.get(huella.getIdActividad().getId());return new SimpleStringProperty(nombre);});
        colValor.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValor())));
        colUnidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUnidad()));
        colFecha.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFecha()));
        tablaHuellas.getItems().setAll(huellas);
    }




    public void annadirHuella(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaAnnadirHuella.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("HuellaCarbono");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            Stage owner = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.initOwner(owner);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void actualizarHuella(ActionEvent actionEvent) {
        Huella huellaSeleccionada = tablaHuellas.getSelectionModel().getSelectedItem();

        if (huellaSeleccionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selecciona una huella");
            alert.setHeaderText(null);
            alert.setContentText("Debes seleccionar una huella de la tabla para actualizarla.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaAnnadirHuella.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Actualizar Huella");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            Stage owner = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.initOwner(owner);
            stage.setResizable(false);


            PantallaAnnadirHuellaController controller = fxmlLoader.getController();
            controller.modoActualizar(huellaSeleccionada);

            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void actualizar(ActionEvent actionEvent) {
        HuellaService huellaService = new HuellaService();
        ActividadService actividadService = new ActividadService();

        List<Huella> huellas = huellaService.getHuellas();
        List<Actividad> actividades = actividadService.getActividades();

        Map<Integer, String> actividadPorId = actividades.stream()
                .collect(Collectors.toMap(Actividad::getId, Actividad::getNombre));

        colActividad.setCellValueFactory(cellData -> {Huella huella = cellData.getValue();String nombre = actividadPorId.get(huella.getIdActividad().getId());return new SimpleStringProperty(nombre);});

        colValor.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValor())));
        colUnidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUnidad()));
        colFecha.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFecha()));

        tablaHuellas.getItems().setAll(huellas);
    }


    public void eliminarHuella(ActionEvent actionEvent) {
        Huella huellaSeleccionada = tablaHuellas.getSelectionModel().getSelectedItem();
        if (huellaSeleccionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Eliminar Huella");
            alert.setTitle("Selecciona una huella");
            alert.setHeaderText(null);
            alert.setContentText("Debes seleccionar una huella de la tabla para eliminar.");
            alert.showAndWait();
            return;
        }
        boolean eliminar = false;
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmar eliminación");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("¿Estás seguro de que quieres eliminar esta huella?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            HuellaService huellaService = new HuellaService();
            huellaService.eliminarHuella(huellaSeleccionada);
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle("Huella eliminada");
            infoAlert.setHeaderText(null);
            infoAlert.setContentText("La huella se eliminó correctamente.");
            infoAlert.showAndWait();
        }


    }


}
