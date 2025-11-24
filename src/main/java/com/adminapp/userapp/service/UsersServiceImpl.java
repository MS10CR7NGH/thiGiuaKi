package com.adminapp.userapp.service;

import com.adminapp.userapp.entity.UserCollection;
import com.adminapp.userapp.model.UserDTO;
import com.adminapp.userapp.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    private final UploadImageFile uploadImageFile;


    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UsersServiceImpl(UploadImageFile uploadImageFile) {
        this.uploadImageFile = uploadImageFile;
    }

    @Transactional
    @Override
    public String createOrUpdateUsers(UserDTO userDTO, MultipartFile file) throws IOException {
        // 1. UPLOAD ẢNH và lưu URL
        if (file != null && !file.isEmpty()) {
            String imageUrl = uploadImageFile.uploadImageFile(file);
            // Lưu URL ảnh đã upload (trả về từ Cloudinary) vào DTO
            userDTO.setImage(imageUrl);
        }

        UserCollection userCollection = modelMapper.map(userDTO, UserCollection.class);
        usersRepository.save(userCollection);
        String message;
        if (userDTO.getId() == null) {
            //message = "Created users successfully";
            message = userCollection.getImage();
        }else {
            message = "Updated users successfully";
        }
        return message;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserCollection> userCollection = usersRepository.findAll();
        List<UserDTO> result = userCollection.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
        return result;
    }

    @Override
    public UserDTO getUserById(String id) {
        UserCollection userCollection = usersRepository.findById(id).orElse(null);
        UserDTO userDTO = modelMapper.map(userCollection, UserDTO.class);
        return userDTO;
    }

    @Transactional
    @Override
    public String deleteUserById(String id) {
        boolean exists = usersRepository.existsById(id);
        if(!exists){
            return "User not found";
        }
        usersRepository.deleteById(id);
        return new String("User deleted successfully");
    }

}
