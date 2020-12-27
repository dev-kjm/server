package com.motoo.server.domain.auth.api;

import com.motoo.server.domain.auth.application.AuthService;
import com.motoo.server.domain.auth.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;


    @PostMapping("/oauth/login")
    @ResponseBody
    public LoginResponse login(@RequestParam String email, @RequestParam String pwd, HttpServletResponse response) {
        return new LoginResponse(authService.login(email, pwd, response));
    }

    @PostMapping("/oauth/logout")
    @ResponseBody
    public ResponseEntity logout(HttpServletResponse response) {
        authService.logout(response);

        return ResponseEntity.ok().build();
    }



}
