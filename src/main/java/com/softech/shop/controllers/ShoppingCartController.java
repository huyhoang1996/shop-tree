/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softech.shop.model.Customers;
import com.softech.shop.model.OrderDetails;
import com.softech.shop.model.Orders;
import com.softech.shop.model.Shipping;

import com.softech.shop.services.CategoryService;
import com.softech.shop.services.CustomerService;
import com.softech.shop.services.NotificationService;
import com.softech.shop.services.OrderDetailService;
import com.softech.shop.services.OrderService;
import com.softech.shop.services.PaymentMethodService;
import com.softech.shop.services.ProductService;
import com.softech.shop.services.ShippingService;
import com.softech.shop.view_model.CartItem;
import com.softech.shop.view_model.OrderViewModel;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author PC
 */
@Controller
@RequestMapping("/users")
public class ShoppingCartController {

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ShippingService shippingService;

    @Autowired
    private ProductService productService;
    
    @Autowired
    private NotificationService notificationService;

    public int orderId = -1;
    
    public List<CartItem> shoppingCartData = null;

    @GetMapping("/order")
    public String viewCart(ModelMap modelMap) {
        modelMap.addAttribute("categories", categoryService.findAll());
        return "/user/myCart";
    }

    @GetMapping("/order/payment")
    public String payment(Model model,HttpSession session) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("paymentMethod", paymentMethodService.findAll());
        if(session.getAttribute("customerId") == null){
            //chưa login
            model.addAttribute("orderInfo", new OrderViewModel());
        }
        else{
            //đã login
           OrderViewModel orderViewModel = new OrderViewModel();
           Customers customer = customerService.findById(Integer.parseInt(session.getAttribute("customerId").toString())).get();
           orderViewModel.setCustomerName(customer.getCustomerName());
           orderViewModel.setEmail(customer.getEmail());
           orderViewModel.setPhone(customer.getPhone());
           orderViewModel.setAddress(customer.getAddress());
           model.addAttribute("orderInfo", orderViewModel);
        }  
        model.addAttribute("checked",false);
        return "/user/checkout";
    }

    @PostMapping("/order/payment")
    public String paymentSubmit(@ModelAttribute("orderInfo") OrderViewModel orderInfo, ModelMap modelMap, HttpSession session) {
        try {
            long totalMoney = 0;
            Orders order = new Orders();
            orderService.save(order);
            order.setTax(0);
            order.setDiscount(0);

            orderId = order.getOrderId();

            if (orderInfo.getDeliveryName() != null && !orderInfo.getDeliveryName().equals("")) {               
                Shipping shipping = new Shipping();
                shipping.setShippingName(orderInfo.getDeliveryName());
                shipping.setEmail(orderInfo.getDeliveryEmail());
                shipping.setPhone(orderInfo.getDeliveryPhone());
                shipping.setAddress(orderInfo.getDeliveryAdress());
                shippingService.save(shipping);
                order.setShippingId(shipping);               
            }

            ObjectMapper mapper = new ObjectMapper();
            List<CartItem> shoppingCart = mapper.readValue(orderInfo.getCartData(), new TypeReference<List<CartItem>>() {});
            shoppingCartData = shoppingCart;
            
            for (int i = 0; i < shoppingCart.size(); i++) {
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrderId(order);
                orderDetails.setProductId(productService.findById(shoppingCart.get(i).getProductId()).get());
                orderDetails.setQuantity(shoppingCart.get(i).getQuantity());
                orderDetails.setPrice(shoppingCart.get(i).getPrice());
                orderDetails.setTax(0);
                orderDetails.setDiscount(0);
                totalMoney += shoppingCart.get(i).getPrice() * shoppingCart.get(i).getQuantity();
                orderDetailService.save(orderDetails);
            }
            Customers customers;
            if (session.getAttribute("customerId") == null) {
                // chưa login
                customers = new Customers();
                customers.setCustomerName(orderInfo.getCustomerName());
                customers.setEmail(orderInfo.getEmail());
                customers.setPhone(orderInfo.getPhone());
                customers.setAddress(orderInfo.getAddress());
                customers.setTotalMoney(totalMoney);
                customerService.save(customers);
                order.setCustomerId(customers);
            } 
            else {
                // đã login
                int customerId = Integer.parseInt(session.getAttribute("customerId").toString());
                customers = customerService.findById(customerId).get();
                order.setCustomerId(customers);
            }

            Date date = new Date();
            order.setPaymentMethodId(paymentMethodService.findById(orderInfo.getPaymentMethodIdView()).get());
            order.setTotal(totalMoney);
            order.setStatus("chờ");
            order.setOrderDate(date);//chổ này nè
            order.setOrderCode(generateOrderCode(orderId, customers.getCustomerId(), date));

            orderService.save(order);
            
            try {             
                if(order.getShippingId() != null){
                    notificationService.sendEmailForShipping(shoppingCartData, order);
                    notificationService.sendEmailToCustomerWhenShipping(shoppingCartData, order);
                }
                else{
                    notificationService.sendEmailForOrder(shoppingCartData,order);
                }
            } catch (MailException ex) {
                System.out.println(ex.getMessage());
            }
            
            return "redirect:/users/order/paymentSuccess";
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return "/user/error";
        }
    }

    @GetMapping("/order/paymentSuccess")
    public String paymentSuccess(Model model) {
        Optional<Orders> order = orderService.findById(orderId);
        if(order != null){
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("order", order.get());
            model.addAttribute("orderDetail", orderDetailService.findByOrderId(orderId));
            return "/user/payment-success";
        }
        else{
            return "/user/error";
        }
    }

    
    public String generateOrderCode(int orderId,int customerId,Date dateNow){
        String orderCode;
        int min = 10, max =99;
        int random = (int)(Math.random()*((max-min)+1)) + min;
        orderCode = Long.toString(dateNow.getTime()) + Integer.toString(orderId) + Integer.toString(customerId) + Integer.toString(random);
        return orderCode;
    }
}
