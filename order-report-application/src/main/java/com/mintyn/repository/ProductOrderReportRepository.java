package com.mintyn.repository;

import com.mintyn.model.ProductOrderReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOrderReportRepository extends JpaRepository<ProductOrderReport, Long> {


}
