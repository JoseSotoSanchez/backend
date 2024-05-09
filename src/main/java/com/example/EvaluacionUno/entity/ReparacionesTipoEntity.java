package com.example.EvaluacionUno.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
public class ReparacionesTipoEntity {
    @Id
    private Long id;
    private String reparacion;
    private int tipo_sedan;
    private int tipo_hatchback;
    private int tipo_suv;
    private int tipo_pickup;
    private int tipo_furgoneta;
    private int total;

    public String getReparacion() {
        return reparacion;
    }

    public void setReparacion(String reparacion) {
        this.reparacion = reparacion;
    }

    public int getTipo_sedan() {
        return tipo_sedan;
    }

    public void setTipo_sedan(int tipo_sedan) {
        this.tipo_sedan = tipo_sedan;
    }

    public int getTipo_hatchback() {
        return tipo_hatchback;
    }

    public void setTipo_hatchback(int tipo_hatchback) {
        this.tipo_hatchback = tipo_hatchback;
    }

    public int getTipo_suv() {
        return tipo_suv;
    }

    public void setTipo_suv(int tipo_suv) {
        this.tipo_suv = tipo_suv;
    }

    public int getTipo_pickup() {
        return tipo_pickup;
    }

    public void setTipo_pickup(int tipo_pickup) {
        this.tipo_pickup = tipo_pickup;
    }

    public int getTipo_furgoneta() {
        return tipo_furgoneta;
    }

    public void setTipo_furgoneta(int tipo_furgoneta) {
        this.tipo_furgoneta = tipo_furgoneta;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
