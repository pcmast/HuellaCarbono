package org.example.huelladecarbono.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.huelladecarbono.HelloApplication;
import org.example.huelladecarbono.model.Actividad;
import org.example.huelladecarbono.model.Habito;
import org.example.huelladecarbono.model.Huella;
import org.example.huelladecarbono.services.ActividadService;
import org.example.huelladecarbono.services.HabitoService;
import org.example.huelladecarbono.services.HuellaService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PantallaHabitoController {
    public TableView<Habito> tablaHabito;
    public TableColumn<Habito, String> colActividad;
    public TableColumn<Habito, String> colValor;
    public TableColumn<Habito, String> colUnidad;
    public TableColumn<Habito, LocalDate> colFecha;
    private boolean modoEdicion = false;

    public void initialize() {
        for (TableColumn<?, ?> col : tablaHabito.getColumns()) {
            col.setResizable(false);
        }
        HabitoService habitoService = new HabitoService();
        ActividadService actividadService = new ActividadService();

        List<Habito> habitos = habitoService.getHabitosPorUsuario(UsuarioActualController.getInstance().getUsuario());
        List<Actividad> actividades = actividadService.getActividades();


        Map<Integer, String> actividadPorId = actividades.stream().collect(Collectors.toMap(Actividad::getId, Actividad::getNombre));

        colActividad.setCellValueFactory(cellData -> {
            Habito habito = cellData.getValue();
            String nombre = actividadPorId.get(habito.getIdActividad().getId());
            return new SimpleStringProperty(nombre);
        });
        colValor.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFrecuencia())));
        colUnidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo()));
        colFecha.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getUltimaFecha()));
        tablaHabito.getItems().setAll(habitos);
    }


    public void annadirHabito(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaAnnadirHabito.fxml"));
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

    public void actualizarHabito(ActionEvent actionEvent) {
        Habito habitoSeleccionado = tablaHabito.getSelectionModel().getSelectedItem();

        if (habitoSeleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selecciona un habito");
            alert.setHeaderText(null);
            alert.setContentText("Debes seleccionar un habito de la tabla para actualizarla.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaAnnadirHabito.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Actualizar Habito");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            Stage owner = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.initOwner(owner);
            stage.setResizable(false);


            PantallaAnnadirHabitoController controller = fxmlLoader.getController();
            controller.modoActualizar(habitoSeleccionado);

            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarHabito(ActionEvent actionEvent) {
        Habito habitoSeleccionado = tablaHabito.getSelectionModel().getSelectedItem();
        if (habitoSeleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Eliminar Habito");
            alert.setTitle("Selecciona una habito");
            alert.setHeaderText(null);
            alert.setContentText("Debes seleccionar una habito de la tabla para eliminar.");
            alert.showAndWait();
            return;
        }
        boolean eliminar = false;
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmar eliminación");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("¿Estás seguro de que quieres eliminar este habito?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            HabitoService habitoService = new HabitoService();
            habitoService.eliminarHabito(habitoSeleccionado);
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle("Habito eliminada");
            infoAlert.setHeaderText(null);
            infoAlert.setContentText("La habito se eliminó correctamente.");
            infoAlert.showAndWait();
        }
    }

    public void actualizarTabla(ActionEvent actionEvent) {
        HabitoService habitoService = new HabitoService();
        ActividadService actividadService = new ActividadService();

        List<Habito> habitos = habitoService.getHabitosPorUsuario(UsuarioActualController.getInstance().getUsuario());
        List<Actividad> actividades = actividadService.getActividades();

        Map<Integer, String> actividadPorId = actividades.stream().collect(Collectors.toMap(Actividad::getId, Actividad::getNombre));

        colActividad.setCellValueFactory(cellData -> {Habito habito = cellData.getValue();String nombre = actividadPorId.get(habito.getIdActividad().getId());return new SimpleStringProperty(nombre);});
        colValor.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFrecuencia())));
        colUnidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo()));
        colFecha.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getUltimaFecha()));

        tablaHabito.getItems().setAll(habitos);

    }
}
