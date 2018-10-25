/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.dao;

//import com.softech.shop.model.Levels;
import com.softech.shop.model.Employees;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class AppRoleDao {

    @Autowired
    private EntityManager entityManager;

    public List<String> getRoleNames(Integer employeeId) {

         String sql ="Select Levels.levelName from Employees join Levels on Employees.levelId = Levels.levelId "
                 + "where Employees.employeeId =:employeeId";

        Query query = this.entityManager.createQuery(sql, String.class);
        query.setParameter("employeeId", employeeId);
        return query.getResultList();
    }
}
