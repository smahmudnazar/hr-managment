package com.example.restapijwt.aop;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Collection;

@Aspect
@Component
public class Checker {

    @Before("@annotation(Check)")
    public boolean value() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
//        for (Role role : user.getRoleList()) {
//            for (PermissionEnum permissionEnum : role.getPermissionEnum()) {
//                if (permissionEnum.name().equals("ADD_COMPANY")) return true;
//            }
//        }
//        return false;
//
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ADD_COMPANY")) {
                return true;
            }
        }
        return false;
    }
}
