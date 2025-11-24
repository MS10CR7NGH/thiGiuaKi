package com.adminapp.userapp.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import org.slf4j.Logger; // <-- Thêm import cho Logger
import org.slf4j.LoggerFactory; // <-- Thêm import cho LoggerFactory

@Service
//@RequiredArgsConstructor
//@Slf4j
public class UploadImageFileImpl implements UploadImageFile {

    // 1. THAY THẾ @Slf4j: Khai báo biến log thủ công
    private static final Logger log = LoggerFactory.getLogger(UploadImageFileImpl.class);
    private final Cloudinary cloudinary;

    public UploadImageFileImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImageFile(MultipartFile file) throws IOException {
        assert  file.getOriginalFilename() != null;
        String publicValue = generatatePublicValue(file.getOriginalFilename());
        log.info("publicValue is:{}", publicValue);
        String extension = getFileName(file.getOriginalFilename())[1];
        log.info("extension is:{}", extension);
        File fileUpload = convert(file);
        log.info("fileUpload is:{}", fileUpload);
        cloudinary.uploader().upload(fileUpload, ObjectUtils.asMap("public_id", publicValue));

        //String filePath = cloudinary.url().generate(StringUtils.join(publicValue, ".", extension));
        cleanDisk(fileUpload);
        return cloudinary.url().generate(StringUtils.join(publicValue, ".", extension));
    }

    private File convert(MultipartFile file) throws IOException {
        assert file.getOriginalFilename() != null;
        File convFile = new File(StringUtils.join(generatatePublicValue(file.getOriginalFilename()), getFileName(file.getOriginalFilename())[1]));
        try(InputStream is = file.getInputStream()) {
            Files.copy(is, convFile.toPath());
        }
        return convFile;

    }

    public void cleanDisk(File file) {
        try {
            log.info("cleanDisk is:{}", file.toPath());
            Path filePath = file.toPath();
            Files.delete(filePath);
        } catch (IOException e) {
            log.error("Error");
        }
    }

    public  String generatatePublicValue(String originalName){
        String fileName = getFileName(originalName)[0];
        return StringUtils.join(UUID.randomUUID().toString(), "_", fileName);
    }

    public  String[] getFileName(String originalName){
        return originalName.split("\\.");
    }
}
