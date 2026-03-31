package com.carbonPrediction.Controller;

import com.carbonPrediction.Repository.CarbonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final CarbonRepository carbonRepository;

    public AdminController(CarbonRepository carbonRepository) {
        this.carbonRepository = carbonRepository;
    }

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("admin", carbonRepository.findAll());
        return "admin-dashboard";



    }
}



