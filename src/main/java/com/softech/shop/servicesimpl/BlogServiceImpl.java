/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.servicesimpl;

import com.softech.shop.model.Blogs;
import com.softech.shop.repository.BlogRepository;
import com.softech.shop.services.BlogService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nguyen Tri
 */
@Service
public class BlogServiceImpl implements BlogService{
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public List<Blogs> findAll() {
        return (List<Blogs>) blogRepository.findAll();
    }

    @Override
    public Optional<Blogs> findById(Integer id) {
        return  blogRepository.findById(id);
    }

    @Override
    public Blogs save(Blogs b) {
        return blogRepository.save(b);
    }

    @Override
    public void deleteById(Integer id) {
        blogRepository.deleteById(id);
    }


    @Override
    public List<Blogs> findByTop(int top) {
        return blogRepository.findByTop(top);
    }


    @Override
    public List<Blogs> findByTopOrderByHot(int top) {
        return blogRepository.findByTopAndOrderByHot(top);
    }

    @Override
    public Blogs findById(int id) {
        return blogRepository.findById(id).get();
    }
    
}
