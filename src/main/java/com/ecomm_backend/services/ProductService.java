package com.ecomm_backend.services;

import com.ecomm_backend.dtos.CategoryDTO;
import com.ecomm_backend.dtos.ProductDTO;
import com.ecomm_backend.exceptions.CategoryNotFoundException;
import com.ecomm_backend.exceptions.ProductNotFoundException;

import java.util.List;

public interface ProductService{
    //create a new category
    CategoryDTO saveCategory(CategoryDTO categoryDTO);
    List<CategoryDTO> getCategoryDTOS();

    //get all product
    List<ProductDTO> products(int page,int size);

    //get products by name
    List<ProductDTO> productsByName(String productName,int page,int size);
    //get products by category
    List<ProductDTO> productsByCategory(Long categoryId,int page,int size) throws CategoryNotFoundException;
    //get products by category
    List<ProductDTO> productsInPromotion(int page,int size);

    //create new product
    ProductDTO saveProduct(ProductDTO productDTO) throws CategoryNotFoundException;
    //get a product by id
    ProductDTO getProduct(Long productID) throws ProductNotFoundException;
    //update product
    ProductDTO updateProduct(ProductDTO productDTO) throws ProductNotFoundException;
    //delete product
    void deleteProduct(Long productID) throws ProductNotFoundException;

}
