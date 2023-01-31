package com.ecomm_backend.mappers;

import com.ecomm_backend.dtos.CategoryDTO;
import com.ecomm_backend.dtos.ProductDTO;
import com.ecomm_backend.entities.Category;
import com.ecomm_backend.entities.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ProductMapperImpl {
    public ProductDTO fromProduct(Product product){
        ProductDTO productDTO=new ProductDTO();
        BeanUtils.copyProperties(product,productDTO);
        return productDTO;
    }
    public Product fromProductDTO(ProductDTO productDTO){
        Product product=new Product();
        BeanUtils.copyProperties(productDTO,product);
        return product;
    }
    public CategoryDTO fromCategory(Category category){
        CategoryDTO categoryDTO=new CategoryDTO();
        BeanUtils.copyProperties(category,categoryDTO);
        return categoryDTO;
    }
    public Category fromCategoryDTO(CategoryDTO categoryDTO){
        Category category=new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        return category;
    }
}
