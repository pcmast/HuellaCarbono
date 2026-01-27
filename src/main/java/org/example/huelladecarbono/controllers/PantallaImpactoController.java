package org.example.huelladecarbono.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.huelladecarbono.DAO.HuellaDAO;
import org.example.huelladecarbono.model.Huella;
import org.example.huelladecarbono.services.HuellaService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class PantallaImpactoController {

    @FXML
    private ComboBox<String> filtroImpacto;
    @FXML
    private Label lblImpactoTotal;

    @FXML
    private TableView<Huella> tablaImpacto;

    @FXML
    private TableColumn<Huella, String> colFecha;

    @FXML
    private TableColumn<Huella, String> colActividad;

    @FXML
    private TableColumn<Huella, String> colCategoria;

    @FXML
    private TableColumn<Huella, String> colValor;

    @FXML
    private TableColumn<Huella, String> colImpacto;


    public void initialize() {
        configurarColumnas();
        cargarImpactoMensual();
        filtroImpacto.getItems().addAll("DIARIO", "MENSUAL", "ANUAL");
        filtroImpacto.setOnAction(e -> {
            String seleccion = filtroImpacto.getValue();

            switch (seleccion) {
                case "DIARIO":
                    cargarImpactoDiario();
                    break;
                case "MENSUAL":
                    cargarImpactoMensual();
                    break;
                case "ANUAL":
                    cargarImpactoAnual();
                    break;
            }
        });
    }

    private void configurarColumnas() {
        colFecha.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFecha().toString()));
        colActividad.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdActividad().getNombre()));
        colCategoria.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdActividad().getIdCategoria().getNombre()));
        colValor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValor()));
        colImpacto.setCellValueFactory(data -> {
            Huella h = data.getValue();
            BigDecimal valor = new BigDecimal(h.getValor());
            BigDecimal factor = h.getIdActividad().getIdCategoria().getFactorEmision();
            BigDecimal impacto = valor.multiply(factor).setScale(2, RoundingMode.HALF_UP);

            return new SimpleStringProperty(impacto.toString());
        });
    }

    private void cargarImpactoMensual() {

        HuellaService huellaService =  new HuellaService();

        LocalDate hoy = LocalDate.now();
        int mes = hoy.getMonthValue();
        int anio = hoy.getYear();

        List<Huella> huellas = huellaService.getHuellasPorMes(UsuarioActualController.getInstance().getUsuario(), mes, anio);

        ObservableList<Huella> datos = FXCollections.observableArrayList(huellas);
        tablaImpacto.setItems(datos);

        BigDecimal impactoMensual = BigDecimal.ZERO;
        for (Huella h : huellas) {
            BigDecimal valor = new BigDecimal(h.getValor());
            BigDecimal factor = h.getIdActividad().getIdCategoria().getFactorEmision();
            impactoMensual = impactoMensual.add(valor.multiply(factor));
        }

        lblImpactoTotal.setText(String.format("Impacto total del mes: %.2f kg CO₂", impactoMensual));
    }

    private void cargarImpactoDiario() {

        HuellaService huellaService =  new HuellaService();
        LocalDate hoy = LocalDate.now();

        List<Huella> huellas = huellaService.getHuellasPorDia(UsuarioActualController.getInstance().getUsuario(), hoy);

        ObservableList<Huella> datos = FXCollections.observableArrayList(huellas);
        tablaImpacto.setItems(datos);

        BigDecimal impactoDiario = BigDecimal.ZERO;
        for (Huella h : huellas) {
            BigDecimal valor = new BigDecimal(h.getValor());
            BigDecimal factor = h.getIdActividad()
                    .getIdCategoria()
                    .getFactorEmision();
            impactoDiario = impactoDiario.add(valor.multiply(factor));
        }

        impactoDiario = impactoDiario.setScale(2, RoundingMode.HALF_UP);
        lblImpactoTotal.setText(String.format("Impacto total de hoy: %.2f kg CO₂", impactoDiario.doubleValue()));
    }
    private void cargarImpactoAnual() {

        HuellaService huellaService =  new HuellaService();
        int anio = LocalDate.now().getYear();

        List<Huella> huellas = huellaService.getHuellasAnio(UsuarioActualController.getInstance().getUsuario(), anio);

        ObservableList<Huella> datos = FXCollections.observableArrayList(huellas);
        tablaImpacto.setItems(datos);

        BigDecimal impactoAnual = BigDecimal.ZERO;
        for (Huella h : huellas) {
            BigDecimal valor = new BigDecimal(h.getValor());
            BigDecimal factor = h.getIdActividad()
                    .getIdCategoria()
                    .getFactorEmision();
            impactoAnual = impactoAnual.add(valor.multiply(factor));
        }
        impactoAnual = impactoAnual.setScale(2, RoundingMode.HALF_UP);
        lblImpactoTotal.setText(String.format("Impacto total del año: %.2f kg CO₂", impactoAnual.doubleValue()));
    }


}
