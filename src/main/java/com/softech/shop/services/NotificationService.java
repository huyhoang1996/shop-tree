/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.services;


import com.softech.shop.model.Customers;
import com.softech.shop.model.Orders;
import com.softech.shop.view_model.CartItem;
import java.util.List;

/**
 *
 * @author PC
 */
public interface NotificationService {
    void sendMailForgotPassword(String requiredEmail);
    
    void sendMailForNewCustomer(Customers customers);
    
    void sendEmailForOrder(List<CartItem> shoppingCartData,Orders order);
    
    void sendEmailForShipping(List<CartItem> shoppingCartData,Orders order);
    
    void sendEmailToCustomerWhenShipping(List<CartItem> shoppingCartData,Orders order);
    
    void sendEmailWhenHandling(Orders order);
    
    void sendEmailWhenDelivered(Orders order);
}
