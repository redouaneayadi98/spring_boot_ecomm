package com.ecomm_backend.services;

import com.ecomm_backend.dtos.CategoryDTO;
import com.ecomm_backend.dtos.ProductDTO;
import com.ecomm_backend.entities.Category;
import com.ecomm_backend.entities.Product;
import com.ecomm_backend.exceptions.CategoryNotFoundException;
import com.ecomm_backend.exceptions.ProductNotFoundException;
import com.ecomm_backend.mappers.ProductMapperImpl;
import com.ecomm_backend.repositoreis.CategoryRepository;
import com.ecomm_backend.repositoreis.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    private ProductMapperImpl dtoMapper;

    //*************category*************//
    @Override
    public List<CategoryDTO> getCategoryDTOS() {
        return categoryRepository.findAll().stream()
                .map(category-> dtoMapper.fromCategory(category))
                .collect(Collectors.toList());
    }
    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        log.info("Saving new Category");
        Category category=dtoMapper.fromCategoryDTO(categoryDTO);
        Category savedCategory=categoryRepository.save(category);
        return dtoMapper.fromCategory(savedCategory);
    }

    public CategoryDTO updateCategory(CategoryDTO categoryDTO) throws CategoryNotFoundException {
        categoryRepository.findById(categoryDTO.getId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        log.info("updating Category");
        Category category=dtoMapper.fromCategoryDTO(categoryDTO);
        Category savedCategory=categoryRepository.save(category);
        return dtoMapper.fromCategory(savedCategory);
    }

    public void deleteCategory(Long categoryId) throws CategoryNotFoundException {
            categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
            categoryRepository.deleteById(categoryId);
    }
    //************product*************//
    @Override
    public List<ProductDTO> products(int page,int size) {
        return productRepository.findAll(PageRequest.of(page,size)).stream()
                .map(product-> dtoMapper.fromProduct(product))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> productsByName(String productName,int page,int size) {
        return productRepository.findProductsByNameContains(productName,PageRequest.of(page,size)).stream()
                .map(product-> dtoMapper.fromProduct(product))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> productsByCategory(Long categoryId,int page,int size) throws CategoryNotFoundException {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        return productRepository.findProductsByCategory_Id(categoryId,PageRequest.of(page,size)).stream()
                .map(product-> dtoMapper.fromProduct(product))
                .collect(Collectors.toList());
    }
    @Override
    public List<ProductDTO> productsInPromotion(int page,int size){
        return productRepository.findProductsByPromotionIsTrue(PageRequest.of(page,size)).stream()
                .map(product-> dtoMapper.fromProduct(product))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) throws CategoryNotFoundException {
        Category category= categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        log.info("Saving new Product");
        Product product=dtoMapper.fromProductDTO(productDTO);
        product.setCategory(category);
        Product savedProduct=productRepository.save(product);
        return dtoMapper.fromProduct(savedProduct);
    }

    @Override
    public ProductDTO getProduct(Long productID) throws ProductNotFoundException {
        Product product=productRepository.findById(productID)
                .orElseThrow(()->new ProductNotFoundException("Product not found"));
        return dtoMapper.fromProduct(product);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) throws ProductNotFoundException {
        Product product2= productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        log.info("Updating Product");
        Product product=dtoMapper.fromProductDTO(productDTO);
        product.setCategory(product2.getCategory());
        Product savedProduct=productRepository.save(product);
        return dtoMapper.fromProduct(savedProduct);
    }
    @Override
    public void deleteProduct(Long productID) throws ProductNotFoundException {
        productRepository.findById(productID)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        productRepository.deleteById(productID);
    }
}
