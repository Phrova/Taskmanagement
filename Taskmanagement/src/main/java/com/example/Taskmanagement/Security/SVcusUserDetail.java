package com.example.Taskmanagement.Security;

import com.example.Taskmanagement.Model.User;
import com.example.Taskmanagement.Repo.RPuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SVcusUserDetail implements UserDetailsService {
    @Autowired private RPuser userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new CusUserDetail(user);
    }
}