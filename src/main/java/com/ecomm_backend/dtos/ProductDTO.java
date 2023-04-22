package com.ecomm_backend.dtos;

import lombok.Data;

import java.util.List;
@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private double currentPrice;
    private boolean promotion;
    private boolean available;
    private String photoName;
    private int promotionRate;
    private Long CategoryId;
    private String CategoryName;
    private List<String> color ;
}