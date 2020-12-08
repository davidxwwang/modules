package com.david.module.util.validation;

import com.david.module.util.validation.annotation.CrossFieldValid;
import com.david.module.util.validation.group.Group1;
import com.david.module.util.validation.group.Group2;

import java.util.Date;

@CrossFieldValid.List({@CrossFieldValid(groups = Group1.class, first = "password", second = "confirmPassword", message = "The password fields must match"),
        @CrossFieldValid(groups = Group2.class, first = "startDate", second = "endDate", message = "结束时间不能小于开始时间")})
public class DemoUserRegistrationForm {

    private String password;


    private String confirmPassword;


    private Date startDate;


    private Date endDate;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
