/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.controllers;

import com.softech.shop.model.Customers;
import com.softech.shop.services.CustomerService;
import java.util.List;
import java.util.Optional;
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
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("")
    public String listCustomer(Model model) {
        model.addAttribute("listCustomer", customerService.findAll());
        return "admin/listCustomer";
    }
     @GetMapping("view/{customerId}")
     public String viewCustomer(ModelMap modelMap,@PathVariable String customerId){
         Optional<Customers> cus = customerService.findById(Integer.parseInt(customerId));
         if(cus != null){
             modelMap.addAttribute("customer", cus.get());
         }else{
         }       
         return "admin/viewCustomer";
     }
}
