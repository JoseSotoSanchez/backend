package com.example.EvaluacionUno.DTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public class ReparacionesDTO {
    private Long id;
    private String patente;
    private String marca;
    private String modelo;
    private int anio;
    private String reparacion;
    private Date fecha_reparacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getReparacion() {
        return reparacion;
    }

    public void setReparacion(String reparacion) {
        this.reparacion = reparacion;
    }

    public Date getFecha_reparacion() {
        return fecha_reparacion;
    }

    public void setFecha_reparacion(Date fecha_reparacion) {
        this.fecha_reparacion = fecha_reparacion;
    }
}