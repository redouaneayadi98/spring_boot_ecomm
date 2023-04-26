package com.ecomm_backend;

import com.ecomm_backend.dtos.CategoryDTO;
import com.ecomm_backend.dtos.ProductDTO;
import com.ecomm_backend.exceptions.CategoryNotFoundException;
import com.ecomm_backend.services.ProductServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

            try {
                Path categories = Paths.get("./images/categories/");
                Path products = Paths.get("./images/products/");
                Files.createDirectories(categories);
                Files.createDirectories(products);

                System.out.println("Directory is created!");

            } catch (IOException e) {
                System.err.println("Failed to create directory!" + e.getMessage());
            }

            try {
                Files.write(Paths.get("./images/categories/unknown.png" ),new byte[1]);
                Files.write(Paths.get("./images/products/unknown.png" ),new byte[1]);

            }catch (IOException e){
                System.out.println("Failed to save images! "+ e.getMessage());
            }


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
        };
    }
}
