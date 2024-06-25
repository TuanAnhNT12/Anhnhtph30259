package com.example.demo.repository;

import com.example.demo.entity.Brand;
import com.example.demo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query("SELECT p FROM Product p WHERE (:keySearch is null or p.product_name like %:keySearch%)" +
            "AND (:statusSearch is null or p.status_id.status_name like %:statusSearch%)" +
            "AND (:cateSearch IS NULL OR p.subcate_id.cate_id.cate_name LIKE %:cateSearch%)")
    Page<Product> findAllSearch(@Param("keySearch") String keySearch,
                                @Param("statusSearch") String statusSearch,
                                @Param("cateSearch")String cateSearch,
                          Pageable pageable);
    @Query("SELECT p FROM Product p WHERE p.id >= (SELECT MAX(id) FROM Product)")
    Product findProductMax();
    @Query("SELECT p from Product p where p.id=:Id")
    Product findProductById(Integer Id);
}
