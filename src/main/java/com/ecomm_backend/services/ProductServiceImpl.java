package com.ecomm_backend.services;

import com.ecomm_backend.dtos.CategoryDTO;
import com.ecomm_backend.dtos.ProductDTO;
import com.ecomm_backend.dtos.ProductPageDTO;
import com.ecomm_backend.entities.Category;
import com.ecomm_backend.entities.Product;
import com.ecomm_backend.exceptions.CategoryNotFoundException;
import com.ecomm_backend.exceptions.ProductNotFoundException;
import com.ecomm_backend.mappers.ProductMapperImpl;
import com.ecomm_backend.repositoreis.CategoryRepository;
import com.ecomm_backend.repositoreis.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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

    /*************** Category ***************/
    //save new category
    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        log.info("Saving new Category");
        Category category=dtoMapper.fromCategoryDTO(categoryDTO);
        category.setPhotoName("unknown.png");
        Category savedCategory=categoryRepository.save(category);
        return dtoMapper.fromCategory(savedCategory);
    }
    //update category
    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) throws CategoryNotFoundException {
        categoryRepository.findById(categoryDTO.getId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        log.info("updating Category");
        Category category=dtoMapper.fromCategoryDTO(categoryDTO);
        Category savedCategory=categoryRepository.save(category);
        return dtoMapper.fromCategory(savedCategory);
    }
    //delete category
    @Override
    public void deleteCategory(Long categoryId) throws CategoryNotFoundException {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        categoryRepository.deleteById(categoryId);
    }
    //get all categories
    @Override
    public List<CategoryDTO> getCategoryDTOS() {
        return categoryRepository.findAll().stream()
                .map(category-> dtoMapper.fromCategory(category))
                .collect(Collectors.toList());
    }
    //get category by id
    @Override
    public CategoryDTO getCategory(Long id) throws CategoryNotFoundException {
        Category category= categoryRepository.findById(id)
                .orElseThrow(()->new CategoryNotFoundException("Category not found"));
        return dtoMapper.fromCategory(category);
    }

    //************product*************//
    //save new product
    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) throws CategoryNotFoundException {
        Category category= categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        log.info("Saving new Product");
        Product product=dtoMapper.fromProductDTO(productDTO);
        product.setCategory(category);
        product.setPhotoName("unknown.png");
        Product savedProduct=productRepository.save(product);
        return dtoMapper.fromProduct(savedProduct);
    }
    //get product by id
    @Override
    public ProductDTO getProduct(Long productID) throws ProductNotFoundException {
        Product product=productRepository.findById(productID)
                .orElseThrow(()->new ProductNotFoundException("Product not found"));
        return dtoMapper.fromProduct(product);
    }
    //update product
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
    //delete product
    @Override
    public void deleteProduct(Long productID) throws ProductNotFoundException {
        productRepository.findById(productID)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        productRepository.deleteById(productID);
    }
    //get all products
    @Override
    public ProductPageDTO getProducts(int page, int size) {
        Page<Product> products= productRepository.findAll(PageRequest.of(page,size));
        return productsPage(products, page, size);
    }
    //search products by keyword
    @Override
    public ProductPageDTO productsByKeyword(String productName, int page, int size) {
        Page<Product> products= productRepository.findProductsByNameContains(productName,PageRequest.of(page,size));
        return productsPage(products, page, size);
    }
    //search products by category
    @Override
    public ProductPageDTO productsByCategory(Long categoryId, int page, int size) throws CategoryNotFoundException {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        Page<Product> products= productRepository.findProductsByCategory_Id(categoryId,PageRequest.of(page,size));
        return productsPage(products, page, size);
    }
    //search products in promotion
    @Override
    public ProductPageDTO productsInPromotion(int page, int size){
        Page<Product> products= productRepository.findProductsByPromotionIsTrue(PageRequest.of(page,size));
        return productsPage(products, page, size);
    }
    //function that transform products to productPageDTO
    public ProductPageDTO productsPage(Page<Product> products , int page, int size){
        List<ProductDTO> productDTOS=products.stream()
                .map(product-> dtoMapper.fromProduct(product))
                .collect(Collectors.toList());
        ProductPageDTO productPageDTO=new ProductPageDTO();
        productPageDTO.setCurrentPage(page);
        productPageDTO.setPageSize(size);
        productPageDTO.setTotalPages(products.getTotalPages());
        productPageDTO.setProductDTOS(productDTOS);
        return productPageDTO;
    }
}
