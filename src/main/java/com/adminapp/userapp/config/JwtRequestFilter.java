package com.adminapp.userapp.config;

import com.adminapp.userapp.repository.UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsersRepository usersRepository; // Dùng để lấy thông tin user

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Trích xuất token từ Header
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
            } catch (Exception e) {
                // Có thể log lỗi ở đây (ví dụ: token hết hạn)
                logger.warn("JWT processing error: " + e.getMessage());
            }
        }

        // Nếu có username và chưa được xác thực
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Kiểm tra user có tồn tại và token có hợp lệ không
            // (Không cần kiểm tra mật khẩu vì token đã là bằng chứng xác thực)
            if (usersRepository.findByUsername(username) != null) {

                if (jwtUtil.validateToken(jwt, username)) {
                    // Tạo đối tượng xác thực
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            username, null, new ArrayList<>());

                    // Thiết lập xác thực cho Spring Security
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        // Tiếp tục chuỗi filter
        filterChain.doFilter(request, response);
    }
}
