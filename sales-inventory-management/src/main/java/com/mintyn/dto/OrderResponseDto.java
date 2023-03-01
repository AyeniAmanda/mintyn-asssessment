package com.mintyn.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate CreatedDate;
    private String customerName;
    private String customerPhoneNumber;
    private String productName;
    private Integer orderQuantity;
    private BigDecimal productPrice;
    private BigDecimal totalProductPrice;
}

