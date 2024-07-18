package com.example.EvaluacionUno.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehiculo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_vehiculo;
    private String patente;
    private String marca;
    private String modelo;
    private String tipo;
    private int anio_fabricacion;
    private String tipo_motor;
    private int numero_asientos;
    private int kilometraje;
    private int totalReparaciones;

    // Getters y setters

    public Long getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(Long id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getAnio_fabricacion() {
        return anio_fabricacion;
    }

    public void setAnio_fabricacion(int anio_fabricacion) {
        this.anio_fabricacion = anio_fabricacion;
    }

    public String getTipo_motor() {
        return tipo_motor;
    }

    public void setTipo_motor(String tipo_motor) {
        this.tipo_motor = tipo_motor;
    }

    public int getNumero_asientos() {
        return numero_asientos;
    }

    public void setNumero_asientos(int numero_asientos) {
        this.numero_asientos = numero_asientos;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    public int getTotalReparaciones() {
        return totalReparaciones;
    }

    public void setTotalReparaciones(int totalReparaciones) {
        this.totalReparaciones = totalReparaciones;
    }

    @Override
    public String toString() {
        return "VehiculoEntity{" +
                "id_vehiculo=" + id_vehiculo +
                ", patente='" + patente + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", anio_fabricacion=" + anio_fabricacion +
                ", tipo_motor='" + tipo_motor + '\'' +
                ", numero_asientos=" + numero_asientos +
                ", kilometraje=" + kilometraje +
                '}';
    }
}

