/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.services;


import com.softech.shop.model.PaymentMethods;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author PC
 */
public interface PaymentMethodService {
    List<PaymentMethods> findAll();
    
    Optional<PaymentMethods> findById(Integer paymentMethodId);
}
