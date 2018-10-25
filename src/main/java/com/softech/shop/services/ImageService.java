/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.services;

import com.softech.shop.model.Images;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Nguyen Tri
 */
public interface ImageService {

    List<Images> findAll();

    Optional<Images> findById(Integer id);

    List<Images> findByProductId(Integer id);

    void deleteByProductId(Integer id);

    Images save(Images p);

    void deleteById(Integer id);

    List<Images> findByName(String name);

    Images findByProduct(int productId);
}
