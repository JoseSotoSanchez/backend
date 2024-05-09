package com.example.EvaluacionUno.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TiempoPromedioEntity {
    @Id
    private Long id;
    private String marca;
    private double tiempoPromedio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getTiempoPromedio() {
        return tiempoPromedio;
    }

    public void setTiempoPromedio(double tiempoPromedio) {
        this.tiempoPromedio = tiempoPromedio;
    }
}
