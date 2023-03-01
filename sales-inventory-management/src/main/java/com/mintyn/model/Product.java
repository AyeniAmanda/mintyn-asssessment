package com.mintyn.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_name", unique = true)
    private String productName;

    @Column(name = "product_description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "product_price", nullable = false)
    private BigDecimal costPrice;

    @Column(name = "product_quantity")
    private Integer quantity;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDate updatedAt;
}