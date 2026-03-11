package com.example.Taskmanagement.Security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static CusUserDetail getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (principal instanceof CusUserDetail) ? (CusUserDetail) principal : null;
    }

    public static Long getCurrentUserId() {
        CusUserDetail user = getCurrentUser();
        return user != null ? user.getId() : null;
    }
}