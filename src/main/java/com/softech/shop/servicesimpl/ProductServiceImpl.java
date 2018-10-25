package com.softech.shop.servicesimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softech.shop.model.Products;
import com.softech.shop.repository.ProductRepository;
import com.softech.shop.services.ProductService;
import com.softech.shop.view_model.ProductToView;
import java.util.Collections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Products> findAll() {
        return (List<Products>) productRepository.findAll();
    }

    @Override
    public Products save(Products p) {
        return productRepository.save(p);
    }

    @Override
    public List<Products> findByName(String name) {
        return productRepository.findByProductName(name);
    }

    @Override
    public Optional<Products> findById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public int deleteById(Integer id) {
        productRepository.deleteById(id);
        return 1;
    }



    @Override
    public List<Products> findByTop(Integer categoryId) {
        return productRepository.findByTop(categoryId);
    }

    @Override
    public List<Products> findRelationProductByCategoryId(Integer productId, Integer categoryId) {
        return productRepository.findRelationProductByCategoryId(productId, categoryId);
    }

    @Override
    public List<Products> findByCategoryId(int categoryId) {
        return productRepository.findByCategory(categoryId);
    }

    @Override
    public Page<ProductToView> findPaginated(Pageable pageable, List<ProductToView> listToView) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ProductToView> list;

        if (listToView.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, listToView.size());
            list = listToView.subList(startItem, toIndex);
        }

        Page<ProductToView> productPage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), listToView.size());

        return productPage;
    }

    @Override
    public int countProduct() {
        return productRepository.countProduct();
    }

}
