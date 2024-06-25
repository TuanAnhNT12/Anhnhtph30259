package com.example.demo.repository;

import com.example.demo.entity.Product;
import com.example.demo.entity.Product_brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Product_BrandRepository extends JpaRepository<Product_brand,Integer> {
    @Query("SELECT p from Product_brand p where p.product_id.id=:Id")
    List<Product_brand> findProduct_brandByIdProduct(Integer Id);
}
