package com.example.EvaluacionUno.service;

import com.example.EvaluacionUno.entity.VehiculoEntity;
import com.example.EvaluacionUno.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    @Autowired
    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    public VehiculoEntity guardarVehiculo(VehiculoEntity vehiculo){
        return vehiculoRepository.save(vehiculo);
    }
    public List<VehiculoEntity> obtenerVehiculos(){
        return vehiculoRepository.findAll();
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

}
