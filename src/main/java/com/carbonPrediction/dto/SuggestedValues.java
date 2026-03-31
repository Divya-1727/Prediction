package com.carbonPrediction.dto;

public class SuggestedValues {
    private double predictedCarbon;
    private double suggestedElectricity;
    private double suggestedPetrol;

    public double getPredictedCarbon() {
        return predictedCarbon;
    }

    public void setPredictedCarbon(double predictedCarbon) {
        this.predictedCarbon = predictedCarbon;
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
}
