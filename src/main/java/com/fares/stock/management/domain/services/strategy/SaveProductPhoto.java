package com.fares.stock.management.domain.services.strategy;

import com.fares.stock.management.core.exception.ErrorCodes;
import com.fares.stock.management.core.exception.InvalidOperationException;
import com.fares.stock.management.domain.dto.product.ProductDto;
import com.fares.stock.management.domain.services.ImgurService;
import com.fares.stock.management.domain.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("productStrategy")
public class SaveProductPhoto implements Strategy<ProductDto> {

    private final ImgurService imgurService;
    private final ProductService productService;

    @Autowired
    public SaveProductPhoto(ImgurService imgurService, ProductService productService) {
        this.imgurService = imgurService;
        this.productService = productService;
    }

    @Override
    public ProductDto savePhoto(Integer id, InputStream photo, String title) {
        ProductDto product = productService.findById(id);
        String urlPhoto = imgurService.savePhoto(photo, title);
        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Error while saving the product Photo ",
                    ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        product.setPhoto(urlPhoto);
        return productService.save(product);
    }


}

