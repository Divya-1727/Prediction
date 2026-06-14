package com.carbonPrediction.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class CarbonEntities {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String username;
private double consumedElectricity;
private double consumedPetrol;
private double wastage;
private double actualCarbon;
private double predictedCarbon;
private double suggestedElectricity;
private double suggestedPetrol;
    private String explanation;

    private LocalDateTime createdAt = LocalDateTime.now();

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public double getSuggestedElectricity() {
        return suggestedElectricity;
    }

    public void setSuggestedElectricity(double suggestedElectricity) {
        this.suggestedElectricity = suggestedElectricity;
    }

    public double getSuggestedPetrol() {
        return suggestedPetrol;
    }

    public void setSuggestedPetrol(double suggestedPetrol) {
        this.suggestedPetrol = suggestedPetrol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getConsumedElectricity() {
        return consumedElectricity;
    }

    public void setConsumedElectricity(double consumedElectricity) {
        this.consumedElectricity = consumedElectricity;
    }

    public double getConsumedPetrol() {
        return consumedPetrol;
    }

    public void setConsumedPetrol(double consumedPetrol) {
        this.consumedPetrol = consumedPetrol;
    }

    public double getWastage() {
        return wastage;
    }

    public void setWastage(double wastage) {
        this.wastage = wastage;
    }

    public double getActualCarbon() {
        return actualCarbon;
    }

    public void setActualCarbon(double actualCarbon) {
        this.actualCarbon = actualCarbon;
    }

    public double getPredictedCarbon() {
        return predictedCarbon;
    }

    public void setPredictedCarbon(double predictedCarbon) {
        this.predictedCarbon = predictedCarbon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}