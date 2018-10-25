package com.softech.shop.servicesimpl;

import com.softech.shop.dao.AppEmployeeDao;
import com.softech.shop.dao.AppRoleDao;
import com.softech.shop.model.Employees;
import com.softech.shop.services.EmployeeService;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nguyen Tri
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppEmployeeDao appEmployeeDao;
    @Autowired
    private AppRoleDao appRoleDao;
    
    @Autowired
    private EmployeeService employeeService;

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        Employees emp = appEmployeeDao.findUserAccount(loginName);
        if (emp == null) {
            System.out.println("Employee not found! " + emp);
            throw new UsernameNotFoundException("Employee " + emp + " was not found in the database");
        }

        System.out.println("Found Employee: " + emp.getLoginName());

        // tìm role
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        GrantedAuthority authority = new SimpleGrantedAuthority(emp.getLevelId().getLevelName());
        grantList.add(authority);
        System.out.println(authority);
        
//        List<String> levelName = appRoleDao.getRoleNames(emp.getEmployeeId());
//
//        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
//        if (levelName != null) {
//            for (String role : levelName) {
//                // ROLE_USER, ROLE_ADMIN,..
//                GrantedAuthority authority = new SimpleGrantedAuthority(role);
//                System.out.println("Role is" + role);
//                grantList.add(authority);
//            }
//        }else{
//            System.out.println("Role not found");
//        }

        UserDetails userDetails = (UserDetails) new User(emp.getLoginName(), //
                emp.getPassword(),true,true,true,true,grantList);

        return userDetails;
    }
}

