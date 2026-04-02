package com.carbonPrediction.Controller;

import com.carbonPrediction.Entity.CarbonEntities;
import com.carbonPrediction.Repository.CarbonRepository;
import com.carbonPrediction.Service.CarbonService;
import com.carbonPrediction.dto.SuggestedValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EntityController {
    @Autowired
    private CarbonService carbonService;
    @Autowired
    private CarbonRepository carbonRepository;

    @GetMapping("/")
    public String dashboard(Model model, Authentication auth) {
        String username = auth.getName();

        model.addAttribute("data",
                carbonRepository.findByUsername(username));
        model.addAttribute("energy", new CarbonEntities());

        return "home";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute CarbonEntities carbonEntities, Model model, Authentication authentication){
      double actualCarbon=  carbonService.calculate(carbonEntities.getConsumedElectricity(),carbonEntities.getConsumedPetrol(),carbonEntities.getWastage());
      carbonEntities.setActualCarbon(actualCarbon);
      carbonEntities.setUsername(authentication.getName());
    SuggestedValues value=  carbonService.callanotherapplications(carbonEntities.getConsumedElectricity(),carbonEntities.getConsumedPetrol(),carbonEntities.getWastage());
      carbonEntities.setPredictedCarbon(value.getPredictedCarbon());
     carbonEntities.setSuggestedElectricity(value.getSuggestedElectricity());
     carbonEntities.setSuggestedPetrol(value.getSuggestedPetrol());
      model.addAttribute("energy",carbonEntities);
      carbonRepository.save(carbonEntities);
      return "predicted";
    }

    @GetMapping("/trend")
    @ResponseBody
    public List<Map<String, Object>> getTrend(Authentication auth) {

        String username = auth.getName();

        return carbonRepository.findByUsername(username)
                .stream()
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("date", e.getCreatedAt().toString());
                    map.put("carbon", e.getActualCarbon());
                    return map;
                })
                .toList();
    }
}
