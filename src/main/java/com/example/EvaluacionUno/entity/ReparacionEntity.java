package com.example.EvaluacionUno.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reparacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReparacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private String tipoCombustible;
    private int precioGasolina;
    private int precioDiesel;
    private int precioHibrido;
    private int precioElectrico;


    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoCombustible() {
        return tipoCombustible;
    }

    public void setTipoCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    public int getPrecioGasolina() {
        return precioGasolina;
    }

    public void setPrecioGasolina(int precioGasolina) {
        this.precioGasolina = precioGasolina;
    }

    public int getPrecioDiesel() {
        return precioDiesel;
    }

    public void setPrecioDiesel(int precioDiesel) {
        this.precioDiesel = precioDiesel;
    }

    public int getPrecioHibrido() {
        return precioHibrido;
    }

    public void setPrecioHibrido(int precioHibrido) {
        this.precioHibrido = precioHibrido;
    }

    public int getPrecioElectrico() {
        return precioElectrico;
    }

    public void setPrecioElectrico(int precioElectrico) {
        this.precioElectrico = precioElectrico;
    }
}
