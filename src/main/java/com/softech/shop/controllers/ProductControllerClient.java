/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.controllers;

import com.softech.shop.model.Products;
import com.softech.shop.services.CategoryService;
import com.softech.shop.services.ImageService;
import com.softech.shop.services.ProductService;
import com.softech.shop.view_model.ProductToView;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author PC
 */
@Controller
@RequestMapping("/users")
public class ProductControllerClient {

    private static int currentPage = 1;
    private static int pageSize = 12;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/products/detail/{productId}")
    public String detail(ModelMap modelMap, @PathVariable String productId) {
        Products product = productService.findById(Integer.parseInt(productId)).get();
        modelMap.addAttribute("product", getProduct(Integer.parseInt(productId)));
        //modelMap.addAttribute("listProduct", productService.findRelationProductByCategoryId(product.get().getProductId(), product.get().getCategoryId().getCategoryId()));
        modelMap.addAttribute("listProductRelation", getRelationProduct(product.getCategoryId().getCategoryId(), product.getProductId()));
        modelMap.addAttribute("categories", categoryService.findAll());
        return "/user/detail";
    }

    @PostMapping("/products/search")
    public String search(ModelMap modelMap, @RequestParam("keyword") String keyword) {
        if (keyword == null || keyword.equals("")) {
            return "redirect:/users/products/list-all";
        } 
        else {
            if(getProductByName(keyword) != null){
                modelMap.addAttribute("categories", categoryService.findAll());
                modelMap.addAttribute("list", getProductByName(keyword));
                modelMap.put("message", "Có " + getProductByName(keyword).size() + " kết quả phù hợp");
                return "/user/list-result";
            }
            else{
                modelMap.put("message", "Không có kết quả phù hợp");
                return "/user/list-result";
            }
        }
    }

    @GetMapping("/products/category/{categoryId}")
    public String findByCategory(Model model, @PathVariable String categoryId) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("categoryName", categoryService.findById(Integer.parseInt(categoryId)).get().getCategoryName());
        model.addAttribute("listProduct", getProductByCategoryId(Integer.parseInt(categoryId)));
        return "/user/list-product";
    }

    @GetMapping("/products/list-all")
    public String listAllProduct(Model model, @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        model.addAttribute("categories", categoryService.findAll());
        page.ifPresent(p -> currentPage = p);
        size.ifPresent(s -> pageSize = s);

        Page<ProductToView> productPage = productService.findPaginated(PageRequest.of(currentPage - 1, pageSize), getAllProduct());
        model.addAttribute("productPage", productPage);
        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "/user/list-all-product";
    }

    //get product and add field imageUrl
    public ProductToView getProduct(int productId) {
        ProductToView productView = new ProductToView();
        Products product = productService.findById(productId).get();
        productView.setProductId(productId);
        productView.setProductName(product.getProductName());
        productView.setCategoryId(product.getCategoryId().getCategoryId());
        productView.setCategoryName(product.getCategoryId().getCategoryName());
        productView.setInputPrice(product.getInputPrice());
        productView.setSellPrice(product.getSellPrice());
        productView.setDescription(product.getDescription());
        productView.setQuantity(product.getQuantity());
        productView.setDiscount(product.getDiscount());
        productView.setImageUrl(imageService.findByProduct(product.getProductId()).getUrl());
        return productView;
    }

    //get all relation product except current product
    public List<ProductToView> getRelationProduct(int categoryId, int productId) {
        List<ProductToView> listRelation = new ArrayList<>();
        List<Products> listFromDb = productService.findByTop(categoryId);
        for (int i = 0; i < listFromDb.size(); i++) {
            if (listFromDb.get(i).getProductId() == productId) {
                continue;
            }
            ProductToView productView = new ProductToView();
            productView.setProductId(listFromDb.get(i).getProductId());
            productView.setProductName(listFromDb.get(i).getProductName());
            productView.setCategoryId(listFromDb.get(i).getCategoryId().getCategoryId());
            productView.setSellPrice(listFromDb.get(i).getSellPrice());
            productView.setImageUrl(imageService.findByProduct(listFromDb.get(i).getProductId()).getUrl().trim());
            listRelation.add(productView);
        }
        return listRelation;
    }

    //get list product by categoryId
    public List<ProductToView> getProductByCategoryId(int categoryId) {
        List<ProductToView> listProduct = new ArrayList<>();
        List<Products> listProductFromDB = productService.findByCategoryId(categoryId);
        for (Products product : listProductFromDB) {
            ProductToView productView = new ProductToView();
            productView.setProductId(product.getProductId());
            productView.setProductName(product.getProductName());
            productView.setSellPrice(product.getSellPrice());
            productView.setImageUrl(imageService.findByProduct(product.getProductId()).getUrl());
            productView.setCategoryId(categoryId);
            listProduct.add(productView);
        }
        return listProduct;
    }

    public List<ProductToView> getAllProduct() {
        List<ProductToView> listToView = new ArrayList<>();
        List<Products> listFromDB = productService.findAll();
        for (Products product : listFromDB) {
            ProductToView productView = new ProductToView();
            productView.setProductId(product.getProductId());
            productView.setProductName(product.getProductName());
            productView.setSellPrice(product.getSellPrice());
            productView.setDiscount(product.getDiscount());
            productView.setCategoryId(product.getCategoryId().getCategoryId());
            productView.setImageUrl(imageService.findByProduct(product.getProductId()).getUrl());
            listToView.add(productView);
        }

        return listToView;
    }

    public List<ProductToView> getProductByName(String keyword) {
        List<ProductToView> list;
        List<Products> listResult = productService.findByName(keyword);
        if (listResult.size() > 0) {
            list = new ArrayList<>();
            for (Products products : listResult) {
                ProductToView productView = new ProductToView();
                productView.setProductId(products.getProductId());
                productView.setProductName(products.getProductName());
                productView.setSellPrice(products.getSellPrice());
                productView.setImageUrl(imageService.findByProduct(products.getProductId()).getUrl());
                productView.setCategoryId(products.getCategoryId().getCategoryId());
                list.add(productView);
            }
        }
        else{
            list = null;
        }
        return list;
    }
}
