package com.fares.stock.management.domain.services.impl;

import com.fares.stock.management.domain.services.ImgurService;

import java.io.InputStream;

public class ImgurServiceImpl implements ImgurService {


    @Override
    public String savePhoto(InputStream photo, String title) {
        connect();
        UploadMetaData uploadMetaData = new UploadMetaData();
        uploadMetaData.setTitle(title);

        String photoId = flickr.getUploader().upload(photo, uploadMetaData);
        return flickr.getPhotosInterface().getPhoto(photoId).getMedium640Url();
    }


}
