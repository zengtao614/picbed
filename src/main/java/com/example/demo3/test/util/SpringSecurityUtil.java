package com.example.demo3.test.util;

import com.example.demo3.test.dao.User;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Version 1.0
 * @Author:zengtao
 * @Date:2021-4-19
 * @Content:
 */
public class SpringSecurityUtil {

    public static User currentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SecurityContextImpl securityContext = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        return ((User)securityContext.getAuthentication().getPrincipal());
    }
}
