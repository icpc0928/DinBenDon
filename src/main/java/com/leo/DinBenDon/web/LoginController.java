package com.leo.DinBenDon.web;

import com.leo.DinBenDon.domain.User;
import com.leo.DinBenDon.domain.UserRepository;
import com.leo.DinBenDon.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    //根目錄自動跳轉到index頁面
    @GetMapping("/")
    public String index(){
        return "index";
    }

    //註冊頁面
    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("userForm", new UserForm());
        return "register";
    }

    //登入頁面請求
    @GetMapping("/login")
    public String loginPage(){
    return "login";
    }

    //登入請求
    @PostMapping("/login")
    public String loginPost(@RequestParam String userName,
                            @RequestParam String password,
                            HttpSession session){
        User user = userRepository.findByUserNameAndPassword(userName, password);
        if(user != null){
            session.setAttribute("user", user);
            return "index";
        }else{
            return "login";
        }
    }

    //登出請求
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "login";
    }

    //註冊請求
    @PostMapping("/register")
    public String register(@Valid UserForm userForm, BindingResult result){
        //檢查確認密碼
        if(!userForm.confirmPassword()){
            result.rejectValue("confirmPassword", "confirmError", "密碼不符");
        }
        //檢查結果錯誤
        if(result.hasErrors()){
            return "register";
        }

        //定義一個Interface 在UserForm內新增內部類別實作, 然後再包裝成一個方法在
        User user = userForm.convertToUser();
        userRepository.save(user);
        //重導向
        return "redirect:/login";
    }

    @GetMapping("/exception")
    public String testException(){
        throw new RuntimeException("測試異常");
    }


}
