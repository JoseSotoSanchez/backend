package com.example.EvaluacionUno.controller;

import com.example.EvaluacionUno.entity.ReparacionEntity;
import com.example.EvaluacionUno.entity.VehiculoEntity;
import com.example.EvaluacionUno.service.ReparacionService;
import com.example.EvaluacionUno.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reparacion")
@CrossOrigin("*")
public class ReparacionController {
    @Autowired
    ReparacionService reparacionService;

    @GetMapping
    public List<ReparacionEntity> consultaReparacion() {
        return this.reparacionService.consultaReparacion();
    }
}
