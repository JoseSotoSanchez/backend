package com.example.EvaluacionUno.DTO;

import java.util.Date;

public class ReparacionDTO {
    private Long id;
    private Long id_vehiculo;
    private String patente;
    private Date fecha_ingreso;
    private String tipo_reparacion;
    private int monto_total;
    private Date fecha_salida;
    private Date fecha_entrega_cliente;
    private boolean pagada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getTipo_reparacion() {
        return tipo_reparacion;
    }

    public void setTipo_reparacion(String tipo_reparacion) {
        this.tipo_reparacion = tipo_reparacion;
    }

    public int getMonto_total() {
        return monto_total;
    }

    public void setMonto_total(int monto_total) {
        this.monto_total = monto_total;
    }

    public Date getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(Date fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public Date getFecha_entrega_cliente() {
        return fecha_entrega_cliente;
    }

    public void setFecha_entrega_cliente(Date fecha_entrega_cliente) {
        this.fecha_entrega_cliente = fecha_entrega_cliente;
    }

    public boolean isPagada() {
        return pagada;
    }

    public void setPagada(boolean pagada) {
        this.pagada = pagada;
    }
}
