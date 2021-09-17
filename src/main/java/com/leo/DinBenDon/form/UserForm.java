package com.leo.DinBenDon.form;

import com.leo.DinBenDon.domain.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserForm {

    public static final String PHONE_REG = "^09[0-9]{8}$";

    @NotBlank(message = "用戶名不得為空") //該值不得為空
    private String userName;

    @Length(min = 6, message = "密碼至少需要6位數")   //長度限制
    private String password;
    @Pattern(regexp = PHONE_REG, message = "請輸入正確手機號碼(09開頭)")
    private String phone;
    @Email
    private String email;
    @NotBlank
    private String confirmPassword;
    @NotBlank
    private String name;
    public UserForm(){

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //檢查密碼是否一致
    public boolean confirmPassword(){
        if(this.confirmPassword == null || this.password == null)
            return false;

        return this.password.equals(this.confirmPassword);
    }

    public User convertToUser(){
        User user = new UserFormConvert().convert(this);
        return user;
    }

    private static class UserFormConvert implements FormConvert<UserForm, User>{
        @Override
        public User convert(UserForm userForm) {
            User user = new User();
            //原數據物件 複製到 目標物件
            BeanUtils.copyProperties(userForm, user);
            return user;
        }
    }
}
