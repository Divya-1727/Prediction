package com.carbonPrediction.Service;

import com.carbonPrediction.dto.SuggestedValues;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CarbonService {
    private RestTemplate restTemplate= new RestTemplate();

    public double calculate(double electricity, double petrol, double wastage){
        return (electricity*0.92)+(petrol*2.31)+(wastage*1.5);


    }

    public double callanotherapplication(double electricity, double petrol, double wastage){
        return restTemplate.getForObject("http://localhost:8080/predict"+"?electricity="+electricity+"&petrol="+petrol+"&wastage="+wastage,Double.class);

    }


    public SuggestedValues callanotherapplications(double electricity, double petrol, double wastage){
        return restTemplate.getForObject("http://localhost:8080/predictcarbon"+"?electricity="+electricity+"&petrol="+petrol+"&wastage="+wastage,SuggestedValues.class);

    }

}
