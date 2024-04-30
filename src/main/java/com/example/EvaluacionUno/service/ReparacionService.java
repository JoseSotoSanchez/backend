package com.example.EvaluacionUno.service;

import com.example.EvaluacionUno.entity.ReparacionEntity;
import com.example.EvaluacionUno.entity.ReparacionesEntity;
import com.example.EvaluacionUno.repository.ReparacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReparacionService {
    private final ReparacionRepository reparacionRepository;

    @Autowired
    public ReparacionService(ReparacionRepository reparacionRepository) {
        this.reparacionRepository = reparacionRepository;
    }

    public List<ReparacionEntity> consultaReparacion(){
        return reparacionRepository.findAll();
    }
}
