/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.services;

import com.softech.shop.model.Employees;
import java.util.List;
import java.util.Optional;
import javax.persistence.Tuple;

/**
 *
 * @author Nguyen Tri
 */
public interface EmployeeService {


    List<Employees> findAllEmployee();
    
    List<Employees> findAllEmployeeLock();

    List<Employees> findAll();

//    List<Employees> findEmployeeAll();
    Optional<Employees> findById(Integer id);

    Employees save(Employees p);

    void deleteById(Integer id);
    
    public Employees findUserAccount(String loginName);

}
