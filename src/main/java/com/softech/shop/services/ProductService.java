package com.softech.shop.services;

import java.util.List;
import java.util.Optional;

import com.softech.shop.model.Products;
import com.softech.shop.view_model.ProductToView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    List<Products> findAll();

    int countProduct();
    
    Optional<Products> findById(Integer id);

    Products save(Products p);

    int deleteById(Integer id);

    List<Products> findByName(String name);

    List<Products> findByTop(Integer categoryId);

    List<Products> findRelationProductByCategoryId(Integer productId, Integer categoryId);

    List<Products> findByCategoryId(int categoryId);

    Page<ProductToView> findPaginated(Pageable pageable, List<ProductToView> listToView);

}
