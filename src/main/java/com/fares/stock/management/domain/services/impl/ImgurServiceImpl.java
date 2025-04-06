package com.fares.stock.management.domain.services.impl;

import com.fares.stock.management.core.config.imgur.ImgurConfig;
import com.fares.stock.management.domain.services.ImgurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImgurServiceImpl implements ImgurService {

    private final RestTemplate restTemplate;
    private final ImgurConfig imgurConfig;

    @Autowired
    public ImgurServiceImpl(RestTemplate restTemplate, ImgurConfig imgurConfig) {
        this.restTemplate = restTemplate;
        this.imgurConfig = imgurConfig;
    }


    @Override
    public String savePhoto(InputStream photo, String title) {
        String url = "https://api.imgur.com/3/image";

        String base64Image = encodeImageToBase64(photo);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Client-ID " + imgurConfig.getClientId());

        Map<String, String> payload = new HashMap<>();
        payload.put("image", base64Image);
        payload.put("type", "base64");
        payload.put("title", title);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url, HttpMethod.POST, request, Map.class
        );

        Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
        return data.get("link").toString();
    }

    private String encodeImageToBase64(InputStream inputStream) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Failed to encode image", e);
        }
    }



}
