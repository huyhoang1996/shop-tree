/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.controllers;

import com.softech.shop.model.Customers;
import com.softech.shop.model.Employees;
import com.softech.shop.model.OrderDetails;
import com.softech.shop.model.Orders;
import com.softech.shop.services.CustomerService;
import com.softech.shop.services.EmployeeService;
import com.softech.shop.services.OrderDetailService;
import com.softech.shop.services.OrderService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Nguyen Tri
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private EmployeeService employeeService;

//    @Autowired
//    private ApplicationContext applicationContext;

    @GetMapping("/")
    public String listOrder(Model model) {
        model.addAttribute("listOrder", orderService.findAllOrderCho());
        return "admin/listOrder";
    }
    
    @GetMapping("/danggiao")
    public String listOrderCho(Model model) {
        model.addAttribute("listOrder", orderService.findAllOrderFinish());
        return "admin/listOrderDangGiao";
    }

    @GetMapping("/delete/{orderId}")
    public String deleteProduct(ModelMap modelMap, @PathVariable String orderId) {
        System.out.println(orderId);
        orderDetailService.deleteByOrderId(Integer.parseInt(orderId));
        
        orderService.deleteByOrderId(Integer.parseInt(orderId));
        return "redirect:/order/";
    }

    @GetMapping("/updateById/{id}")
    public String updateById(ModelMap modelMap, @PathVariable String id, Principal principal) {
        Orders orders = orderService.findById(Integer.parseInt(id)).get();

        String userName = principal.getName();
        System.out.println("loginName" + userName);
        Employees emp = employeeService.findUserAccount(userName);
        
        if (orders.getStatus().equals("Chờ")) {
            orders.setStatus("Xác nhận");
            orders.setEmployeeId(emp);
        } else {
            //xác nhận rồi không cho xác nhận lại
            orders.setStatus("Xác nhận");
            orders.setEmployeeId(emp);
        }
        orderService.save(orders);
        return "redirect:/order/";
    }
}
