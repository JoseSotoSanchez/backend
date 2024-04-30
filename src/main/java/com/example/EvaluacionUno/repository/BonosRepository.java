package com.example.EvaluacionUno.repository;

import com.example.EvaluacionUno.entity.BonosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonosRepository extends  JpaRepository<BonosEntity, Long> {
}