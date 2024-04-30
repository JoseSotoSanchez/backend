package com.example.EvaluacionUno.repository;

import com.example.EvaluacionUno.entity.BonosAplicadosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonosAplicadosRepository extends JpaRepository<BonosAplicadosEntity, Long> {
}
