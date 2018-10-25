package com.softech.shop.controllers;

import com.softech.shop.model.Employees;
import com.softech.shop.model.Levels;
import com.softech.shop.model.OrderDetails;
import com.softech.shop.model.Orders;
import com.softech.shop.model.Products;
import com.softech.shop.services.EmployeeService;
import com.softech.shop.services.LevelService;
import com.softech.shop.services.OrderDetailService;
import com.softech.shop.services.OrderService;
import com.softech.shop.services.ProductService;
import com.softech.shop.servicesimpl.WebUtils;
import com.softech.shop.view_model.EmployeeViewModelTemp;
import com.softech.shop.view_model.Statis;
import com.sun.tracing.dtrace.ModuleAttributes;
import java.math.BigInteger;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Nguyen Tri
 */
@Controller
public class homeComtroller {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private LevelService levelService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailService orderDetailService;

    public static String encrytePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public boolean hashPassword(String pass1, String pass2) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(pass1, pass2);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "admin/login";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model, Principal principal) {
        String userName = principal.getName();
        model.addAttribute("countProduct", productService.findAll().size());
        model.addAttribute("countOrder", orderService.findAllOrderCho().size());
        int count = 0;
        for(Products item : productService.findAll()){
            count += Integer.parseInt( item.getView().toString());
        }
        model.addAttribute("countView", count);
        
        long sumPriceOrder = 0;
        long sumInputPrice = 0;
        for( Orders item : orderService.findAll()){
            sumPriceOrder += item.getTotal();
        }
        for(OrderDetails item : orderDetailService.findAll()){
            sumInputPrice += item.getProductId().getInputPrice() * item.getQuantity();
        }
        model.addAttribute("sumPrice", (sumPriceOrder - sumInputPrice));
        
        //thêm
        Integer northeastSales = 17089;
        Integer westSales = 10603;
        Integer midwestSales = 5223;
        Integer southSales = 10111;
        
        
//        int sum = 0;
//        for(OrderDetails item : orderDetailService.findStatics()){
//            sum += item.getQuantity();
//        }
//        
//        Statis statis = new Statis();
//        OrderDetails orderDetails = (OrderDetails) orderDetailService.findStatics();
//        statis.setName(orderDetails.getProductId().getProductName());
//        statis.setTotal(sum);
        
        //model.addAttribute("northeastSales", statis);
        
        model.addAttribute("northeastSales", northeastSales);
        model.addAttribute("southSales", southSales);
        model.addAttribute("midwestSales", midwestSales);
        model.addAttribute("westSales", westSales);
        
        
        //
        List<Integer> inshoreSales = Arrays.asList(4074, 3455, 4112);
        List<Integer> nearshoreSales = Arrays.asList(3222, 3011, 3788);
        List<Integer> offshoreSales = Arrays.asList(7811, 7098, 6455);
         
        model.addAttribute("inshoreSales", inshoreSales);
        model.addAttribute("nearshoreSales", nearshoreSales);
        model.addAttribute("offshoreSales", offshoreSales);
        //
        
        
        return "admin/home";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);

            model.addAttribute("userInfo", userInfo);

            String message = "Xin chào " + principal.getName() //
                    + "<br>Bạn không có quyền truy cập trang này!<br>Nếu bạn cố tình truy cập hệ thống sẽ khóa tài khoản của bạn";
            model.addAttribute("message", message);

        }

        return "admin/403Page";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String updateProfile(Model model, Principal principal) {
        String userName = principal.getName();
        Employees emp = employeeService.findUserAccount(userName);
        model.addAttribute("employee", emp);

        return "admin/updateProfile";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateProfileEmp(Model model, Principal principal, @Valid Employees employees, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/updateProfile";
        }
        String userName = principal.getName();
        Employees emp = employeeService.findUserAccount(userName);
        employees.setStatus(true);
        employees.setLevelId(emp.getLevelId());
        employeeService.save(employees);
        return "redirect:/home";
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String changePassword(Model model) {
        model.addAttribute("changePassword", new EmployeeViewModelTemp());
        return "admin/changePassword";
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String changePasswordSubmit(ModelMap model, Principal principal, @ModelAttribute("changePassword") EmployeeViewModelTemp employeeViewModelTemp) {

        Employees emp = employeeService.findUserAccount(principal.getName());
        String password = emp.getPassword();
        System.out.println(password);
        System.out.println(employeeViewModelTemp.getPasswordOld());
        String passwordOld = encrytePassword(employeeViewModelTemp.getPasswordOld());
        

        String passwordNew = employeeViewModelTemp.getPasswordNew();
        String confirmPassword = employeeViewModelTemp.getConfirmPassword();

        if (passwordNew.equals(confirmPassword)) {
            emp.setPassword(encrytePassword(passwordNew));
            employeeService.save(emp);
            return "redirect:/home";
        } else {
            model.put("erro", "Mật khẩu nhập lại không chính xác");
            return "admin/changePassword";
        }
    }
}
