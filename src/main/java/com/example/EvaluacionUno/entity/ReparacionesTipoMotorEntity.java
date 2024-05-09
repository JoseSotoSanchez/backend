package com.example.EvaluacionUno.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ReparacionesTipoMotorEntity {
    @Id
    private Long id;
    private String reparacion;
    private int tipo_gasolina;
    private int tipo_diesel;
    private int tipo_hibrido;
    private int tipo_electrico;
    private int total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReparacion() {
        return reparacion;
    }

    public void setReparacion(String reparacion) {
        this.reparacion = reparacion;
    }

    public int getTipo_gasolina() {
        return tipo_gasolina;
    }

    public void setTipo_gasolina(int tipo_gasolina) {
        this.tipo_gasolina = tipo_gasolina;
    }

    public int getTipo_diesel() {
        return tipo_diesel;
    }

    public void setTipo_diesel(int tipo_diesel) {
        this.tipo_diesel = tipo_diesel;
    }

    public int getTipo_hibrido() {
        return tipo_hibrido;
    }

    public void setTipo_hibrido(int tipo_hibrido) {
        this.tipo_hibrido = tipo_hibrido;
    }

    public int getTipo_electrico() {
        return tipo_electrico;
    }

    public void setTipo_electrico(int tipo_electrico) {
        this.tipo_electrico = tipo_electrico;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
