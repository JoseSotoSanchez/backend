package com.example.EvaluacionUno.controller;

import com.example.EvaluacionUno.DTO.ReparacionDTO;
import com.example.EvaluacionUno.rDTO.ReparacionesDTO;
import com.example.EvaluacionUno.entity.ReparacionesEntity;
import com.example.EvaluacionUno.service.ReparacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reparaciones")
@CrossOrigin("*")
public class ReparacionesController {
    @Autowired
    ReparacionesService reparacionesService;

    @PostMapping("guardar")
    public ReparacionesEntity guardar(@RequestBody ReparacionesEntity reparacion) {
        return this.reparacionesService.guardarReparacion(reparacion);
    }

    @GetMapping("calcular/{id}")
    public int calculaReparacion(@PathVariable int id) {
        return this.reparacionesService.calculaReparacion(id);
    }

    @GetMapping("id/{id}")
    public Optional<ReparacionesEntity> obtenerReparacion(@PathVariable int id) {
        return this.reparacionesService.obtenerReparacion(id);
    }

    @GetMapping
    public List<ReparacionDTO> obtenerReparaciones() {
        return this.reparacionesService.obtenerReparaciones();
    }

    @GetMapping("ultimas")
    public List<ReparacionesDTO> obtenerUltimasReparaciones() {
        return this.reparacionesService.obtenerUltimasReparaciones();
    }

    @PutMapping
    public ReparacionesEntity actualizar(@RequestBody ReparacionesEntity reparacion) {
        return this.reparacionesService.actualizarReparacion(reparacion);
    }

    @PutMapping("pagar/{id}")
    public ReparacionesEntity pagar(@PathVariable int id) {
        return this.reparacionesService.pagarReparacion(id);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<Object> deleteReparaciones(@PathVariable int id) throws Exception {
        var isDeleted = reparacionesService.deleteReparaciones(id);
        return ResponseEntity.noContent().build();
    }
}
