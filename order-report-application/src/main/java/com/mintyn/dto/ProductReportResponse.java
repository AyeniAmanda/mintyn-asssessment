package com.mintyn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ProductReportResponse {

    private String message;

    private HttpStatus status;
}
