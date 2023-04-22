package com.ecomm_backend.web;

import com.ecomm_backend.dtos.CategoryDTO;
import com.ecomm_backend.dtos.ProductDTO;
import com.ecomm_backend.dtos.ProductPageDTO;
import com.ecomm_backend.exceptions.CategoryNotFoundException;
import com.ecomm_backend.exceptions.ProductNotFoundException;
import com.ecomm_backend.services.ProductServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/everyone")
public class ProductController {
    private ProductServiceImpl productService;

    /*************** Category ***************/
    //get All Categories
    @GetMapping("/categories")
    public List<CategoryDTO> categories(){
        return productService.getCategoryDTOS();
    }
    //get Category by id
    @GetMapping("/categories/{categoryId}")
    public CategoryDTO getCategory(@PathVariable Long categoryId) throws CategoryNotFoundException {
        return productService.getCategory(categoryId);
    }


    /*************** Product ***************/
    //get product by id
    @GetMapping("/products/{productId}")
    public ProductDTO getProduct(@PathVariable Long productId) throws ProductNotFoundException {
        return productService.getProduct(productId);
    }

    //get all products
    @GetMapping("/products")
    public ProductPageDTO getProducts(
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "5") int size
            ){
        return productService.getProducts(page,size);
    }
    //search products by keyword
    @GetMapping("/products/search/by-keyword")
    public ProductPageDTO productsByKeyword(
            @RequestParam(name="keyword",defaultValue = "") String keyword,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "5") int size
    ){
        return productService.productsByKeyword(keyword,page,size);
    }
    //search products by category
    @GetMapping("/products/search/by-category")
    public ProductPageDTO productsByCategory(
            @RequestParam(name="categoryId",defaultValue = "1") Long categoryId,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "5") int size
    ) throws CategoryNotFoundException {
        return productService.productsByCategory(categoryId,page,size);
    }
    //search products in promotion
    @GetMapping("/products/search/in-promotion")
    public ProductPageDTO productsInPromotion(
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "5") int size
    ){
        return productService.productsInPromotion(page,size);
    }
    //get photo of product by id
    @GetMapping(path = "/products/photo/{id}",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getPhotoProduct(@PathVariable Long id) throws ProductNotFoundException, IOException {
        ProductDTO p= productService.getProduct(id);
        return Files.readAllBytes(Paths.get("./images/products/" +p.getPhotoName()));
    }
    //get photo of category by id
    @GetMapping(path = "/categories/photo/{id}",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getPhotoCategory(@PathVariable Long id) throws IOException, CategoryNotFoundException {
        CategoryDTO c= productService.getCategory(id);
        return Files.readAllBytes(Paths.get("./images/categories/"+c.getPhotoName()));
    }
}
