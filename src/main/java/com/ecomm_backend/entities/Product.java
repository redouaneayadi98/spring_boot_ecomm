package com.ecomm_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data@AllArgsConstructor@NoArgsConstructor
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double currentPrice;
    private boolean promotion;
    private int promotionRate;
    private boolean available;
    private String photoName;
    private List<String> color;
    @ManyToOne
    private Category category;

}
