/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.services;

import com.softech.shop.model.Levels;
import java.util.List;

/**
 *
 * @author Nguyen Tri
 */
public interface LevelService {
    List<Levels> findAll();
}
