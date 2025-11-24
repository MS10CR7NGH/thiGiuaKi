package com.adminapp.userapp.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConfigCloudinary {
    @Bean
    public Cloudinary configKey() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dksafd2ld");
        config.put("api_key", "459496672343684");
        config.put("api_secret", "9xe6LCRTOyazqrJY1cwIAvaWJSM");
        return new Cloudinary(config);
    }
}
