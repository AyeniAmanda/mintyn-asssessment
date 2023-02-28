package com.mintyn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_report")
public class ProductOrderReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;
    private String customerName;

    private String customerPhoneNumber;

    private String productName;

    private Integer orderQuantity;

    private BigDecimal productPrice;

    private BigDecimal totalProductPrice;

}
