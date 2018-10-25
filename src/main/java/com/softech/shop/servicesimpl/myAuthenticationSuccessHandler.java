/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.servicesimpl;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 *
 * @author Nguyen Tri
 */
public class myAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest hsr, HttpServletResponse hsr1, Authentication a) throws IOException, ServletException {
        
    }

    public String determineTargetUrl(Authentication authentication) {
        boolean isUser = false;
        boolean isAdmin = false;
        boolean isShipper = false;
        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("Employee")) {
                isUser = true;
                break;
            }
            if (grantedAuthority.getAuthority().equals("Shipper")) {
                isShipper = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("Admin")) {
                isAdmin = true;
                break;
            }
        }

        if (isShipper) {
            return "/listOrderShipper.html";
        }
        if (isUser) {
            return "/listCustomer.html";
        } else if (isAdmin) {
            return "/home.html";
        } else {
            throw new IllegalStateException();
        }
    }

}
