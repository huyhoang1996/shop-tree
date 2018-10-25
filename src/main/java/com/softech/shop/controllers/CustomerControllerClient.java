/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.controllers;


import com.softech.shop.model.Customers;
import com.softech.shop.model.Orders;
import com.softech.shop.services.CategoryService;
import com.softech.shop.services.CustomerService;
import com.softech.shop.services.NotificationService;
import com.softech.shop.services.OrderDetailService;
import com.softech.shop.services.OrderService;
import com.softech.shop.view_model.ChangePasswordViewModel;
import com.softech.shop.view_model.CustomerViewModel;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author PC
 */
@Controller
@RequestMapping("/users")
public class CustomerControllerClient {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/customer/register")
    public String registerForm(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("customer", new CustomerViewModel());
        return "/user/register";
    }

    @PostMapping("/customer/register")
    public String registerSubmit(@ModelAttribute("customer") CustomerViewModel customerViewModel, ModelMap modelMap, HttpSession session) {
        Customers customers;
        for (String email : customerService.listEmail()) {
            if (customerViewModel.getEmail().equals(email)) {
                customers = customerService.findByEmail(email);
                if (customers.getPassword() == null) {
                    if (customerViewModel.getPassword().equals(customerViewModel.getReNewPassword())) {
                        //password == reNewPassword
                        customers.setCustomerName(customerViewModel.getCustomerName());
                        customers.setEmail(customerViewModel.getEmail());
                        customers.setPassword(customerViewModel.getPassword());
                        customers.setPhone(customerViewModel.getPhone());
                        customers.setAddress(customerViewModel.getAddress());
                        customers.setTotalMoney(0L);

                        customerService.save(customers);
                        try {
                            notificationService.sendMailForNewCustomer(customers);
                        } catch (MailException ex) {
                            System.out.println(ex.getMessage());
                        }
                        session.setAttribute("userName", customers.getCustomerName());
                        session.setAttribute("customerId", customers.getCustomerId());
                        return "redirect:/users/";
                    } 
                    else {
                        modelMap.put("message", "Nhập lại mật khẩu không đúng, xin vui lòng thử lại lần nữa!");
                        return "/user/register";
                    }
                }
                else {
                    modelMap.put("message", "This email has been registered, please try another email!");
                    return "/user/register";
                }

            }
        }
        if (customerViewModel.getPassword().equals(customerViewModel.getReNewPassword())) {
            //password == reNewPassword
            customers = new Customers();
            customers.setCustomerName(customerViewModel.getCustomerName());
            customers.setEmail(customerViewModel.getEmail());
            customers.setPassword(customerViewModel.getPassword());
            customers.setPhone(customerViewModel.getPhone());
            customers.setAddress(customerViewModel.getAddress());
            customers.setTotalMoney(0L);

            customerService.save(customers);
            try {
                notificationService.sendMailForNewCustomer(customers);
            } catch (MailException ex) {
                System.out.println(ex.getMessage());
            }
            session.setAttribute("userName", customers.getCustomerName());
            session.setAttribute("customerId", customers.getCustomerId());
            return "redirect:/users/";
        } else {
            modelMap.put("message", "Nhập lại mật khẩu không đúng, xin vui lòng thử lại lần nữa!");
            return "/user/register";
        }
    }

    @GetMapping("/customer/success")
    public String success(ModelMap modelMap) {
        modelMap.addAttribute("categories", categoryService.findAll());
        return "/user/success";
    }

    @GetMapping("/customer/login")
    public String loginForm(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("customer", new Customers());
        return "/user/login";
    }

    @PostMapping("/customer/login")
    public String loginSubmit(@ModelAttribute("customer") Customers customerView, ModelMap modelMap, HttpSession session) {
        Customers customer = customerService.findByEmail(customerView.getEmail());
        if (customer != null) {
            if (customer.getPassword().equals(customerView.getPassword())) {
                //login success
                session.setAttribute("userName", customer.getCustomerName());
                session.setAttribute("customerId", customer.getCustomerId());
                return "redirect:/users/";
            } else {
                //password wrong
                modelMap.put("message", "Wrong password, please try again!");
                return "/user/login";
            }
        } else {
            //wrong email
            modelMap.put("message", "Wrong email, please try again!");
            return "/user/login";
        }
    }

    @GetMapping("/customer/logout")
    public String logout(HttpSession session) {
        if (session.getAttribute("userName") != null) {
            session.removeAttribute("userName");
            session.removeAttribute("customerId");
            return "redirect:/users/customer/login";
        }
        return "redirect:/users/";
    }

    @GetMapping("/customer/forgot-password")
    public String forgotPassword(ModelMap modelMap) {
        modelMap.addAttribute("categories", categoryService.findAll());
        return "/user/forgot-password";
    }

    @PostMapping("/customer/forgot-password")
    public String forgotPasswordSubmit(ModelMap modelMap, @RequestParam("requiredEmail") String requiredEmail) {
        Customers customer = customerService.findByEmail(requiredEmail);
        if (customer == null) {
            modelMap.put("state", "fail");
            modelMap.put("message", "Mail vừa nhập không tồn tại");
            return "/user/forgot-password";
        }
        else {
            notificationService.sendMailForgotPassword(requiredEmail);
            modelMap.put("state", "success");
            modelMap.put("message", "Đã gửi mail xác nhận! Xin vui lòng kiểm tra email của bạn");
            return "/user/forgot-password";
        }
    }

    @GetMapping("/customer/change-password")
    public String changePassword(ModelMap modelMap, HttpSession session) {
        modelMap.addAttribute("categories", categoryService.findAll());
        if (session.getAttribute("customerId") == null) {
            return "redirect:/users/customer/login";
        } 
        else {
            ChangePasswordViewModel customer = new ChangePasswordViewModel();
            customer.setCustomerId(Integer.parseInt(session.getAttribute("customerId").toString()));
            modelMap.addAttribute("customer", customer);
            return "/user/change-password";
        }
    }

    @PostMapping("/customer/change-password")
    public String changePasswordSubmit(@ModelAttribute("customer") ChangePasswordViewModel customer, ModelMap modelMap) {
        String oldPassword = customer.getOldPassword();
        String newPassword = customer.getNewPassword();
        String reNewPassword = customer.getReNewPassword();

        Customers customerDB = customerService.findById(customer.getCustomerId()).get();
        String passwordFromDB = customerDB.getPassword();

        if (oldPassword.equals(passwordFromDB)) {
            //nhập đúng mật khẩu cũ
            if (newPassword.equals(reNewPassword)) {
                //mật khẩu mới trùng với nhập lại mật khẩu mới
                customerDB.setPassword(newPassword);
                customerService.save(customerDB);
                modelMap.put("messageSuccess", "Thay đổi mật khẩu thành công");
                return "/user/change-password";
            } 
            else {
                //mật khẩu mới khác với nhập lại mật khẩu mới
                modelMap.put("messageFailed", "Nhập lại mật khẩu không đúng");
                return "/user/change-password";
            }
        }
        else {
            // nhập sai mật khẩu cũ
            modelMap.put("messageFailed", "Mật khẩu cũ không đúng");
            return "/user/change-password";
        }
    }

    @GetMapping("/customer/edit")
    public String profile(ModelMap modelMap, HttpSession session) {
        modelMap.addAttribute("categories", categoryService.findAll());
        if (session.getAttribute("customerId") == null) {
            return "redirect:/users/customer/login";
        } else {
            Customers customers = customerService.findById(Integer.parseInt(session.getAttribute("customerId").toString())).get();
            if (customers != null) {
                modelMap.addAttribute("customer", customers);
                return "/user/profile";
            } else {
                return "/user/error";
            }
        }
    }

    @PostMapping("/customer/edit")
    public String profileSubmit(@ModelAttribute("customer") Customers customers, ModelMap modelMap, HttpSession session) {
        Customers customerToDB = customerService.findById(customers.getCustomerId()).get();
        customerToDB.setCustomerName(customers.getCustomerName());
        customerToDB.setPhone(customers.getPhone());
        customerToDB.setAddress(customers.getAddress());
        customerService.save(customerToDB);
        modelMap.put("message", "Thay đổi thông tin thành công!");
        modelMap.addAttribute("customer", customerToDB);
        session.setAttribute("userName", customerToDB.getCustomerName());
        return "/user/profile";
    }

    @GetMapping("/customer/check-order")
    public String checkOrder(ModelMap modelMap, HttpSession session) {
        if (session.getAttribute("userName") == null) {
            return "redirect:/users/customer/check-order-without-login";
        } else {
            int customerId = Integer.parseInt(session.getAttribute("customerId").toString());
            List<Orders> listOrder = orderService.findByCustomerId(customerId);
            modelMap.addAttribute("categories", categoryService.findAll());
            modelMap.addAttribute("listOrder", listOrder);
            return "/user/check-order";
        }
    }

    @GetMapping("/customer/check-order-detail")
    public String checkOrderDetails(ModelMap modelMap, HttpSession session, @Param("orderCode") String orderCode) {
        if (session.getAttribute("userName") == null) {
            return "redirect:/users/customer/login";
        } 
        else {
            modelMap.addAttribute("categories", categoryService.findAll());
            Orders order = orderService.findByOrderCode(orderCode);
            modelMap.addAttribute("order", order);
            modelMap.addAttribute("listOrderDetail", orderDetailService.findByOrderId(order.getOrderId()));
            return "/user/check-order-detail";
        }
    }

    @GetMapping("/customer/check-order-without-login")
    public String checkOrderWithoutLogin(ModelMap modelMap,HttpSession session) {
        if (session.getAttribute("userName") != null) {
            return "redirect:/users/customer/check-order";
        } 
        else{
            modelMap.addAttribute("categories", categoryService.findAll());
            modelMap.put("orderCode","");
            return "/user/check-order-without-login";
        }
    }
    
    @PostMapping("/customer/check-order-without-login")
    public String checkOrderWithoutLoginSubmit(ModelMap modelMap,@Param("orderCode")String orderCode) {
        Orders order = orderService.findByOrderCode(orderCode);
        if(order != null){
            modelMap.addAttribute("order",order);
            modelMap.addAttribute("listOrderDetail", orderDetailService.findByOrderId(order.getOrderId()));
            modelMap.put("orderCode",orderCode);
            return "/user/check-order-without-login";
        }
        else{
            modelMap.put("message", "Cannot find your order code, please try again!");
            modelMap.put("orderCode",orderCode);
            return "/user/check-order-without-login";
        }
    }
}
