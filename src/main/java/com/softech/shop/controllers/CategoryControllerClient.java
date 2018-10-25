/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.controllers;

import com.softech.shop.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author PC
 */
@Controller
public class CategoryControllerClient {
    
    @Autowired
    private CategoryService categoryService;
    
//    @GetMapping(value = "/users")
//    public String listCategory(ModelMap model){
//        model.addAttribute("categories", categoryService.findAll());
//        return "common/header";
//    }
}
