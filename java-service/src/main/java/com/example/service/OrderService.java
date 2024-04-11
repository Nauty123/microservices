package com.example.service;

import com.example.model.Order;
import com.example.model.OrderReport;
import com.example.repository.OrderReportRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final RestTemplate restTemplate;
    private final OrderReportRepository orderReportRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public OrderService(RestTemplate restTemplate, OrderReportRepository orderReportRepository, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.orderReportRepository = orderReportRepository;
        this.objectMapper = objectMapper;
    }

    public void generateOrderReport(int userId) {
        String url = "http://fastapi-service:8000/orders?user_id=" + userId;
        List<Order> orders = restTemplate.getForObject(url, List.class);

        if (orders != null && !orders.isEmpty()) {
            try {
                String reportData = objectMapper.writeValueAsString(orders);
                OrderReport report = new OrderReport();
                report.setUserId(userId);
                report.setReportDate(LocalDateTime.now());
                report.setReportData(reportData);
                orderReportRepository.save(report);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }


    // public List<OrderReport> getOrderReport(int userId) {
    //     // Fetch the report from the repository or any other source
    //     return orderReportRepository.findByUserId(userId);
    // }



}