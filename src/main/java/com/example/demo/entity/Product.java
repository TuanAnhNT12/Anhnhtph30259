package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToOne
    @JoinColumn(name="subcate_id")
    Sub_category subcate_id;
    String product_name;
    String color;
    Integer quantity;
    BigDecimal sell_price;
    BigDecimal origin_price;
    String mota;
    @ManyToOne
    @JoinColumn(name="status_id")
    Status status_id;
}
