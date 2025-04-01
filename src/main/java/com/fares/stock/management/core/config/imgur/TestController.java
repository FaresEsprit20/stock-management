package com.fares.stock.management.core.config.imgur;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/test")
public class TestController {

    private final ImgurConfig imgurConfig;

    public TestController(ImgurConfig imgurConfig) {
        this.imgurConfig = imgurConfig;
    }

    @GetMapping("/keys")
    public String testKeys() {
        return "Client ID: " + imgurConfig.getClientId() +
                ", Client Secret: " + imgurConfig.getClientSecret();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPhoto(@RequestParam("file") MultipartFile file) {
        // Logic to handle file upload
        return ResponseEntity.ok("Image uploaded successfully: https://imgur.com/test-image");
    }


}

