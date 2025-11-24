package com.adminapp.userapp.api;

import com.adminapp.userapp.model.UserDTO;
import com.adminapp.userapp.service.UploadImageFile;
import com.adminapp.userapp.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api")
public class UserAPI {


    private final UploadImageFile uploadImageFile;

    public UserAPI(UploadImageFile uploadImageFile) {
        this.uploadImageFile = uploadImageFile;
    }

    @Autowired
    private UsersService usersService;

    @PostMapping("/users")
    public String createOrUpdateUser(
            @ModelAttribute UserDTO user, // Nhận dữ liệu JSON (hoặc Form data)
            @RequestParam(value = "file", required = false) MultipartFile file // Nhận tệp ảnh, có thể null
    ) throws IOException { // Thêm throws IOException
        // usersService.createOrUpdateUsers cần nhận cả file
        return usersService.createOrUpdateUsers(user, file);
    }

//    @PostMapping("/users")
//    public String createOrUpdateUser(@RequestBody UserDTO user) {
//        return usersService.createOrUpdateUsers(user);
//    }

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

    @PostMapping("/upload/image")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        return uploadImageFile.uploadImageFile(file);
    }

}
