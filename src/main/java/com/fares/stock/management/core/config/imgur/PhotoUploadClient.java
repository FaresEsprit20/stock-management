package com.fares.stock.management.core.config.imgur;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class PhotoUploadClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ImgurConfig imgurConfig;

    // Inject ImgurConfig
    public PhotoUploadClient(ImgurConfig imgurConfig) {
        this.imgurConfig = imgurConfig;
    }

    public String uploadPhoto(MultipartFile file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Client-ID " + imgurConfig.getClientId());

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });

        // The full API URL, e.g., https://api.imgur.com/3/image
        String apiUrl = "https://api.imgur.com/3/image";

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Make the API request
        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl, HttpMethod.POST, requestEntity, String.class
        );

        return response.getBody();
    }


}
