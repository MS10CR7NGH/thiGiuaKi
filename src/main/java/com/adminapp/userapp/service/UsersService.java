package com.adminapp.userapp.service;

import com.adminapp.userapp.model.UserDTO;

import java.util.List;

public interface UsersService {
    String createOrUpdateUsers(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(String id);
    String deleteUserById(String id);
}
