package com.mintyn.repository;

import com.mintyn.model.ProductOrderReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductOrderReportRepository extends JpaRepository<ProductOrderReport, Long> {

        Optional<List<ProductOrderReport>> findAllByCreatedAtBetween(Date earliestDate, Date latestDate);
}
