package com.ecomm_backend;

import com.ecomm_backend.dtos.CategoryDTO;
import com.ecomm_backend.dtos.ProductDTO;
import com.ecomm_backend.exceptions.CategoryNotFoundException;
import com.ecomm_backend.services.ProductServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class EcommBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommBackendApplication.class, args);
    }
    @Bean
    CommandLineRunner start(
            ProductServiceImpl productService
    ){
        return args -> {
            Stream.of("Sport","Books","Info").forEach(name->{
                CategoryDTO categoryDTO=new CategoryDTO();
                categoryDTO.setName(name);
                productService.saveCategory(categoryDTO);
            });

            productService.getCategoryDTOS()
                    .forEach(categoryDTO -> {
                ProductDTO productDTO=new ProductDTO();
                productDTO.setName(categoryDTO.getName()+" product");
                productDTO.setAvailable(true);
                productDTO.setDescription("product");
                productDTO.setCurrentPrice(200);
                productDTO.setPromotion(true);
                productDTO.setCategoryId(categoryDTO.getId());
                    try {
                        productService.saveProduct(productDTO);
                    } catch (CategoryNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    });
/*          to check
            System.out.println("all products "+productService.products(0,5));
            System.out.println("products by category "+productService.productsByCategory(1L,0,5));
            System.out.println("products by name "+productService.productsByName("prod",0,5));
            System.out.println("products in promotion "+productService.productsInPromotion(0,5));
            System.out.println("all categories "+productService.getCategoryDTOS());
            System.out.println("product by id "+productService.getProduct(2L));
            System.out.println();*/
        };
    }
}
