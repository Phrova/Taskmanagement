package com.example.Taskmanagement.RController;

import com.example.Taskmanagement.DTO.ResApi;
import com.example.Taskmanagement.DTO.ReqLogin;
import com.example.Taskmanagement.DTO.ResToken;
import com.example.Taskmanagement.Security.CusUserDetail;
import com.example.Taskmanagement.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class RCLauth {

    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<ResApi<ResToken>> login(@RequestBody ReqLogin req) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
            );

            CusUserDetail user = (CusUserDetail) auth.getPrincipal();
            String token = jwtUtil.generateToken(user);
            ResToken res = new ResToken(
                    token, user.getId(), user.getUsername(), user.getEmail(), user.getRoleNames()
            );

            return ResponseEntity.ok(new ResApi<>(200, "Login success", res));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResApi<>(401, "Invalid email or password", null));
        }
    }
}