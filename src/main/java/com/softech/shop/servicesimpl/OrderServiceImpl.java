/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.servicesimpl;

import com.softech.shop.model.Customers;
import com.softech.shop.model.Orders;
import com.softech.shop.repository.CustomerRepository;
import com.softech.shop.repository.OrderRepository;
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
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    
    @Override
    public List<Orders> findAll() {
        return (List<Orders>) orderRepository.findAll();
    }

    @Override
    public Optional<Orders> findById(Integer id) {
       return orderRepository.findById(id);
    }

    @Override
    public Orders save(Orders cus) {
       return orderRepository.save(cus);
    }

    @Override
    public void deleteById(Integer id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void deleteByOrderId(Integer id) {
        orderRepository.deleteByOrderId(id);
    }

    @Override
    public List<Orders> findAllOrder() {
        return (List<Orders>) orderRepository.findAllOrder();
    }

    @Override
    public List<Orders> findAllOrderCho() {
        return(List<Orders>) orderRepository.findAllOrderCho();
    }


    @Override
    public List<Orders> findByCustomerId(int customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public Orders findByOrderCode(String orderCode) {
        return orderRepository.findByOrderCode(orderCode);
    }

    @Override
    public List<Orders> findAllOrderFinish() {
        return orderRepository.findAllOrderFinish();
    }

    @Override
    public int countFindAllOrderCho() {
        return orderRepository.countFindAllOrderCho();
    }

}
