/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.services;

import com.softech.shop.model.OrderDetails;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Nguyen Tri
 */
public interface OrderDetailService {

    List<OrderDetails> findAll();
    
    List<OrderDetails> findStatics();

    Optional<OrderDetails> findById(Integer id);

    OrderDetails save(OrderDetails cusdetail);

    List<OrderDetails> findAllOrderDetail(Integer orderId);

    void deleteById(Integer id);

    void deleteByOrderId(Integer id);

    List<OrderDetails> findByOrderId(Integer orderId);
}
