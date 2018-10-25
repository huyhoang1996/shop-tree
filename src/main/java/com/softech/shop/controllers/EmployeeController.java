/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.controllers;

import com.softech.shop.model.Customers;
import com.softech.shop.model.Employees;
import com.softech.shop.services.EmployeeService;
import com.softech.shop.services.LevelService;
import static com.softech.shop.servicesimpl.EncrytedPasswordUtils.encrytePassword;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Nguyen Tri
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LevelService levelService;

    public static String encrytePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new PasswordEncoder() {
//            @Override
//            public String encode(CharSequence cs) {
//                return BCrypt.hashpw(cs.toString(), BCrypt.gensalt(4));
//            }
//
//            @Override
//            public boolean matches(CharSequence cs, String string) {
//                return BCrypt.checkpw(cs.toString(), string);
//            }
//        };      
//    }

    @GetMapping("/")
    public String listEmployee(ModelMap modelMap) {
        modelMap.addAttribute("listEmployee", employeeService.findAllEmployee());
        return "admin/listEmployee";
    }

    @GetMapping("/lock")
    public String listEmployeeLock(ModelMap modelMap) {
        modelMap.addAttribute("listEmployee", employeeService.findAllEmployeeLock());
        return "admin/listEmployeeLock";
    }

    @GetMapping("/new")
    public String addEmployee(Model model) {
        Employees emp = new Employees();
        model.addAttribute("employee", emp);
        model.addAttribute("level", levelService.findAll());

        return "admin/addOrSaveEmployee";
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(Model model, @PathVariable String id) {
        model.addAttribute("employee", employeeService.findById(Integer.parseInt(id)));
        model.addAttribute("level", levelService.findAll());
        return "admin/addOrSaveEmployee";
    }

    @PostMapping("/save")
    public String saveEmployee(Model model, @Valid Employees employees, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("level", levelService.findAll());
            return "admin/addOrSaveEmployee";
        }
        employees.setStatus(true);
        employeeService.save(employees);
        Employees emp = employeeService.findUserAccount(employees.getLoginName());
        emp.setPassword(encrytePassword(emp.getPassword()));
        employeeService.save(emp);
        return "redirect:/employee";

    }

    @GetMapping("view/{employeeId}")
    public String viewCustomer(ModelMap modelMap, @PathVariable String employeeId) {
        Optional<Employees> emp = employeeService.findById(Integer.parseInt(employeeId));
        if (emp != null) {
            modelMap.addAttribute("employee", emp.get());
        } else {
        }
        return "admin/viewEmployee";
    }

    @GetMapping("/updateById/{id}")
    public String updateById(ModelMap modelMap, @PathVariable String id) {
        Employees emp = employeeService.findById(Integer.parseInt(id)).get();

        if (emp.getStatus()) {
            emp.setStatus(false);
        } else {
            emp.setStatus(true);
        }
        employeeService.save(emp);
        return "redirect:/employee";
    }
}
