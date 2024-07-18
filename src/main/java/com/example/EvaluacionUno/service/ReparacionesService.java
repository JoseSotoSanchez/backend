package com.example.EvaluacionUno.service;

import com.example.EvaluacionUno.DTO.ReparacionDTO;
import com.example.EvaluacionUno.rDTO.ReparacionesDTO;
import com.example.EvaluacionUno.entity.DescuentosRecargosEntity;
import com.example.EvaluacionUno.entity.ReparacionesEntity;
import com.example.EvaluacionUno.entity.VehiculoEntity;
import com.example.EvaluacionUno.repository.DescuentosRecargosRepository;
import com.example.EvaluacionUno.repository.ReparacionRepository;
import com.example.EvaluacionUno.repository.ReparacionesRepository;
import com.example.EvaluacionUno.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

@Service
public class ReparacionesService {
    private final ReparacionesRepository reparacionesRepository;
    private final ReparacionRepository reparacionRepository;
    private final VehiculoRepository vehiculoRepository;
    private final DescuentosRecargosRepository descuentosRecargosRepository;

    @Autowired
    public ReparacionesService(ReparacionesRepository reparacionesRepository, VehiculoRepository vehiculoRepository, ReparacionRepository reparacionRepository, DescuentosRecargosRepository descuentosRecargosRepository) {
        this.reparacionesRepository = reparacionesRepository;
        this.reparacionRepository = reparacionRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.descuentosRecargosRepository = descuentosRecargosRepository;
    }

    public ReparacionesEntity guardarReparacion(ReparacionesEntity reparacion){
        reparacion.setPagada(false);
        return reparacionesRepository.save(reparacion);
    }

    public List<ReparacionDTO> obtenerReparaciones(){
        List<ReparacionDTO> reparacionDTOs = new ArrayList<>();
        List<ReparacionesEntity> reparacionesEntities = reparacionesRepository.findAll();
        List<VehiculoEntity> vehiculosEntities = vehiculoRepository.findAll();
        for (ReparacionesEntity reparacion : reparacionesEntities){
            for (VehiculoEntity vehiculo : vehiculosEntities){
                if(reparacion.getId_vehiculo() == vehiculo.getId_vehiculo()){
                    ReparacionDTO reparacionDTO = new ReparacionDTO();
                    reparacionDTO.setId(vehiculo.getId_vehiculo());
                    reparacionDTO.setPatente(vehiculo.getPatente());
                    reparacionDTO.setTipoReparacion(reparacion.getTipo_reparacion());
                    reparacionDTO.setFechaIngreso(reparacion.getFecha_ingreso());
                    reparacionDTO.setFechaSalida(reparacion.getFecha_salida());
                    reparacionDTO.setFechaEntregaCliente(reparacion.getFecha_entrega_cliente());
                    reparacionDTO.setIdVehiculo(vehiculo.getId_vehiculo());
                    reparacionDTO.setPagada(reparacion.isPagada());
                    reparacionDTO.setMontoTotal(reparacion.getMonto_total());
                    reparacionDTOs.add(reparacionDTO);
                }
            }
        }
        return reparacionDTOs;
    }

    public List<ReparacionesDTO> obtenerUltimasReparaciones(){
        List<ReparacionesDTO> reparacionesDTOs = new ArrayList<>();
        List<ReparacionesEntity> reparacionesEntities = reparacionesRepository.findAll();
        List<VehiculoEntity> vehiculosEntities = vehiculoRepository.findAll();
        for (ReparacionesEntity reparacion : reparacionesEntities){
            for (VehiculoEntity vehiculo : vehiculosEntities){
                if(reparacion.getId_vehiculo() == vehiculo.getId_vehiculo()){
                    ReparacionesDTO reparacionDTO = new ReparacionesDTO();
                    reparacionDTO.setId(vehiculo.getId_vehiculo());
                    reparacionDTO.setPatente(vehiculo.getPatente());
                    reparacionDTO.setMarca(vehiculo.getMarca());
                    reparacionDTO.setModelo(vehiculo.getModelo());
                    reparacionDTO.setAnio(vehiculo.getAnio_fabricacion());
                    reparacionDTO.setReparacion(reparacion.getTipo_reparacion());
                    reparacionDTO.setFechaReparacion(reparacion.getFecha_salida());
                    reparacionesDTOs.add(reparacionDTO);
                }
            }
        }
        Collections.sort(reparacionesDTOs, new Comparator<ReparacionesDTO>() {
            @Override
            public int compare(ReparacionesDTO o1, ReparacionesDTO o2) {
                return Long.compare(o2.getId(), o1.getId()); // De mayor a menor
            }
        });
        List<ReparacionesDTO> limitedList = reparacionesDTOs.subList(0, Math.min(reparacionesDTOs.size(), 10));
        return limitedList;
    }

    public Optional<ReparacionesEntity> obtenerReparacion(int id){
        return reparacionesRepository.findById((long)id);
    }

    public ReparacionesEntity pagarReparacion(int id){
        ReparacionesEntity rep = new ReparacionesEntity();
        List<ReparacionesEntity> reparaciones = reparacionesRepository.findAll();
        for(ReparacionesEntity reparacion : reparaciones){
            if(reparacion.getId_vehiculo() == id){
                rep = reparacion;
            }
        }
        rep.setPagada(true);
        return reparacionesRepository.save(rep);
    }

    public ReparacionesEntity actualizarReparacion(ReparacionesEntity reparacion){
        return reparacionesRepository.save(reparacion);
    }

    public boolean deleteReparaciones(int id) throws Exception {
        try{
            reparacionesRepository.deleteById((long) id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    public int calculaReparacion(int id){
        int totalReparacion = 0;
        List<VehiculoEntity> vehiculos = vehiculoRepository.findAll();
        VehiculoEntity veh = new VehiculoEntity();
        for (VehiculoEntity vehiculo: vehiculos)  {
            if(vehiculo.getId_vehiculo() == id){
                veh=vehiculo;
            }
        }
        if(veh.getPatente() == null) return 0;
        List<ReparacionesEntity> reparacionesRealizadas = reparacionesRepository.findAll();
        List<ReparacionesEntity> reparacionesRealizadaVehiculo = new ArrayList<>();
        for (ReparacionesEntity rp: reparacionesRealizadas){
            if(rp.getId_vehiculo() == veh.getId_vehiculo()){
                reparacionesRealizadaVehiculo.add(rp);
            }
        }
        int contadorReparaciones = 0;
        int diaSemana =0;
        int hora=0;
        for (ReparacionesEntity rpr: reparacionesRealizadaVehiculo) {
            if (!rpr.isPagada()) {
                totalReparacion = totalReparacion + rpr.getMonto_total();
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Valor total reparación "+ rpr.getTipo_reparacion());
                descuentosRecargos.setValor(rpr.getMonto_total());
                descuentosRecargosRepository.save(descuentosRecargos);
                Date fechaActual = new Date();
                long diferenciaMilisegundos = fechaActual.getTime() - rpr.getFecha_ingreso().getTime();
                int mesesDeAntiguedad = (int) (diferenciaMilisegundos / (1000L * 60 * 60 * 24 * 30)); // Aproximación

                if (mesesDeAntiguedad < 12) {
                    contadorReparaciones +=1;
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(rpr.getFecha_ingreso());
                diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
                hora = calendar.get(Calendar.HOUR_OF_DAY);
            }
        }
        int lastIndex = reparacionesRealizadaVehiculo.size() - 1;
        ReparacionesEntity ultimaReparacion = reparacionesRealizadaVehiculo.get(lastIndex);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ultimaReparacion.getFecha_salida());
        Period periodo = Period.between(ultimaReparacion.getFecha_salida().toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate(), LocalDate.now());

        for (int i = 0; i < periodo.getDays(); i = i + 1){
            totalReparacion = (int) (totalReparacion*1.05);
            DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
            descuentosRecargos.setPatente(veh.getPatente());
            descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
            descuentosRecargos.setTipo("Recargo por retraso en recogida de vehiculo");
            descuentosRecargos.setValor((int) (totalReparacion*0.05));
            descuentosRecargosRepository.save(descuentosRecargos);
        }
        //recargos y descuentos
        if(contadorReparaciones > 0 && contadorReparaciones <= 2) {
            if(veh.getTipo_motor().equals("Gasolina")){
                totalReparacion = (int) (totalReparacion*0.95);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Descuento por Cantidad de reparaciones de 0 a 2, Gasolina");
                descuentosRecargos.setValor((int) (totalReparacion*0.05));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo_motor().equals("Diesel")){
                totalReparacion = (int) (totalReparacion*0.93);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Descuento por Cantidad de reparaciones de 0 a 2, Diesel");
                descuentosRecargos.setValor((int) (totalReparacion*0.07));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo_motor().equals("Hibrido")){
                totalReparacion = (int) (totalReparacion*0.90);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Descuento por Cantidad de reparaciones de 0 a 2, Hibrido");
                descuentosRecargos.setValor((int) (totalReparacion*0.10));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo_motor().equals("Electrico")){
                totalReparacion = (int) (totalReparacion*0.92);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Descuento por Cantidad de reparaciones de 0 a 2, Electrico");
                descuentosRecargos.setValor((int) (totalReparacion*0.08));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
        }
        if(contadorReparaciones >= 3 && contadorReparaciones <= 5) {
            if(veh.getTipo_motor().equals("Gasolina")){
                totalReparacion = (int) (totalReparacion*0.90);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Descuento por Cantidad de reparaciones de 3 a 5, Gasolina");
                descuentosRecargos.setValor((int) (totalReparacion*0.10));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo_motor().equals("Diesel")){
                totalReparacion = (int) (totalReparacion*0.88);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Descuento por Cantidad de reparaciones de 3 a 5, Diesel");
                descuentosRecargos.setValor((int) (totalReparacion*0.12));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo_motor().equals("Hibrido")){
                totalReparacion = (int) (totalReparacion*0.85);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Descuento por Cantidad de reparaciones de 3 a 5, Hibrido");
                descuentosRecargos.setValor((int) (totalReparacion*0.15));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo_motor().equals("Electrico")){
                totalReparacion = (int) (totalReparacion*0.83);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Descuento por Cantidad de reparaciones de 3 a 5, Electrico");
                descuentosRecargos.setValor((int) (totalReparacion*0.17));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
        }
        if(contadorReparaciones >= 6 && contadorReparaciones <= 9) {
            if(veh.getTipo_motor().equals("Gasolina")){
                totalReparacion = (int) (totalReparacion*0.85);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Descuento por Cantidad de reparaciones de 6 a 9, Gasolina");
                descuentosRecargos.setValor((int) (totalReparacion*0.15));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo_motor().equals("Diesel")){
                totalReparacion = (int) (totalReparacion*0.83);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Descuento por Cantidad de reparaciones de 6 a 9, Diesel");
                descuentosRecargos.setValor((int) (totalReparacion*0.17));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo_motor().equals("Hibrido")){
                totalReparacion = (int) (totalReparacion*0.80);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Descuento por Cantidad de reparaciones de 6 a 9, Hibrido");
                descuentosRecargos.setValor((int) (totalReparacion*0.20));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo_motor().equals("Electrico")){
                totalReparacion = (int) (totalReparacion*0.82);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Descuento por Cantidad de reparaciones de 6 a 9, Electrico");
                descuentosRecargos.setValor((int) (totalReparacion*0.18));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
        }
        if(contadorReparaciones >= 10) {
            if(veh.getTipo_motor().equals("Gasolina")){
                totalReparacion = (int) (totalReparacion*0.80);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Descuento por Cantidad de reparaciones mayor que 10, Gasolina");
                descuentosRecargos.setValor((int) (totalReparacion*0.20));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo_motor().equals("Diesel")){
                totalReparacion = (int) (totalReparacion*0.78);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Descuento por Cantidad de reparaciones mayor que 10, Diesel");
                descuentosRecargos.setValor((int) (totalReparacion*0.22));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo_motor().equals("Hibrido")){
                totalReparacion = (int) (totalReparacion*0.75);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Descuento por Cantidad de reparaciones mayor que 10, Hibrido");
                descuentosRecargos.setValor((int) (totalReparacion*0.25));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo_motor().equals("Electrico")){
                totalReparacion = (int) (totalReparacion*0.77);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Descuento por Cantidad de reparaciones mayor que 10, Electrico");
                descuentosRecargos.setValor((int) (totalReparacion*0.23));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
        }

        if ((diaSemana == Calendar.MONDAY || diaSemana == Calendar.THURSDAY) && hora >= 9 && hora < 12) {
            totalReparacion = (int) (totalReparacion*0.90);
            DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
            descuentosRecargos.setPatente(veh.getPatente());
            descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
            descuentosRecargos.setTipo("Descuento por Dia de ingreso a taller Lunes, Jueves entre 9:00 a 12:00");
            descuentosRecargos.setValor((int) (totalReparacion*0.10));
            descuentosRecargosRepository.save(descuentosRecargos);
        }

        //Recargos
        if(veh.getKilometraje() > 5000 && veh.getKilometraje() <= 12000){
            if(veh.getTipo().equals("Sedan")){
                totalReparacion = (int) (totalReparacion*1.03);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por kilometraje entre 5000 a 12000 tipo Sedan");
                descuentosRecargos.setValor((int) (totalReparacion*0.03));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("Hatchback")){
                totalReparacion = (int) (totalReparacion*1.03);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por kilometraje entre 5000 a 12000 tipo Hatchback");
                descuentosRecargos.setValor((int) (totalReparacion*0.03));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("SUV")){
                totalReparacion = (int) (totalReparacion*1.05);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por kilometraje entre 5000 a 12000 tipo SUV");
                descuentosRecargos.setValor((int) (totalReparacion*0.05));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("Pickup")){
                totalReparacion = (int) (totalReparacion*1.05);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por kilometraje entre 5000 a 12000 tipo Pickup");
                descuentosRecargos.setValor((int) (totalReparacion*0.05));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("Furgoneta")){
                totalReparacion = (int) (totalReparacion*1.05);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por kilometraje entre 5000 a 12000 tipo Furgoneta");
                descuentosRecargos.setValor((int) (totalReparacion*0.05));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
        }
        if(veh.getKilometraje() > 12000 && veh.getKilometraje() <= 25000){
            if(veh.getTipo().equals("Sedan")){
                totalReparacion = (int) (totalReparacion*1.07);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por kilometraje entre 12000 a 25000 tipo sedan");
                descuentosRecargos.setValor((int) (totalReparacion*0.07));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("Hatchback")){
                totalReparacion = (int) (totalReparacion*1.07);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por kilometraje entre 12000 a 25000 tipo hatchback");
                descuentosRecargos.setValor((int) (totalReparacion*0.07));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("SUV")){
                totalReparacion = (int) (totalReparacion*1.09);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por kilometraje entre 12000 a 25000 tipo SUV");
                descuentosRecargos.setValor((int) (totalReparacion*0.09));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("Pickup")){
                totalReparacion = (int) (totalReparacion*1.09);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por kilometraje entre 12000 a 25000 tipo Pickup");
                descuentosRecargos.setValor((int) (totalReparacion*0.09));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("Furgoneta")){
                totalReparacion = (int) (totalReparacion*1.09);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por kilometraje entre 12000 a 25000 tipo Furgoneta");
                descuentosRecargos.setValor((int) (totalReparacion*0.09));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
        }
        if(veh.getKilometraje() > 25000 && veh.getKilometraje() <= 40000){
            if(veh.getTipo().equals("Sedan")){
                totalReparacion = (int) (totalReparacion*1.12);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por kilometraje entre 25000 a 40000 tipo Sedan");
                descuentosRecargos.setValor((int) (totalReparacion*0.12));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("Hatchback")){
                totalReparacion = (int) (totalReparacion*1.12);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por kilometraje entre 25000 a 40000 tipo Hatchback");
                descuentosRecargos.setValor((int) (totalReparacion*0.12));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("SUV")){
                totalReparacion = (int) (totalReparacion*1.12);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por kilometraje entre 25000 a 40000 tipo SUV");
                descuentosRecargos.setValor((int) (totalReparacion*0.12));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("Pickup")){
                totalReparacion = (int) (totalReparacion*1.12);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por kilometraje entre 25000 a 40000 tipo Pickup");
                descuentosRecargos.setValor((int) (totalReparacion*0.12));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("Furgoneta")){
                totalReparacion = (int) (totalReparacion*1.12);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por kilometraje entre 25000 a 40000 tipo Furgoneta");
                descuentosRecargos.setValor((int) (totalReparacion*0.12));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
        }
        if(veh.getKilometraje() > 40000){
            if(veh.getTipo().equals("Sedan")){
                totalReparacion = (int) (totalReparacion*1.20);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por kilometraje mayor a 40000 tipo Sedan");
                descuentosRecargos.setValor((int) (totalReparacion*0.20));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("Hatchback")){
                totalReparacion = (int) (totalReparacion*1.20);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por kilometraje mayor a 40000 tipo Hatchback");
                descuentosRecargos.setValor((int) (totalReparacion*0.20));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("SUV")){
                totalReparacion = (int) (totalReparacion*1.20);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por kilometraje mayor a 40000 tipo SUV");
                descuentosRecargos.setValor((int) (totalReparacion*0.20));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("Pickup")){
                totalReparacion = (int) (totalReparacion*1.20);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por kilometraje mayor a 40000 tipo Pickup");
                descuentosRecargos.setValor((int) (totalReparacion*0.20));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("Furgoneta")){
                totalReparacion = (int) (totalReparacion*1.20);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por kilometraje mayor a 40000 tipo Furgoneta");
                descuentosRecargos.setValor((int) (totalReparacion*0.20));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
        }
        LocalDate currentDate = LocalDate.now();
        int anioActual = currentDate.getYear();
        int aniosAntiguedadVehiculo = anioActual - veh.getAnio_fabricacion();

        if(aniosAntiguedadVehiculo > 5 && aniosAntiguedadVehiculo <= 10){
            if(veh.getTipo().equals("Sedan")){
                totalReparacion = (int) (totalReparacion*1.05);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por antiguedad entre 5 a 10 años tipo Sedan");
                descuentosRecargos.setValor((int) (totalReparacion*0.05));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("Hatchback")){
                totalReparacion = (int) (totalReparacion*1.05);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por antiguedad entre 5 a 10 años tipo Hatchback");
                descuentosRecargos.setValor((int) (totalReparacion*0.05));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("SUV")){
                totalReparacion = (int) (totalReparacion*1.07);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por antiguedad entre 5 a 10 años tipo SUV");
                descuentosRecargos.setValor((int) (totalReparacion*0.07));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("Pickup")){
                totalReparacion = (int) (totalReparacion*1.07);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por antiguedad entre 5 a 10 años tipo Pickup");
                descuentosRecargos.setValor((int) (totalReparacion*0.07));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("Furgoneta")){
                totalReparacion = (int) (totalReparacion*1.07);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por antiguedad entre 5 a 10 años tipo Furgoneta");
                descuentosRecargos.setValor((int) (totalReparacion*0.07));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
        }
        if(aniosAntiguedadVehiculo > 10 && aniosAntiguedadVehiculo <= 15){
            if(veh.getTipo().equals("Sedan")){
                totalReparacion = (int) (totalReparacion*1.09);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por antiguedad entre 10 a 15 años tipo Sedan");
                descuentosRecargos.setValor((int) (totalReparacion*0.09));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("Hatchback")){
                totalReparacion = (int) (totalReparacion*1.09);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por antiguedad entre 10 a 15 años tipo Hatchback");
                descuentosRecargos.setValor((int) (totalReparacion*0.09));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("SUV")){
                totalReparacion = (int) (totalReparacion*1.11);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por antiguedad entre 10 a 15 años tipo SUV");
                descuentosRecargos.setValor((int) (totalReparacion*0.11));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("Pickup")){
                totalReparacion = (int) (totalReparacion*1.11);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por antiguedad entre 10 a 15 años tipo Pickup");
                descuentosRecargos.setValor((int) (totalReparacion*0.11));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("Furgoneta")){
                totalReparacion = (int) (totalReparacion*1.11);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por antiguedad entre 10 a 15 años tipo Furgoneta");
                descuentosRecargos.setValor((int) (totalReparacion*0.11));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
        }
        if(aniosAntiguedadVehiculo > 15){
            if(veh.getTipo().equals("Sedan")){
                totalReparacion = (int) (totalReparacion*1.15);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por antiguedad mayor a 15 años tipo Sedan");
                descuentosRecargos.setValor((int) (totalReparacion*0.15));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("Hatchback")){
                totalReparacion = (int) (totalReparacion*1.15);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por antiguedad mayor a 15 años tipo Hatchback");
                descuentosRecargos.setValor((int) (totalReparacion*0.15));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("SUV")){
                totalReparacion = (int) (totalReparacion*1.20);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por antiguedad mayor a 15 años tipo SUV");
                descuentosRecargos.setValor((int) (totalReparacion*0.15));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("Pickup")){
                totalReparacion = (int) (totalReparacion*1.20);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por antiguedad mayor a 15 años tipo Pickup");
                descuentosRecargos.setValor((int) (totalReparacion*0.15));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
            if(veh.getTipo().equals("Furgoneta")){
                totalReparacion = (int) (totalReparacion*1.20);
                DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
                descuentosRecargos.setPatente(veh.getPatente());
                descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
                descuentosRecargos.setTipo("Recargo por antiguedad mayor a 15 años tipo Furgoneta");
                descuentosRecargos.setValor((int) (totalReparacion*0.15));
                descuentosRecargosRepository.save(descuentosRecargos);
            }
        }

        DescuentosRecargosEntity descuentosRecargos = new DescuentosRecargosEntity();
        descuentosRecargos.setPatente(veh.getPatente());
        descuentosRecargos.setId_vehiculo(veh.getId_vehiculo().intValue());
        descuentosRecargos.setTipo("Recargo por IVA");
        descuentosRecargos.setValor((int) (totalReparacion*0.19));
        descuentosRecargosRepository.save(descuentosRecargos);
        return (int) (totalReparacion*1.19);
    }
}
