package com.example.EvaluacionUno.service;

import com.example.EvaluacionUno.entity.ReparacionesEntity;
import com.example.EvaluacionUno.entity.VehiculoEntity;
import com.example.EvaluacionUno.repository.ReparacionesRepository;
import com.example.EvaluacionUno.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final ReparacionesRepository reparacionesRepository;

    @Autowired
    public VehiculoService(VehiculoRepository vehiculoRepository, ReparacionesRepository reparacionesRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.reparacionesRepository = reparacionesRepository;
    }

    public VehiculoEntity guardarVehiculo(VehiculoEntity vehiculo){
        vehiculo.setTotalReparaciones(0);
        return vehiculoRepository.save(vehiculo);
    }
    public List<VehiculoEntity> obtenerVehiculos(){
        return vehiculoRepository.findAll();
    }
    public List<VehiculoEntity> obtenerVehiculosNoPagados(){
        List<VehiculoEntity> vehiculosNoPagados = new ArrayList<>();
        List<VehiculoEntity> vehiculos = vehiculoRepository.findAll();
        List<ReparacionesEntity> reparaciones = reparacionesRepository.findAll();
        VehiculoEntity vehiculoEncontrado = null;
        for (VehiculoEntity vehiculo : vehiculos){
            for(ReparacionesEntity reparacion : reparaciones){
                if(Integer.parseInt(vehiculo.getId_vehiculo().toString()) == reparacion.getId_vehiculo()){
                    if(!reparacion.isPagada()){
                        for (VehiculoEntity vh : vehiculosNoPagados) {
                            if (vh.getPatente().equals(vehiculo.getPatente())) {
                                vehiculoEncontrado = vh;
                                break;
                            }
                        }
                        if(vehiculoEncontrado == null){
                            vehiculosNoPagados.add(vehiculo);
                        }
                    }
                }
            }
        }
        return vehiculosNoPagados;
    }
    public Optional<VehiculoEntity> getVehiculobyId(int id){
        return vehiculoRepository.findById((long) id);
    }

    public VehiculoEntity updateVehiculo(VehiculoEntity vehiculo){
        return vehiculoRepository.save(vehiculo);
    }

    public boolean deleteVehiculo(int id) throws Exception {
        try{
            vehiculoRepository.deleteById((long) id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    public VehiculoEntity guardarTotal(int id, int total){
        Optional<VehiculoEntity> vehiculo = vehiculoRepository.findById(Long.parseLong(String.valueOf(id)));
        VehiculoEntity veh = new VehiculoEntity();
        if(vehiculo.isPresent()) {
            veh = vehiculo.get();
            veh.setTotalReparaciones(total);
        }
        return vehiculoRepository.save(veh);
    }

}
