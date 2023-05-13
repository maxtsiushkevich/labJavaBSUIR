package com.example.labjava.repository;

import com.example.labjava.model.TimeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<TimeModel, Integer> {
    public TimeModel findByDistanceAndSpeed(double distance, double speed);
}
