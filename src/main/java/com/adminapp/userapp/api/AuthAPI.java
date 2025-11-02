package com.adminapp.userapp.api;

import com.adminapp.userapp.entity.UserCollection;
import com.adminapp.userapp.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class AuthAPI {

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/auth/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {

        UserCollection user = usersRepository.findByUsername(username);

        if(user == null) return "User not found";
        if(!user.getPassword().equals(password)) return "Wrong password";

        Authentication auth = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(auth);

        request.getSession(true); // create session → JSESSIONID

        return "Login success";
    }
}
