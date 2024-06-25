package com.example.demo.repository;

import com.example.demo.entity.Brand;
import com.example.demo.entity.Sub_category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcategoryRepository extends JpaRepository<Sub_category,Integer> {
    @Query("SELECT s from Sub_category s where s.id=:Id")
    Sub_category findSub_categoryById(Integer Id);
}
