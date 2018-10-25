/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.servicesimpl;

import com.softech.shop.model.Categories;
import com.softech.shop.repository.CategoryRepository;
import com.softech.shop.services.CategoryService;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nguyen Tri
 */
@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Categories> findAll() {
		return (List<Categories>) categoryRepository.findAll();
	}

	@Override
	public Categories save(Categories p) {
		return categoryRepository.save(p);
	}

	@Override
	public List<Categories> findByName(String name) {
		return (List<Categories>) ((CategoryService) categoryRepository).findByName(name);
	}

	@Override
	public Optional<Categories> findById(Integer id) {
		return categoryRepository.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		categoryRepository.deleteById(id);
	}   

}
