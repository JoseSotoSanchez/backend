package com.example.EvaluacionUno.service;

import com.example.EvaluacionUno.entity.*;
import com.example.EvaluacionUno.repository.DescuentosRecargosRepository;
import com.example.EvaluacionUno.repository.ReparacionRepository;
import com.example.EvaluacionUno.repository.ReparacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ReporteService {
    private final DescuentosRecargosRepository desuentosRecargosRepository;
    private final ReparacionService reparacionService;
    private final ReparacionesService reparacionesService;
    private final ReparacionesRepository reparacionesRepository;
    private final VehiculoService vehiculoService;

    @Autowired
    public ReporteService(DescuentosRecargosRepository desuentosRecargosRepository,
                          ReparacionService reparacionService,
                          ReparacionesService reparacionesService,
                          VehiculoService vehiculoService,
                          ReparacionesRepository reparacionesRepository) {
        this.desuentosRecargosRepository = desuentosRecargosRepository;
        this.reparacionService = reparacionService;
        this.reparacionesService = reparacionesService;
        this.reparacionesRepository = reparacionesRepository;
        this.vehiculoService = vehiculoService;
    }

    public List<DescuentosRecargosEntity> consultaValores(){
        return desuentosRecargosRepository.findAll();
    }

    public List<ReparacionesTipoEntity> reparacionesTipo(){
        List<ReparacionesTipoEntity> lista = new ArrayList<>();
        var reparaciones = reparacionService.consultaReparacion();
        for (ReparacionEntity reparacion : reparaciones){
            ReparacionesTipoEntity reptip = new ReparacionesTipoEntity();
            reptip.setReparacion(reparacion.getNombre());
            reptip.setTipo_furgoneta(0);
            reptip.setTipo_hatchback(0);
            reptip.setTipo_pickup(0);
            reptip.setTipo_sedan(0);
            reptip.setTipo_suv(0);
            reptip.setTotal(0);
            lista.add(reptip);
        }

        var reparacionesall = reparacionesRepository.findAll();
        for (ReparacionesEntity reparacion1 : reparacionesall){
            Optional<VehiculoEntity> vehiculo = vehiculoService.getVehiculobyId(reparacion1.getId_vehiculo());
            if(vehiculo.isPresent()){
                VehiculoEntity veh = vehiculo.get();
                for (ReparacionesTipoEntity rep:lista){
                    if(rep.getReparacion().equals(reparacion1.getTipo_reparacion())){
                        if(veh.getTipo().equals("Sedan")){
                            rep.setTipo_sedan(rep.getTipo_sedan()+1);
                        }
                        if(veh.getTipo().equals("SUV")){
                            rep.setTipo_suv(rep.getTipo_suv()+1);
                        }
                        if(veh.getTipo().equals("Furgoneta")){
                            rep.setTipo_furgoneta(rep.getTipo_furgoneta()+1);
                        }
                        if(veh.getTipo().equals("Pickup")){
                            rep.setTipo_pickup(rep.getTipo_pickup()+1);
                        }
                        if(veh.getTipo().equals("Hatchback")){
                            rep.setTipo_hatchback(rep.getTipo_hatchback()+1);
                        }
                        rep.setTotal(rep.getTotal() + veh.getTotalReparaciones());
                    }
                }
            }
        }
        return lista;
    }

    public List<TiempoPromedioEntity> tiempoPromedio(){
        List<TiempoPromedioEntity> lista = new ArrayList<>();
        var reparaciones = reparacionesRepository.findAll();
        for(ReparacionesEntity reparacion : reparaciones){
            Optional<VehiculoEntity> vehiculo = vehiculoService.getVehiculobyId(reparacion.getId_vehiculo());
            if(vehiculo.isPresent()) {
                VehiculoEntity veh = vehiculo.get();
                if (lista.isEmpty()) {
                    TiempoPromedioEntity tmprepo = new TiempoPromedioEntity();
                    tmprepo.setMarca(veh.getMarca());
                    long diferenciaEnMilisegundos = reparacion.getFecha_salida().getTime() - reparacion.getFecha_ingreso().getTime();
                    long diferenciaEnHoras = TimeUnit.MILLISECONDS.toHours(diferenciaEnMilisegundos);
                    tmprepo.setTiempoPromedio(Math.abs(diferenciaEnHoras));
                    lista.add(tmprepo);
                }
                else{
                    for(TiempoPromedioEntity repo: lista){
                        if(repo.getMarca().equals(veh.getMarca())){
                            long diferenciaEnMilisegundos = reparacion.getFecha_salida().getTime() - reparacion.getFecha_ingreso().getTime();
                            long diferenciaEnHoras = TimeUnit.MILLISECONDS.toHours(Math.abs(diferenciaEnMilisegundos));
                            double diferencia = Math.abs(diferenciaEnHoras) + Math.abs(repo.getTiempoPromedio());
                            repo.setTiempoPromedio(diferencia / 2.0);
                        }else{
                            TiempoPromedioEntity tmprepo = new TiempoPromedioEntity();
                            tmprepo.setMarca(veh.getMarca());
                            long diferenciaEnMilisegundos = reparacion.getFecha_salida().getTime() - reparacion.getFecha_ingreso().getTime();
                            long diferenciaEnHoras = TimeUnit.MILLISECONDS.toHours(Math.abs(diferenciaEnMilisegundos));
                            tmprepo.setTiempoPromedio(Math.abs(diferenciaEnHoras) / 2.0);
                            lista.add(tmprepo);
                        }
                    }

                }
            }
        }
        return lista;
    }

    public List<ReparacionesTipoMotorEntity> reparacionesTipoMotor(){
        List<ReparacionesTipoMotorEntity> lista = new ArrayList<>();
        var reparaciones = reparacionService.consultaReparacion();
        for (ReparacionEntity reparacion : reparaciones){
            ReparacionesTipoMotorEntity reptip = new ReparacionesTipoMotorEntity();
            reptip.setReparacion(reparacion.getNombre());
            reptip.setTipo_gasolina(0);
            reptip.setTipo_diesel(0);
            reptip.setTipo_hibrido(0);
            reptip.setTipo_electrico(0);
            reptip.setTotal(0);
            lista.add(reptip);
        }

        var reparacionesall = reparacionesRepository.findAll();
        for (ReparacionesEntity reparacion1 : reparacionesall){
            Optional<VehiculoEntity> vehiculo = vehiculoService.getVehiculobyId(reparacion1.getId_vehiculo());
            if(vehiculo.isPresent()){
                VehiculoEntity veh = vehiculo.get();
                for (ReparacionesTipoMotorEntity rep:lista){
                    if(rep.getReparacion().equals(reparacion1.getTipo_reparacion())){
                        if(veh.getTipo_motor().equals("Gasolina")){
                            rep.setTipo_gasolina(rep.getTipo_gasolina()+1);
                        }
                        if(veh.getTipo_motor().equals("Diesel")){
                            rep.setTipo_diesel(rep.getTipo_diesel()+1);
                        }
                        if(veh.getTipo_motor().equals("Hibrido")){
                            rep.setTipo_hibrido(rep.getTipo_hibrido()+1);
                        }
                        if(veh.getTipo_motor().equals("Electrico")){
                            rep.setTipo_electrico(rep.getTipo_electrico()+1);
                        }
                        rep.setTotal(rep.getTotal() + reparacion1.getMonto_total());
                    }
                }
            }
        }
        return lista;
    }
}
