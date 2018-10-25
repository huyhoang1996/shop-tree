/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.servicesimpl;


import com.softech.shop.model.PaymentMethods;
import com.softech.shop.repository.PaymentMethodRepository;
import com.softech.shop.services.PaymentMethodService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author PC
 */
@Service
public class PaymentMethodServiceImpl implements PaymentMethodService{

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    
    @Override
    public List<PaymentMethods> findAll() {
        return (List<PaymentMethods>)paymentMethodRepository.findAll();
    }

    @Override
    public Optional<PaymentMethods> findById(Integer paymentMethodId) {
        return paymentMethodRepository.findById(paymentMethodId);
    }
    
}
