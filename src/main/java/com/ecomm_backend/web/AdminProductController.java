package com.ecomm_backend.web;

import com.ecomm_backend.dtos.CategoryDTO;
import com.ecomm_backend.dtos.ProductDTO;
import com.ecomm_backend.exceptions.CategoryNotFoundException;
import com.ecomm_backend.exceptions.ProductNotFoundException;
import com.ecomm_backend.services.ProductServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminProductController {

    /*************** Product ***************/

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
    private ProductServiceImpl productService;

    //delete category
    @DeleteMapping("/categories/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) throws CategoryNotFoundException {
        productService.deleteCategory(categoryId);
    }

    /*************** Product ***************/
    //save new product
    @PostMapping("/products")
    public ProductDTO saveProduct(@RequestBody ProductDTO productDTO) throws CategoryNotFoundException {
        return productService.saveProduct(productDTO);
    }

    //delete product
    @DeleteMapping("/products/{productId}")
    public void deleteProduct(@PathVariable Long productId) throws ProductNotFoundException {
        productService.deleteProduct(productId);
    }

    //update product
    @PutMapping("/products/{productId}")
    public ProductDTO updateProduct(@PathVariable Long productId,@RequestBody ProductDTO productDTO) throws ProductNotFoundException {
        productDTO.setId(productId);
        return productService.updateProduct(productDTO);
    }

    //upload photo of product
    @PostMapping(path = "/products/uploadPhoto/{id}")
    public void uploadPhotoProduct(MultipartFile file, @PathVariable Long id) throws ProductNotFoundException, IOException {
        ProductDTO p= productService.getProduct(id);
        p.setPhotoName(id+".png");
        //Files.write(Paths.get(System.getProperty("user.home") + "/ecom/products/" + p.getPhotoName()),file.getBytes());
        Files.write(Paths.get("./images/products/" + p.getPhotoName()),file.getBytes());
        productService.updateProduct(p);
    }
    //upload photo of category
    @PostMapping(path = "/categories/uploadPhoto/{id}")
    public void uploadPhotoCategory(MultipartFile file, @PathVariable Long id) throws IOException, CategoryNotFoundException {
        CategoryDTO c= productService.getCategory(id);
        c.setPhotoName(id+".png");
        Files.write(Paths.get("./images/categories/" + c.getPhotoName()),file.getBytes());
        productService.updateCategory(c);
    }

}
