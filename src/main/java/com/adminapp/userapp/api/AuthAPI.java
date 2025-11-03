package com.adminapp.userapp.api;

import com.adminapp.userapp.config.JwtUtil; // <-- Import
import com.adminapp.userapp.entity.UserCollection;
import com.adminapp.userapp.repository.UsersRepository;
// import jakarta.servlet.http.HttpServletRequest; // <-- Không cần nữa
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // <-- Import
import org.springframework.http.ResponseEntity; // <-- Import
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // <-- Không cần nữa
// import org.springframework.security.core.Authentication; // <-- Không cần nữa
// import org.springframework.security.core.context.SecurityContextHolder; // <-- Không cần nữa
import org.springframework.web.bind.annotation.*;

// import java.util.ArrayList; // <-- Không cần nữa
import java.util.Map; // <-- Import

@RestController
@RequestMapping("/api")
public class AuthAPI {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtUtil jwtUtil; // <-- Tiêm JwtUtil

    @PostMapping("/auth/login")
    // Thay đổi kiểu trả về từ String sang ResponseEntity<?>
    // Bỏ HttpServletRequest
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

        // --- Bỏ toàn bộ logic tạo session ---
        // Authentication auth = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
        // SecurityContextHolder.getContext().setAuthentication(auth);
        // request.getSession(true);

        // Tạo JWT
        final String token = jwtUtil.generateToken(user.getUsername());

        // Trả về token cho client
        // (Sẽ có dạng: {"token": "ey..."})
        return ResponseEntity.ok(Map.of("token", token));
    }
}
