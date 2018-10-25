/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.view_model;

import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Nguyen Tri
 */
public class MyFile implements Serializable {

    private static final long serialVersionUID = 1L;
    private MultipartFile multipartFile;

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
