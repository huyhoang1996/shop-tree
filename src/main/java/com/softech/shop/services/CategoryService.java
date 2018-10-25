/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.services;

import com.softech.shop.model.Categories;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Nguyen Tri
 */
public interface CategoryService {

    List<Categories> findAll();

    Optional<Categories> findById(Integer id);

    Categories save(Categories p);

    void deleteById(Integer id);

    List<Categories> findByName(String name);
}
