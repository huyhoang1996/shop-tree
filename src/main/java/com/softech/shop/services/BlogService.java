/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.services;

import com.softech.shop.model.Blogs;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Nguyen Tri
 */
public interface BlogService {

    List<Blogs> findAll();

    Optional<Blogs> findById(Integer id);

    Blogs save(Blogs b);

    void deleteById(Integer id);

    List<Blogs> findByTop(int top);

    List<Blogs> findByTopOrderByHot(int top);

    Blogs findById(int id);
}
