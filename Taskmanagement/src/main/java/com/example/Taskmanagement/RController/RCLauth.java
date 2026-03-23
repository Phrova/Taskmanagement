package com.example.Taskmanagement.RController;

import com.example.Taskmanagement.DTO.ResApi;
import com.example.Taskmanagement.DTO.ReqLogin;
import com.example.Taskmanagement.DTO.ResToken;
import com.example.Taskmanagement.Security.CusUserDetail;
import com.example.Taskmanagement.Security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Authentication", description = "Login API")
public class RCLauth {

    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/login")
    @Operation(summary = "Login with email & password", description = "Returns JWT token if credentials are valid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid email or password")
    })
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