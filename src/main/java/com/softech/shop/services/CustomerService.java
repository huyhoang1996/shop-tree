/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.services;

import com.softech.shop.model.Customers;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Nguyen Tri
 */
public interface CustomerService {

    
    List<Customers> findAll();

    Optional<Customers> findById(Integer id);

    Customers save(Customers cus);

    void deleteById(Integer id);

    
    Customers findByEmail(String email);
    
//    Customers findById(int customerId);
    
    List<String> listEmail();
}
