package com.example.demo.repository;

import com.example.demo.entity.Brand;
import com.example.demo.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Integer> {
    @Query("SELECT b from Brand b where b.id=:Id")
    Brand findBrandById(Integer Id);
}
