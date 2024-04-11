package com.example.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private LocalDateTime orderDate;
    private double totalAmount;
    private List<OrderItem> items;

    // Getters and Setters
}