package com.example.EvaluacionUno.repository;

import com.example.EvaluacionUno.entity.ReparacionesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReparacionesRepository extends  JpaRepository<ReparacionesEntity, Long> {
}