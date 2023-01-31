package com.ecomm_backend.repositoreis;

import com.ecomm_backend.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findProductsByNameContains(String productName, Pageable pageable);
    Page<Product> findProductsByCategory_Id(Long categoryId, Pageable pageable);
    Page<Product> findProductsByPromotionIsTrue(Pageable pageable);
}