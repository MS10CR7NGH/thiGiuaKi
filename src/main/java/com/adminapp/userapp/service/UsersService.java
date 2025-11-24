package com.adminapp.userapp.service;

import com.adminapp.userapp.model.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UsersService {
    String createOrUpdateUsers(UserDTO userDTO, MultipartFile file) throws IOException;
    List<UserDTO> getAllUsers();
    UserDTO getUserById(String id);
    String deleteUserById(String id);
}
