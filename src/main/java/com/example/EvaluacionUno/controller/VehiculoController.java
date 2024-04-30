package com.example.EvaluacionUno.controller;
import com.example.EvaluacionUno.entity.VehiculoEntity;
import com.example.EvaluacionUno.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/vehiculos")
@CrossOrigin("*")
public class VehiculoController {

    @Autowired
    VehiculoService vehiculoService;

    @PostMapping("guardar")
    public VehiculoEntity guardar(@RequestBody VehiculoEntity vehiculo) {
        return this.vehiculoService.guardarVehiculo(vehiculo);
    }
    @GetMapping
    public List<VehiculoEntity> obtenerVehiculos() {
        return this.vehiculoService.obtenerVehiculos();
    }
    @GetMapping("id/{id}")
    public Optional<VehiculoEntity> getVehiculobyId(@PathVariable int id) {
        return this.vehiculoService.getVehiculobyId(id);
    }

    @PutMapping
    public VehiculoEntity updateVehiculo(@RequestBody VehiculoEntity vehiculo) {
        return this.vehiculoService.updateVehiculo(vehiculo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteVehiculo(@PathVariable int id) throws Exception {
        var isDeleted = vehiculoService.deleteVehiculo(id);
        return ResponseEntity.noContent().build();
    }
}
