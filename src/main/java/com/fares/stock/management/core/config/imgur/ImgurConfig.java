package com.fares.stock.management.core.config.imgur;

import com.fares.stock.management.domain.services.ImgurService;
import com.fares.stock.management.domain.services.impl.ImgurServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConfigurationProperties(prefix = "imgur")
public class ImgurConfig {

    @Value("${imgur.client-id}")
    private String clientId;

    @Value("${imgur.client-secret}")
    private String clientSecret;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Bean
    public ImgurService imgurService(RestTemplate restTemplate) {
        return new ImgurServiceImpl(restTemplate, this);
    }



}