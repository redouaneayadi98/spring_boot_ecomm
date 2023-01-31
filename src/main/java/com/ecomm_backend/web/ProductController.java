package com.ecomm_backend.web;

import com.ecomm_backend.dtos.CategoryDTO;
import com.ecomm_backend.dtos.ProductDTO;
import com.ecomm_backend.exceptions.CategoryNotFoundException;
import com.ecomm_backend.exceptions.ProductNotFoundException;
import com.ecomm_backend.services.ProductServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@CrossOrigin("*")
@RestController
public class ProductController {
    private ProductServiceImpl productService;

    /*************** Category ***************/
    //get All Categories
    @GetMapping("/categories")
    public List<CategoryDTO> categories(){
        return productService.getCategoryDTOS();
    }
    //create new category
    @PostMapping("/categories")
    public CategoryDTO createCategory(@RequestBody CategoryDTO categoryDTO){
        return productService.saveCategory(categoryDTO);
    }
    //update category
    @PutMapping("/categories/{categoryId}")
    public CategoryDTO updateCategory(@PathVariable Long categoryId,@RequestBody CategoryDTO categoryDTO) throws CategoryNotFoundException {
        categoryDTO.setId(categoryId);
        return productService.updateCategory(categoryDTO);
    }
    //delete category
    @DeleteMapping("/categories/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) throws CategoryNotFoundException {
        productService.deleteCategory(categoryId);
    }
    /*************** Product ***************/
    //get product by id
    @GetMapping("/products/{productId}")
    public ProductDTO getProduct(@PathVariable Long productId) throws ProductNotFoundException {
        return productService.getProduct(productId);
    }
    //create new product
    @PostMapping("/products")
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) throws CategoryNotFoundException {
        return productService.saveProduct(productDTO);
    }
    //update category
    @PutMapping("/products/{productId}")
    public ProductDTO updateProduct(@PathVariable Long productId,@RequestBody ProductDTO productDTO) throws ProductNotFoundException {
        productDTO.setId(productId);
        return productService.updateProduct(productDTO);
    }
    //delete category
    @DeleteMapping("/products/{productId}")
    public void deleteProduct(@PathVariable Long productId) throws ProductNotFoundException {
        productService.deleteProduct(productId);
    }
    /*************** find Products ***************/
    //get all products
    @GetMapping("/products")
    public List<ProductDTO> products(
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "5") int size
            ){
        return productService.products(page,size);
    }
    //get products by their name
    @GetMapping("/products/by-name")
    public List<ProductDTO> productsByName(
            @RequestParam(name="keyword",defaultValue = "") String keyword,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "5") int size
    ){
        return productService.productsByName(keyword,page,size);
    }
    //get products by their category
    @GetMapping("/products/by-category")
    public List<ProductDTO> productsByCategory(
            @RequestParam(name="categoryId",defaultValue = "1") Long categoryId,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "5") int size
    ) throws CategoryNotFoundException {
        return productService.productsByCategory(categoryId,page,size);
    }
    //get products in promotion
    @GetMapping("/products/in-promotion")
    public List<ProductDTO> productsInPromotion(
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "5") int size
    ){
        return productService.productsInPromotion(page,size);
    }
}
