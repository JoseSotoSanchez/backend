package com.example.EvaluacionUno.repository;

import com.example.EvaluacionUno.entity.DescuentosReparacionesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescuentosReparacionesRepository extends  JpaRepository<DescuentosReparacionesEntity, Long> {
}
