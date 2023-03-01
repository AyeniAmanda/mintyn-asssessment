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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "product_order")
@Entity
public class OrderedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_phone_number")
    private String customerPhoneNumber;

    @Column(name = "product_order_name")
    private String productName;

    @Column(name = "order_quantity")
    private Integer orderQuantity;

    @Column(name = "product_order_price")
    private BigDecimal productPrice;

    @Column(name = "total_product_price")
    private BigDecimal totalProductPrice;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;
}
