package com.example.EvaluacionUno.controller;

import com.example.EvaluacionUno.entity.DescuentosRecargosEntity;
import com.example.EvaluacionUno.entity.ReparacionEntity;
import com.example.EvaluacionUno.service.ReparacionService;
import com.example.EvaluacionUno.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reporte")
@CrossOrigin("*")
public class ReporteController {
    @Autowired
    ReporteService reporteService;

    @GetMapping("valores")
    public List<DescuentosRecargosEntity> consultaValores() {
        return this.reporteService.consultaValores();
    }
}
