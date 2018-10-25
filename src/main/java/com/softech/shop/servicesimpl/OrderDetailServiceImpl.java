/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.servicesimpl;

import com.softech.shop.model.OrderDetails;
import com.softech.shop.model.Orders;
import com.softech.shop.repository.OrderDetailRepository;
import com.softech.shop.services.OrderDetailService;
import com.softech.shop.services.OrderService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nguyen Tri
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService{
    
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetails> findAll() {
        return (List<OrderDetails>) orderDetailRepository.findAll();
    }

    @Override
    public Optional<OrderDetails> findById(Integer id) {
       return orderDetailRepository.findById(id);
    }

    @Override
    public OrderDetails save(OrderDetails cusdetail) {
        return orderDetailRepository.save(cusdetail);
    }    

    @Override
    public List<OrderDetails> findAllOrderDetail(Integer orderId) {
        return orderDetailRepository.findAllOrderDetail(orderId);
    }

    @Override
    public void deleteById(Integer id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public void deleteByOrderId(Integer id) {
        orderDetailRepository.deleteByOrderId(id);
    }
  
    

    @Override
    public List<OrderDetails> findByOrderId(Integer orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    @Override
    public List<OrderDetails> findStatics() {
        return orderDetailRepository.findStatics();
    }
}
