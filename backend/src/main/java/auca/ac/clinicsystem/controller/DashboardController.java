package auca.ac.clinicsystem.controller;

import auca.ac.clinicsystem.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    // D4 - clinic summary
    @GetMapping("/summary")
    public Map<String, Long> summary() {
        return dashboardService.getSummary();
    }
}
