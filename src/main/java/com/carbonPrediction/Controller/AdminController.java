package com.carbonPrediction.Controller;
import java.util.List;

import com.carbonPrediction.Entity.CarbonEntities;
import com.carbonPrediction.Repository.CarbonRepository;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.awt.*;
import java.util.Collection;


@Controller
public class AdminController {

    private final CarbonRepository carbonRepository;

    public AdminController(CarbonRepository carbonRepository) {
        this.carbonRepository = carbonRepository;
    }

    //admin dashboard
    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        // Get logged-in user
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        // Get role
        Collection<? extends GrantedAuthority> authorities =
                authentication.getAuthorities();

        boolean isAdmin = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        List<CarbonEntities> data;

        // ADMIN -> see all data
        if (isAdmin) {

            data = carbonRepository.findAll();

        } else {

            // USER -> see only own data
            data = carbonRepository.findByUsername(username);
        }

        // Send table data to dashboard
        model.addAttribute("admin", data);

        // Total predictions count
        model.addAttribute("totalPredictions", data.size());

        // High carbon cases count
        long highCarbonCases = data.stream()
                .filter(c -> c.getPredictedCarbon() > 200)
                .count();

        model.addAttribute("highCarbonCases", highCarbonCases);

        return "admin-dashboard";
    }



    //download report

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadSingle(@PathVariable Long id) throws Exception {

        var e = carbonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

// 🔐 ADD THIS BLOCK
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));



// 👇 SIMPLE RULE
        if (!isAdmin && !e.getUsername().equals(username)) {
            throw new RuntimeException("You can only download your own report");
        }
       



        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // ===== Fonts =====
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        PdfFont normal = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        // ===== Title =====
        document.add(new Paragraph("Energy Consumption Report")
                .setFont(bold)
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph(" ")); // space

        // ===== Table =====
        Table table = new Table(8);
        table.setWidth(UnitValue.createPercentValue(100));

        String[] headers = {
                "ID","Electricity","Petrol","Waste",
                "Actual Carbon","Predicted",
                "Suggested Electricity","Suggested Petrol"
        };

        for (String h : headers) {
            table.addHeaderCell(new Cell()
                    .add(new Paragraph(h).setFont(bold))
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY));
        }

        table.addCell(e.getId().toString());
        table.addCell(String.valueOf(e.getConsumedElectricity()));
        table.addCell(String.valueOf(e.getConsumedPetrol()));
        table.addCell(String.valueOf(e.getWastage()));
        table.addCell(String.valueOf(e.getActualCarbon()));
        table.addCell(String.valueOf(e.getPredictedCarbon()));
        table.addCell(String.valueOf(e.getSuggestedElectricity()));
        table.addCell(String.valueOf(e.getSuggestedPetrol()));

        document.add(table);

        document.add(new Paragraph(" "));

        document.close();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=energy-" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(out.toByteArray());
    }





}



