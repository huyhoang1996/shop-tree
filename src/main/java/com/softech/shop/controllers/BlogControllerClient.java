/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.controllers;

import com.softech.shop.services.BlogService;
import com.softech.shop.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author PC
 */
@Controller
@RequestMapping("/users")
public class BlogControllerClient {
    
    @Autowired
    private BlogService blogService;
    
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping("/blog")
    public String blog(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("blogs", blogService.findAll());
        model.addAttribute("blogSidebar", blogService.findByTop(5));
        return "/user/blog";
    }
    
    @GetMapping("/blog/detail")
    public String details(ModelMap modelMap,@RequestParam("id")String id){
        modelMap.addAttribute("categories", categoryService.findAll());
        modelMap.addAttribute("blogItem",blogService.findById(Integer.parseInt(id)));
        return "/user/blog-detail";
    }
    
}
