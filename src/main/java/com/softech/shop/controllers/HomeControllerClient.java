/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.controllers;


import com.softech.shop.model.Products;
import com.softech.shop.services.BlogService;
import com.softech.shop.services.CategoryService;
import com.softech.shop.services.ImageService;
import com.softech.shop.services.ProductService;
import com.softech.shop.view_model.ProductToView;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author PC
 */
@Controller
@RequestMapping("/users")
public class HomeControllerClient {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;
    
    @Autowired
    private ImageService imageService;
    
    @Autowired
    private BlogService blogService;
    
    @GetMapping("")
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("categories", categoryService.findAll());
        modelMap.addAttribute("listCactus", getProduct(7));
        modelMap.addAttribute("listSucculent", getProduct(6));
        modelMap.addAttribute("listTreeInHouse", getProduct(5));
        modelMap.addAttribute("blogFooter",blogService.findByTopOrderByHot(6));
        modelMap.addAttribute("blogSidebar", blogService.findByTop(5));
        return "/user/index";
    }

    public List<ProductToView> getProduct(int categoryId){
        List<ProductToView> list = new ArrayList<>();
        List<Products> listFromDb = productService.findByTop(categoryId);
        for (int i = 0; i < listFromDb.size(); i++) {
            ProductToView productView = new ProductToView();
            productView.setProductId(listFromDb.get(i).getProductId());
            productView.setProductName(listFromDb.get(i).getProductName());
            productView.setCategoryId(listFromDb.get(i).getCategoryId().getCategoryId());
            productView.setInputPrice(listFromDb.get(i).getInputPrice());
            productView.setSellPrice(listFromDb.get(i).getSellPrice());
            productView.setImageUrl(imageService.findByProduct(listFromDb.get(i).getProductId()).getUrl());
            list.add(productView);
        }
        return list;
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "/user/contact";
    }

    @GetMapping("/introduction")
    public String introduction(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "/user/introduction";
    }

    @GetMapping("/return")
    public String returnProduct(Model model){
        model.addAttribute("categories", categoryService.findAll());
        return "/user/return";
    }
    
    @GetMapping("/security")
    public String security(Model model){
        model.addAttribute("categories", categoryService.findAll());
        return "/user/security";
    }
    
    @GetMapping("/shopping-guide")
    public String shoppingGuide(Model model){
        model.addAttribute("categories", categoryService.findAll());
        return "/user/shopping-guide";
    }
    
    @GetMapping("/payment-guide")
    public String paymentGuide(Model model){
        model.addAttribute("categories", categoryService.findAll());
        return "/user/payment-guide";
    }
    
    @GetMapping("/trading-account")
    public String tradingAccount(Model model){
        model.addAttribute("categories", categoryService.findAll());
        return "/user/trading-account";
    }
}
