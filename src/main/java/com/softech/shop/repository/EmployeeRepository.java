/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.repository;

import com.softech.shop.model.Employees;
import java.util.List;
import javax.persistence.Tuple;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nguyen Tri
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employees, Integer> {

    @Query("SELECT e FROM Employees e WHERE e.status=true")
    List<Employees> findAllEmployee();

    @Query("SELECT e FROM Employees e WHERE e.status=false")
    List<Employees> findAllEmployeeLock();
    
    @Query("Select e from Employees e where e.loginName =:loginName and e.status=true")
    public Employees findUserAccount(@Param("loginName") String loginName);
}
