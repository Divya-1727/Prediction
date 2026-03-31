package com.carbonPrediction.Repository;

import com.carbonPrediction.Entity.CarbonEntities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarbonRepository extends JpaRepository<CarbonEntities, Long> {
}
