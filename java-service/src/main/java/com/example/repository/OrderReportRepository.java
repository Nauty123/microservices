package com.example.repository;
import java.util.List;


import com.example.model.OrderReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderReportRepository extends JpaRepository<OrderReport, Long> {
    //  List<OrderReport> findByUserId(int userId);
}
