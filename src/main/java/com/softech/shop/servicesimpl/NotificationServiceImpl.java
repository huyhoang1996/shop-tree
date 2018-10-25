/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.servicesimpl;

import com.softech.shop.model.Customers;
import com.softech.shop.model.Orders;
import com.softech.shop.services.CustomerService;
import com.softech.shop.services.NotificationService;
import com.softech.shop.services.OrderDetailService;
import com.softech.shop.view_model.CartItem;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author PC
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private OrderDetailService orderDetailService;
 
    @Override
    public void sendMailForgotPassword(String requiredEmail) {
        Customers customer = customerService.findByEmail(requiredEmail);
        String newPassword = randomPassword();
        customer.setPassword(newPassword);
        customerService.save(customer);
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(requiredEmail);
        mail.setFrom("testemailforprojectindut@gmail.com");
        mail.setSubject("About validate password forgotten");
        String mailBody = "Hello " + customer.getCustomerName() + ".\n";
        mailBody += "You have just requested to reset your password.\n";
        mailBody += "Your new password : " + newPassword + ".\n";
        mailBody += "Thank you for using our service.\n";
        mailBody += "When using our services, you will get the best deals.\n\n";
        mailBody += "Best regards.\n";
        mailBody += "Project sem4 Java Spring - Team 1. \n";
        mailBody += (new Date()).toString();
        mail.setText(mailBody);
        javaMailSender.send(mail);
    }

    @Override
    public void sendMailForNewCustomer(Customers customers) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(customers.getEmail());
        mail.setFrom("testemailforprojectindut@gmail.com");
        mail.setSubject("Welcome to Fairy Garden");
        String mailBody = "Hello " + customers.getCustomerName() + ".\n";
        mailBody += "You have successfully registered to our system.\n";
        mailBody += "Your username : " + customers.getEmail() + ".\n";
        mailBody += "Thank you for using our service.\n";
        mailBody += "When using our services, you will get the best deals.\n\n";
        mailBody += "Best regards.\n";
        mailBody += "Project sem4 Java Spring - Team 1. \n";
        mailBody += (new Date()).toString();
        mail.setText(mailBody);

        javaMailSender.send(mail);
    }

    @Override
    public void sendEmailForOrder(List<CartItem> shoppingCartData,Orders order) {
        SimpleMailMessage mail = new SimpleMailMessage();
        long totalMoney = 0;
        mail.setTo(order.getCustomerId().getEmail());
        mail.setFrom("testemailforprojectindut@gmail.com");
        mail.setSubject("About order of " + order.getCustomerId().getEmail());
        
        String mailBody = "Hello " + order.getCustomerId().getCustomerName() + "\n";
        mailBody += "Your Order Code : " + order.getOrderCode() + "\n";
        mailBody += "Order date : " + order.getOrderDate() + "\n";
        mailBody += "Your order details are below: \n";
        for (CartItem cartItem : shoppingCartData) {
            mailBody += "\t\t" + (shoppingCartData.indexOf(cartItem)+ 1)+ ". " +cartItem.getProductName() + " x" + cartItem.getQuantity() + " =" + (cartItem.getQuantity() * cartItem.getPrice()) + " đ\n";
            totalMoney += cartItem.getQuantity() * cartItem.getPrice();
        }
        mailBody += "\t\t\t\t Total money : " + totalMoney + " đ\n";
        mailBody += "Shipment Details :  \n";
        mailBody += "\tMr/Mrs " + order.getCustomerId().getCustomerName() + "\n";
        mailBody += "\t\tEmail : " + order.getCustomerId().getEmail()+"\n";
        mailBody += "\t\tAddress : " + order.getCustomerId().getAddress()+"\n";
        mailBody += "\t\tPhone : " + order.getCustomerId().getPhone()+"\n";
        mailBody += "Billing Information :  \n";
        mailBody += "\tMr/Mrs " + order.getCustomerId().getCustomerName() + "\n";
        mailBody += "\t\tEmail : " + order.getCustomerId().getEmail()+"\n";
        mailBody += "\t\tAddress : " + order.getCustomerId().getAddress()+"\n";
        mailBody += "\t\tPhone : " + order.getCustomerId().getPhone()+"\n";
        
        mailBody += "Thank you for using our service.\n";
        mailBody += "When using our services, you will get the best deals.\n\n";
        mailBody += "Best regards.\n";
        mailBody += "Project sem4 Java Spring - Team 1.\n";
        mailBody += (new Date()).toString();
        mail.setText(mailBody);
        
        javaMailSender.send(mail);
    }

    @Override
    public void sendEmailForShipping(List<CartItem> shoppingCartData,Orders order) {
        SimpleMailMessage mail = new SimpleMailMessage();
        long totalMoney = 0;
        mail.setTo(order.getShippingId().getEmail());
        mail.setFrom("testemailforprojectindut@gmail.com");
        mail.setSubject("About order from " + order.getCustomerId().getEmail() + " to you");
        
        String mailBody = "Hello " + order.getShippingId().getShippingName() + "\n";
        mailBody += "You have a gifts from " + order.getCustomerId().getCustomerName() + "\n";
        mailBody += "Your Order Code : " + order.getOrderCode() + "\n";
        mailBody += "Order date : " + order.getOrderDate() + "\n";
        mailBody += "Your order details are below: \n";
        for (CartItem cartItem : shoppingCartData) {
            mailBody += "\t\t" + (shoppingCartData.indexOf(cartItem)+1) + ". " +cartItem.getProductName() + " x" + cartItem.getQuantity() + " =" + (cartItem.getQuantity() * cartItem.getPrice()) + " đ\n";
            totalMoney += cartItem.getQuantity() * cartItem.getPrice();
        }
        mailBody += "\t\t\t\t Total money : " + totalMoney + " đ\n";
        mailBody += "Shipment Details :  \n";
        mailBody += "\tMr/Mrs " + order.getShippingId().getShippingName()+ "\n";
        mailBody += "\t\tEmail : " + order.getShippingId().getEmail()+"\n";
        mailBody += "\t\tAddress : " + order.getShippingId().getAddress()+"\n";
        mailBody += "\t\tPhone : " + order.getShippingId().getPhone()+"\n";
        mailBody += "Billing Information :  \n";
        mailBody += "\tMr/Mrs " + order.getCustomerId().getCustomerName()+ "\n";
        mailBody += "\t\tEmail : " + order.getCustomerId().getEmail()+"\n";
        mailBody += "\t\tAddress : " + order.getCustomerId().getAddress()+"\n";
        mailBody += "\t\tPhone : " + order.getCustomerId().getPhone()+"\n";
        
        mailBody += "Thank you for using our service.\n";
        mailBody += "When using our services, you will get the best deals.\n\n";
        mailBody += "Best regards.\n";
        mailBody += "Project sem4 Java Spring - Team 1.\n";
        mailBody += (new Date()).toString();
        mail.setText(mailBody);
        
        javaMailSender.send(mail);
    }

    @Override
    public void sendEmailToCustomerWhenShipping(List<CartItem> shoppingCartData, Orders order) {
        SimpleMailMessage mail = new SimpleMailMessage();
        long totalMoney = 0;
        mail.setTo(order.getCustomerId().getEmail());
        mail.setFrom("testemailforprojectindut@gmail.com");
        mail.setSubject("About shipping to " + order.getShippingId().getEmail());
        
        String mailBody = "Hello " + order.getCustomerId().getCustomerName() + "\n";
        mailBody += "Your shipping to " + order.getShippingId().getEmail() + " has been submited successfully.\n";
        mailBody += "Your Order Code : " + order.getOrderCode() + "\n";
        mailBody += "Order date : " + order.getOrderDate() + "\n";
        mailBody += "Your order details are below: \n";
        for (CartItem cartItem : shoppingCartData) {
            mailBody += "\t\t" + (shoppingCartData.indexOf(cartItem)+1) + ". " +cartItem.getProductName() + " x" + cartItem.getQuantity() + " =" + (cartItem.getQuantity() * cartItem.getPrice()) + " đ\n";
            totalMoney += cartItem.getQuantity() * cartItem.getPrice();
        }
        mailBody += "\t\t\t\t Total money : " + totalMoney + " đ\n";
        mailBody += "Shipment Details :  \n";
        mailBody += "\tMr/Mrs " + order.getShippingId().getShippingName()+ "\n";
        mailBody += "\t\tEmail : " + order.getShippingId().getEmail()+"\n";
        mailBody += "\t\tAddress : " + order.getShippingId().getAddress()+"\n";
        mailBody += "\t\tPhone : " + order.getShippingId().getPhone()+"\n";
        mailBody += "Billing Information :  \n";
        mailBody += "\tMr/Mrs " + order.getCustomerId().getCustomerName()+ "\n";
        mailBody += "\t\tEmail : " + order.getCustomerId().getEmail()+"\n";
        mailBody += "\t\tAddress : " + order.getCustomerId().getAddress()+"\n";
        mailBody += "\t\tPhone : " + order.getCustomerId().getPhone()+"\n";
        
        mailBody += "Thank you for using our service.\n";
        mailBody += "When using our services, you will get the best deals.\n\n";
        mailBody += "Best regards.\n";
        mailBody += "Project sem4 Java Spring - Team 1.\n";
        mailBody += (new Date()).toString();
        mail.setText(mailBody);
        
        javaMailSender.send(mail);
    }
    
    private String randomPassword(){
        int max = 999999, min = 100000;
        int random = (int)(Math.random()*((max-min)+1)) + min;
        String password = Integer.toString(random);
        return password;
    }

    @Override
    public void sendEmailWhenHandling(Orders order) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(order.getCustomerId().getEmail());
        mail.setFrom("testemailforprojectindut@gmail.com");
        mail.setSubject("About order to " + order.getCustomerId().getEmail());
        
        String mailBody = "Hello " + order.getCustomerId().getCustomerName() + "\n";
//        if(order.getShippingId() == null){
//            mailBody += "Your order is on the way. Your order will arrive within 3 or 5 days.\n";
//        }
//        else{
//            mailBody += "Your order to " + order.getShippingId().getEmail() + " is on the way. Your order will be arrive within 3 or 5 days";
//        }
//        mailBody += "Your Order Code : " + order.getOrderCode() + "\n";
//        mailBody += "Order date : " + order.getOrderDate() + "\n";
//        mailBody += "Please visit our website to check your order.\n\n";
        
        mailBody += "Thank you for using our service.\n";
        mailBody += "When using our services, you will get the best deals.\n\n";
        mailBody += "Best regards.\n";
        mailBody += "Project sem4 Java Spring - Team 1.\n";
        mailBody += (new Date()).toString();
        mail.setText(mailBody);
        
        javaMailSender.send(mail);
    }

    @Override
    public void sendEmailWhenDelivered(Orders order) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(order.getCustomerId().getEmail());
        mail.setFrom("testemailforprojectindut@gmail.com");
        mail.setSubject("About order to " + order.getShippingId().getEmail());
        
        String mailBody = "Hello " + order.getCustomerId().getCustomerName() + "\n";
        if(order.getShippingId() == null){
            mailBody += "Your order has been succesfully delivered.\n";
        }
        else{
            mailBody += "Your order to " + order.getShippingId().getEmail() + " has been succesfully delivered.\n";
        }
        mailBody += "Your Order Code : " + order.getOrderCode() + "\n\n";        
        
        mailBody += "Thank you for using our service.\n";
        mailBody += "When using our services, you will get the best deals.\n\n";
        mailBody += "Best regards.\n";
        mailBody += "Project sem4 Java Spring - Team 1.\n";
        mailBody += (new Date()).toString();
        mail.setText(mailBody);
        
        javaMailSender.send(mail);
    }
}
