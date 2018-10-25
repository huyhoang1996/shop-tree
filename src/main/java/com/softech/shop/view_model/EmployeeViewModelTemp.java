/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.view_model;

/**
 *
 * @author Nguyen Tri
 */
public class EmployeeViewModelTemp {
    private int employeeId;
    private String loginName,passwordOld,passwordNew,confirmPassword;

    public EmployeeViewModelTemp(int employeeId, String loginName, String passwordOld, String passwordNew, String confirmPassword) {
        this.employeeId = employeeId;
        this.loginName = loginName;
        this.passwordOld = passwordOld;
        this.passwordNew = passwordNew;
        this.confirmPassword = confirmPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

   
    public  EmployeeViewModelTemp(){
        
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(String passwordOld) {
        this.passwordOld = passwordOld;
    }

    public String getPasswordNew() {
        return passwordNew;
    }

    public void setPasswordNew(String passwordNew) {
        this.passwordNew = passwordNew;
    }
    
}
