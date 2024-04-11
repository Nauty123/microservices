package com.example.controller;

import com.example.model.OrderReport;
import com.example.repository.OrderReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class ReportController {

    private final OrderReportRepository orderReportRepository;

    @Autowired
    public ReportController(OrderReportRepository orderReportRepository) {
        this.orderReportRepository = orderReportRepository;
    }

    @GetMapping("/reports/{reportId}")
    public String getReport(@PathVariable Long reportId, Model model) {
        Optional<OrderReport> report = orderReportRepository.findById(reportId);
        if (report.isPresent()) {
            model.addAttribute("report", report.get());
            return "report";
        } else {
            return "report-not-found";
        }
    }
}