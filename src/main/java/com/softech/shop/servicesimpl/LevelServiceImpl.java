/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.servicesimpl;

import com.softech.shop.model.Levels;
import com.softech.shop.repository.LevelRepository;
import com.softech.shop.services.LevelService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nguyen Tri
 */
@Service
public class LevelServiceImpl implements LevelService{
    @Autowired
    private LevelRepository levelRepository;

    @Override
    public List<Levels> findAll() {
        return (List<Levels>) levelRepository.findAll();
    }   
}
