package com.example.EvaluacionUno.repository;

import com.example.EvaluacionUno.entity.DescuentosRecargosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescuentosRecargosRepository extends JpaRepository<DescuentosRecargosEntity, Long> {
}
