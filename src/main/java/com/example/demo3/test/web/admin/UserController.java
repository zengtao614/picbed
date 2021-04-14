package com.example.demo3.test.web.admin;

import com.example.demo3.test.dao.User;
import com.example.demo3.test.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Version 1.0
 * @Author:zengtao
 * @Date:2021-4-13
 * @Content:
 */
@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping("/getusers")
    public String getUserList(Model model, @RequestParam(name = "likename" ,required = false) String likename){
        List<User> users = userService.getUserList(likename);
        model.addAttribute("users",users);
        return "userlist.html";
    }

}
