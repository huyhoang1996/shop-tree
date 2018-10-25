/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.servicesimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softech.shop.model.Images;
import com.softech.shop.repository.ImageRepository;
import com.softech.shop.services.ImageService;

/**
 *
 * @author Nguyen Tri
 */
@Service
public class ImageServiceImpl implements ImageService{
	@Autowired
	private ImageRepository imageRepository;

	@Override
	public List<Images> findAll() {
		return (List<Images>) imageRepository.findAll();
	}

	@Override
	public Images save(Images p) {
		return imageRepository.save(p);
	}

	@Override
	public List<Images> findByName(String name) {
		return (List<Images>) ((ImageService) imageRepository).findByName(name);
	}

	@Override
	public List<Images> findByProductId(Integer id) {
		return imageRepository.findByProductId(id);
	}

	@Override
	public void deleteByProductId(Integer id) {
		imageRepository.deleteByProductId(id);
	}

	@Override
	public Optional<Images> findById(Integer id) {
		return imageRepository.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		imageRepository.deleteById(id);
	}   

    @Override
    public Images findByProduct(int productId) {
        return imageRepository.findByProductId(productId);
    }

}
