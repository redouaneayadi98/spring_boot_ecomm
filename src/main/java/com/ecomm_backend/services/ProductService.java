package com.ecomm_backend.services;

import com.ecomm_backend.dtos.CategoryDTO;
import com.ecomm_backend.dtos.ProductDTO;
import com.ecomm_backend.dtos.ProductPageDTO;
import com.ecomm_backend.exceptions.CategoryNotFoundException;
import com.ecomm_backend.exceptions.ProductNotFoundException;

import java.util.List;

public interface ProductService{

    /*************** Category ***************/
    //save new category
    CategoryDTO saveCategory(CategoryDTO categoryDTO);
    //get category by id
    CategoryDTO getCategory(Long id) throws CategoryNotFoundException;
    //update category
    CategoryDTO updateCategory(CategoryDTO categoryDTO) throws CategoryNotFoundException;
    //delete product
    void deleteCategory(Long categoryId) throws CategoryNotFoundException;
    //get all categories
    List<CategoryDTO> getCategoryDTOS();

    /*************** Product ***************/
    //save new product
    ProductDTO saveProduct(ProductDTO productDTO) throws CategoryNotFoundException;
    //get product by id
    ProductDTO getProduct(Long productID) throws ProductNotFoundException;
    //update product
    ProductDTO updateProduct(ProductDTO productDTO) throws ProductNotFoundException;
    //delete product
    void deleteProduct(Long productID) throws ProductNotFoundException;
    //get all products
    ProductPageDTO getProducts(int page, int size);
    //search products by keyword
    ProductPageDTO productsByKeyword(String productName, int page, int size);
    //search products by category
    ProductPageDTO productsByCategory(Long categoryId, int page, int size) throws CategoryNotFoundException;
    //search products in promotion
    ProductPageDTO productsInPromotion(int page, int size);
}
