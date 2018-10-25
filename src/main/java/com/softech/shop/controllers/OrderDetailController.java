/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.controllers;

import com.softech.shop.model.OrderDetails;
import com.softech.shop.model.Orders;
import com.softech.shop.services.OrderDetailService;
import com.softech.shop.services.OrderService;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Nguyen Tri
 */
@Controller
@RequestMapping("/orderdetails")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderId}")
    public String listOrderDetail(Model model, @PathVariable String orderId) {
        model.addAttribute("listdetail", orderDetailService.findAllOrderDetail(Integer.parseInt(orderId)));
        return "admin/listOrderDetail";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(ModelMap modelMap, @PathVariable String id) {
        Optional<OrderDetails> orderDetails = orderDetailService.findById(Integer.parseInt(id));

        if (orderDetails != null) {
            modelMap.addAttribute("orderDetails", orderDetails.get());
        } else {
        }
        return "admin/addOrSaveOrderDetail";
    }

    //chưa đưa về trang được lưu được
    @PostMapping("/save")
    public String save(ModelMap modelMap, @ModelAttribute @Validated OrderDetails orderDetails, BindingResult result) {
        if (result.hasErrors()) {
            return "/admin/addOrSaveOrderDetail";
        }
        OrderDetails saveOrder = orderDetailService.save(orderDetails);

//        return "redirect:/orderdetails";
//làm tại khi muốn quay về thì chọn nút
        return "admin/addOrSaveOrderDetail";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(ModelMap modelMap,
            @PathVariable String id) {

        orderDetailService.deleteById(Integer.parseInt(id));

        return "redirect:/orderdetails";
    }

}
