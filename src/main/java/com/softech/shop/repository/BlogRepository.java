/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.repository;

import com.softech.shop.model.Blogs;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nguyen Tri
 */
@Repository
public interface BlogRepository extends CrudRepository<Blogs, Integer> {

    @Query(nativeQuery = true, value = "SELECT TOP (?) * FROM Blogs")
    List<Blogs> findByTop(@Param("top") int top);

    @Query(nativeQuery = true, value = "SELECT TOP (?) * FROM Blogs ORDER BY Hot DESC")
    List<Blogs> findByTopAndOrderByHot(@Param("top") int top);
}
