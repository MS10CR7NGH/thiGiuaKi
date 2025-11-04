//package com.adminapp.userapp.service;
//
//import io.minio.BucketExistsArgs;
//import io.minio.MakeBucketArgs;
//import io.minio.MinioClient;
//import io.minio.PutObjectArgs;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import java.util.UUID; // Thêm import này
//
//@Service
//public class FileService {
//
//    @Autowired
//    private MinioClient minioClient;
//
//    @Value("${minio.bucket}")
//    private String bucket;
//
//    @Value("${minio.url}")
//    private String minioUrl;
//
//    public String uploadFile(MultipartFile file) throws Exception {
//
//        boolean found = minioClient.bucketExists(
//                BucketExistsArgs.builder().bucket(bucket).build()
//        );
//
//        if (!found) {
//            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
//        }
//
//        // 1. Tạo tên file duy nhất
//        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
//
//        minioClient.putObject(
//                PutObjectArgs.builder()
//                        .bucket(bucket)
//                        .object(fileName) // Sử dụng tên file duy nhất
//                        .stream(file.getInputStream(), file.getSize(), -1)
//                        .contentType(file.getContentType())
//                        .build()
//        );
//
//        // 2. Trả về URL đầy đủ để truy cập file
//        // URL = minio.url + / + minio.bucket + / + fileName
//        return minioUrl + "/" + bucket + "/" + fileName;
//    }
//}