package com.example.EvaluacionUno.repository;

import com.example.EvaluacionUno.entity.VehiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepository extends  JpaRepository<VehiculoEntity, Long> {
}
