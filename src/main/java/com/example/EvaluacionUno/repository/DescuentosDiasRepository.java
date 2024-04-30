package com.example.EvaluacionUno.repository;

import com.example.EvaluacionUno.entity.DescuentosDiasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescuentosDiasRepository extends  JpaRepository<DescuentosDiasEntity, Long> {
}