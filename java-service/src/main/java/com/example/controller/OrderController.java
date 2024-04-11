package com.example.controller;

import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/generate-report")
    public void generateOrderReport(@RequestParam int userId) {
        orderService.generateOrderReport(userId);
    }

    // @GetMapping("/get-report")
    // public void getOrderReport(@RequestParam int userId) {
    //     return orderService.getOrderReport(userId);
    // }
}