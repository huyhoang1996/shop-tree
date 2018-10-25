/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.repository;

import com.softech.shop.model.Customers;
import com.softech.shop.model.Employees;
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
public interface CustomerRepository extends CrudRepository<Customers, Integer> {

 @Query(nativeQuery = true, value = "SELECT * FROM Customers c WHERE c.email = :email")
    Customers findByEmail(@Param("email") String email);
    
    @Query(nativeQuery = true,value = "SELECT Email FROM Customers")
    List<String> listEmail();
}
