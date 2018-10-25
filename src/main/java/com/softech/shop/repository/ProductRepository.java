package com.softech.shop.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.softech.shop.model.Products;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends CrudRepository<Products, Integer> {

    List<Products> findByProductName(String productName);
    
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "SELECT sum([View]) FROM Products")
    int countProduct();

    @Query(nativeQuery = true,
            value = "SELECT TOP 12 * FROM Products p WHERE p.CategoryId = :categoryId and p.Status = 1 ORDER BY p.Priority DESC")
    List<Products> findByTop(@Param("categoryId") Integer categoryId);

    @Query(nativeQuery = true,
            value = "SELECT TOP 8 * FROM Products p WHERE p.ProductId != :productId and p.CategoryId = :categoryId and p.Status = 1 ORDER BY p.Priority DESC")
    List<Products> findRelationProductByCategoryId(@Param("productId") Integer productId, @Param("categoryId") Integer categoryId);

    @Query(nativeQuery = true, value = "SELECT * FROM Products WHERE CategoryId = :categoryId")
    List<Products> findByCategory(@Param("categoryId") int categoryId);

    @Query(nativeQuery = true, value = "SELECT * FROM Products WHERE ProductName like %:keyword%")
    List<Products> findByName(@Param("keyword") String keyword);
}
