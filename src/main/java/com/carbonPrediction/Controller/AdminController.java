package com.carbonPrediction.Controller;

import com.carbonPrediction.Repository.CarbonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/admin/download/{id}")
    public ResponseEntity<String> downloadSingle(@PathVariable Long id) {

        var e = carbonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        StringBuilder pdf = new StringBuilder();
        pdf.append("ID,Electricity,Petrol,Waste,Carbon,Predicted,SuggestedElectricity,SuggestedPetrol,Date\n");

        pdf.append(e.getId()).append(",")
                .append(e.getConsumedElectricity()).append(",")
                .append(e.getConsumedPetrol()).append(",")
                .append(e.getWastage()).append(",")
                .append(e.getActualCarbon()).append(",")
                .append(e.getPredictedCarbon()).append(",")
                .append(e.getSuggestedElectricity()).append(",")
                .append(e.getSuggestedPetrol()).append(",");


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=energy-" + id + ".pdf")
                .contentType(MediaType.TEXT_PLAIN)
                .body(pdf.toString());
    }
}



