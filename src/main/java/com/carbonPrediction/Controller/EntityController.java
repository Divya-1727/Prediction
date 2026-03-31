package com.carbonPrediction.Controller;

import com.carbonPrediction.Entity.CarbonEntities;
import com.carbonPrediction.Service.CarbonService;
import com.carbonPrediction.dto.SuggestedValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EntityController {
    @Autowired
    private CarbonService carbonService;
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("energy",new CarbonEntities());
        return "home";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute CarbonEntities carbonEntities,Model model){
      double actualCarbon=  carbonService.calculate(carbonEntities.getConsumedElectricity(),carbonEntities.getConsumedPetrol(),carbonEntities.getWastage());
      carbonEntities.setActualCarbon(actualCarbon);
    SuggestedValues value=  carbonService.callanotherapplications(carbonEntities.getConsumedElectricity(),carbonEntities.getConsumedPetrol(),carbonEntities.getWastage());
      carbonEntities.setPredictedCarbon(value.getPredictedCarbon());
     carbonEntities.setSuggestedElectricity(value.getSuggestedElectricity());
     carbonEntities.setSuggestedPetrol(value.getSuggestedPetrol());
      model.addAttribute("energy",carbonEntities);
      return "predicted";
    }
}
