/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.repository;

import com.softech.shop.model.Categories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nguyen Tri
 */

@Repository
public interface CategoryRepository extends CrudRepository<Categories, Integer>{
    
}
