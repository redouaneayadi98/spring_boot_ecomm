package com.ecomm_backend.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ProductPageDTO {
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<ProductDTO> productDTOS;
}
