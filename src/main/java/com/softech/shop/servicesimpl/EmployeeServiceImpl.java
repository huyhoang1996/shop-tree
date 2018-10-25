/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.servicesimpl;

import com.softech.shop.model.Employees;
import com.softech.shop.repository.EmployeeRepository;
import com.softech.shop.services.EmployeeService;
import java.util.List;
import java.util.Optional;
import javax.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nguyen Tri
 */
@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;
    
//    @Override
//    public Employees findByUserAndPassword(String loginName, String password) {
//        return employeeRepository.findByUserAndPassword(loginName, password);
//    }

    @Override
    public List<Employees> findAll() {
        return (List<Employees>) employeeRepository.findAll();
    }

    @Override
    public Optional<Employees> findById(Integer id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employees save(Employees p) {
        return employeeRepository.save(p);
    }

    @Override
    public void deleteById(Integer id) {
        employeeRepository.deleteById(id);
    }    

   

//    @Override
//    public List<Employees> findEmployeeAll() {
//        return employeeRepository.findEmployeeAll();
//    }

//    @Override
//    public List<Tuple> findByLevel(String level) {
//        return employeeRepository.findByLevel(Integer.parseInt(level));
//    }

    @Override
    public List<Employees> findAllEmployee() {
        return (List<Employees>) employeeRepository.findAllEmployee();
    }

    @Override
    public List<Employees> findAllEmployeeLock() {
        return employeeRepository.findAllEmployeeLock();
    }

    @Override
    public Employees findUserAccount(String loginName) {
        return employeeRepository.findUserAccount(loginName);
    }

}
