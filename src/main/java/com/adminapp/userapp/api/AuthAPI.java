package com.adminapp.userapp.api;

import com.adminapp.userapp.config.JwtUtil;
import com.adminapp.userapp.entity.UserCollection;
import com.adminapp.userapp.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map; // <-- Import

@RestController
@RequestMapping("/api")
public class AuthAPI {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/auth/login")

    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {

        UserCollection user = usersRepository.findByUsername(username);

        if (user == null) {
            // Trả về lỗi 401
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
        if (!user.getPassword().equals(password)) {
            // Trả về lỗi 401
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong password");
        }


        final String token = jwtUtil.generateToken(user.getUsername());


        return ResponseEntity.ok(Map.of("token", token));
    }
}
