/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.controllers;

import com.softech.shop.model.Blogs;
import com.softech.shop.services.BlogService;
import com.softech.shop.view_model.MyFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Nguyen Tri
 */
@Controller
@RequestMapping("blog")
public class BlogController {

    //String path = "E:/imagesBlog/";
    // String path = "/src/main/resources/static/imagesBlog/";
    //xét độ hot
    ArrayList<Integer> list;

    public BlogController() {
        
        list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
    }

    @Autowired
    private BlogService blogService;

    @GetMapping("/")
    public String listBlog(Model model) {
        model.addAttribute("listBlog", blogService.findAll());
        return "admin/listBlog";
    }

    @GetMapping("/new")
    public String addBlog(Model model) {
        model.addAttribute("blog", new Blogs());
        model.addAttribute("listHot", list);
        return "admin/addOrSaveBlog";
    }

    @GetMapping("/edit/{blogId}")
    public String editBlog(Model model, @PathVariable String blogId) {
        model.addAttribute("blog", blogService.findById(Integer.parseInt(blogId)));
        model.addAttribute("listHot", list);
        return "admin/addOrSaveBlog";
    }

    @PostMapping("/save")
    public String saveBlog(Model model, @Valid Blogs blogs, @RequestParam("imageFile") MultipartFile imageFile, @RequestParam(value = "blogId") String blogId) throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File fileUrl = new File(classLoader.getResource("").getFile());
        String pathImage = fileUrl.getPath();
        String pathImageBuild = "";
        pathImage = pathImage.replace("\\target\\classes", "\\src\\main\\resources\\static\\images\\blog");

        if (!imageFile.isEmpty()) {
//            String fileName = imageFile.getOriginalFilename();
//            System.out.println(path + fileName);
//            InputStream ipst = imageFile.getInputStream();
//            Files.copy(ipst, Paths.get(path + fileName), StandardCopyOption.REPLACE_EXISTING);
//            blogs.setImage(fileName);

            byte[] bytes = imageFile.getBytes();
            Path path = Paths.get(pathImage + "\\" + imageFile.getOriginalFilename());
            blogs.setImage("blog/" +imageFile.getOriginalFilename());
            Files.write(path, bytes);
            blogs.setView(10);
            blogs.setNew1(new Date());
            blogService.save(blogs);
            return "redirect:/blog/";
        } else {
            Blogs blogs1 = blogService.findById(Integer.parseInt(blogId));
            Blogs bl = new Blogs();
            bl.setImage(blogs1.getImage());
            return "redirect:/blog/";
        }
    }

    @GetMapping("/delete/{blogId}")
    public String deleteBlog(ModelMap modelMap, @PathVariable String blogId) {
        blogService.deleteById(Integer.parseInt(blogId));
        return "redirect:/blog/";
    }
    //chưa view

    @GetMapping("view/{blogId}")
    public String viewBlog(ModelMap modelMap, @PathVariable String blogId) {
        Blogs blog = blogService.findById(Integer.parseInt(blogId));
        if (blog != null) {
            modelMap.addAttribute("blog", blog);
        } else {
        }
        return "admin/viewBlog";
    }
}
