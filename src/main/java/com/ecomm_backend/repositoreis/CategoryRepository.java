package com.ecomm_backend.repositoreis;

import com.ecomm_backend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
