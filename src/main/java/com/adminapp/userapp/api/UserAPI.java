package com.adminapp.userapp.api;

import com.adminapp.userapp.model.UserDTO;
import com.adminapp.userapp.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserAPI {

    @Autowired
    private UsersService usersService;
    @PostMapping("/users")
    public String createOrUpdateUser(@RequestBody UserDTO user) {
        return usersService.createOrUpdateUsers(user);
    }

    @GetMapping("/all/users")
    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOList = usersService.getAllUsers();
        return userDTOList;
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable String id) {
        UserDTO userDTO = usersService.getUserById(id);
        return userDTO;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) {
        return usersService.deleteUserById(id);
    }
}
