/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.view_model;

/**
 *
 * @author DELL
 */
public class Statis {
    private String name;
    private int total;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Statis(String name, int total) {
        this.name = name;
        this.total = total;
    }
    public Statis(){
        
    }
}
