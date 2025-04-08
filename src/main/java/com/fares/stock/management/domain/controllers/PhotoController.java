package com.fares.stock.management.domain.controllers;

import com.fares.stock.management.domain.controllers.api.PhotoApi;
import com.fares.stock.management.domain.services.strategy.StrategyPhotoContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class PhotoController implements PhotoApi {

    private final StrategyPhotoContext strategyPhotoContext;

    @Autowired
    public PhotoController(StrategyPhotoContext strategyPhotoContext) {
        this.strategyPhotoContext = strategyPhotoContext;
    }

    @Override
    public Object savePhoto(String context, Integer id, MultipartFile photo, String title) throws IOException {
        return strategyPhotoContext.savePhoto(context, id, photo.getInputStream(), title);
    }

}
