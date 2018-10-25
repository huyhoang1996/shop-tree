package com.softech.shop.dao;

import com.softech.shop.model.Employees;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nguyen Tri
 */
@Repository
@Transactional
public class AppEmployeeDao {

    @Autowired
    private EntityManager entityManager;

    public Employees findUserAccount(@Param("loginName")String loginName) {
        try {
//            String sql = "Select e from " + Employees.class.getName() + " e "
//                    + " Where e.loginName = :loginName ";
            String sql =String.format("SELECT e FROM Employees e WHERE e.status=true and e.loginName = '%s' ", loginName) ;
//            String sql =("SELECT e FROM Employees e WHERE e.status=true and e.loginName = 'trumtintac'") ;

            Query query = entityManager.createQuery(sql, Employees.class);
//            query.setParameter("loginName", loginName);

            return (Employees) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
