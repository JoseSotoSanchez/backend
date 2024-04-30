package com.example.EvaluacionUno.service;

import com.example.EvaluacionUno.entity.DescuentosRecargosEntity;
import com.example.EvaluacionUno.entity.ReparacionEntity;
import com.example.EvaluacionUno.repository.DescuentosRecargosRepository;
import com.example.EvaluacionUno.repository.ReparacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteService {
    private final DescuentosRecargosRepository desuentosRecargosRepository;

    @Autowired
    public ReporteService(DescuentosRecargosRepository desuentosRecargosRepository) {
        this.desuentosRecargosRepository = desuentosRecargosRepository;
    }

    public List<DescuentosRecargosEntity> consultaValores(){
        return desuentosRecargosRepository.findAll();
    }
}
