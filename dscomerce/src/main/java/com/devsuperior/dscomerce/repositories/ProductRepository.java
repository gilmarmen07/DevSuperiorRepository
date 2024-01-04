package com.devsuperior.dscomerce.repositories;

import com.devsuperior.dscomerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
