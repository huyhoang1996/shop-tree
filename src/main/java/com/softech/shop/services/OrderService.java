/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.services;

import com.softech.shop.model.Customers;
import com.softech.shop.model.Orders;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Nguyen Tri
 */
public interface OrderService {

    List<Orders> findAll();
    
    List<Orders> findAllOrder();
    
    List<Orders> findAllOrderFinish();
    
    List<Orders> findAllOrderCho();
    
    int countFindAllOrderCho();

    Optional<Orders> findById(Integer id);

    Orders save(Orders cus);

    void deleteById(Integer id);
    
    void deleteByOrderId(Integer id);
    
    List<Orders> findByCustomerId(int customerId);
    
    Orders findByOrderCode(String orderCode);
    
}
