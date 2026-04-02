package com.carbonPrediction.Repository;

import com.carbonPrediction.Entity.CarbonEntities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarbonRepository extends JpaRepository<CarbonEntities, Long> {

    List<CarbonEntities> findByUsername(String username);
}
