package com.adminapp.userapp.api;

import com.adminapp.userapp.model.UserDTO;
//import com.adminapp.userapp.service.FileService;
import com.adminapp.userapp.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//    @Autowired
//    private FileService fileService;
//
//    @PostMapping("/upload")
//    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
//        try {
//            String fileUrl = fileService.uploadFile(file);
//
//            // Trả về một đối tượng JSON chứa URL
//            Map<String, String> response = new HashMap<>();
//            response.put("url", fileUrl);
//
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
//        }
//    }
}
