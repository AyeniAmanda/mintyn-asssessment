package com.mintyn.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long productId;
    private String customerName;

    private String customerPhoneNumber;

    private String productName;

    private Integer orderQuantity;

}
