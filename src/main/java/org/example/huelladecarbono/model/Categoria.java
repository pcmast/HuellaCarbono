package org.example.huelladecarbono.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "categoria", schema = "huella_carbono")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "factor_emision", nullable = false, precision = 10, scale = 4)
    private BigDecimal factorEmision;

    @Column(name = "unidad", nullable = false, length = 20)
    private String unidad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getFactorEmision() {
        return factorEmision;
    }

    public void setFactorEmision(BigDecimal factorEmision) {
        this.factorEmision = factorEmision;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

}