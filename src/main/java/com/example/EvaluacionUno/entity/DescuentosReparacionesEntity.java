package com.example.EvaluacionUno.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "descuentos_reparaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DescuentosReparacionesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int numReparaciones;
    private int descGasolina;
    private int descDiesel;
    private int descHibrido;
    private int descElectrico;

    // Getters y setters
    // ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Número de reparaciones
    public int getNumReparaciones() {
        return numReparaciones;
    }

    public void setNumReparaciones(int numReparaciones) {
        this.numReparaciones = numReparaciones;
    }

    // Descuento para vehículos de gasolina
    public int getDescGasolina() {
        return descGasolina;
    }

    public void setDescGasolina(int descGasolina) {
        this.descGasolina = descGasolina;
    }

    // Descuento para vehículos diésel
    public int getDescDiesel() {
        return descDiesel;
    }

    public void setDescDiesel(int descDiesel) {
        this.descDiesel = descDiesel;
    }

    // Descuento para vehículos híbridos
    public int getDescHibrido() {
        return descHibrido;
    }

    public void setDescHibrido(int descHibrido) {
        this.descHibrido = descHibrido;
    }

    // Descuento para vehículos eléctricos
    public int getDescElectrico() {
        return descElectrico;
    }

    public void setDescElectrico(int descElectrico) {
        this.descElectrico = descElectrico;
    }
}
