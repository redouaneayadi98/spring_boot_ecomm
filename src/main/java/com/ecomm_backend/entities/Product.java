package com.ecomm_backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data@AllArgsConstructor@NoArgsConstructor
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    private String description;
    @NotNull
    private double currentPrice;
    private boolean promotion=false;
    private int promotionRate=0;
    private boolean available=true;
    private String photoName;
    private List<String> color;
    @NotNull
    @ManyToOne
    private Category category;

}
