/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.servicesimpl;


import com.softech.shop.model.Shipping;
import com.softech.shop.repository.ShippingRepository;
import com.softech.shop.services.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author PC
 */
@Service
public class ShippingServiceImpl implements ShippingService{

    @Autowired
    private ShippingRepository shippingRepository;
    

    public Shipping save(Shipping shipping) {
        return shippingRepository.save(shipping);
    }
    
}
