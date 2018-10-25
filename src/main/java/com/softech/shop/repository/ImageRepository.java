/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.repository;

import com.softech.shop.model.Images;

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
public interface ImageRepository extends CrudRepository<Images, Integer>{
	@Query(value = "SELECT * FROM Images WHERE ProductId = ?1", nativeQuery = true)
	List<Images> findByProductId(Integer id);
	
	@Query(value = "DELETE FROM Images WHERE ProductId = ?1", nativeQuery = true)
	void deleteByProductId(Integer id);

 @Query(nativeQuery = true,value = "SELECT * FROM Images WHERE ProductId = :productId")
    Images findByProductId(@Param("productId")int productId);
}
