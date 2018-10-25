/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.controllers;

import com.softech.shop.model.Orders;
import com.softech.shop.services.OrderDetailService;
import com.softech.shop.services.OrderService;
import com.softech.shop.servicesimpl.WebUtils;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author Nguyen Tri
 */
@Controller
@RequestMapping("shipper")
public class ShipperController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("")
    public String listOrder(Model model) {
        model.addAttribute("listOrder", orderService.findAllOrder());
        return "admin/listOrderShipper";
    }

    @GetMapping("/updateById/{id}")
    public String updateById(ModelMap modelMap, @PathVariable String id, Principal principal) {
        Orders orders = orderService.findById(Integer.parseInt(id)).get();

        if (orders.getStatus().equals("Xác nhận")) {
            orders.setStatus("Đang giao");
        } else {
            //xác nhận rồi không cho xác nhận lại
            orders.setStatus("Đang giao");
            if (orders.getStatus().equals("Đang giao")) {
                orders.setStatus("Xong");
            } else {
                //xác nhận rồi không cho xác nhận lại
                orders.setStatus("Xong");
            }
        }
        orderService.save(orders);
        //modelMap.addAttribute("userInfo", userInfo);
        return "redirect:/shipper";
    }

    @GetMapping("/{orderId}")
    public String listOrderDetail(Model model, @PathVariable String orderId) {
        model.addAttribute("listdetail", orderDetailService.findAllOrderDetail(Integer.parseInt(orderId)));
        return "admin/listOrderDetailShipper";
    }
}
