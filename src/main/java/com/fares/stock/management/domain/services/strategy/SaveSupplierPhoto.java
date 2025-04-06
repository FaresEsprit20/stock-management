package com.fares.stock.management.domain.services.strategy;


import com.fares.stock.management.core.exception.ErrorCodes;
import com.fares.stock.management.core.exception.InvalidOperationException;
import com.fares.stock.management.domain.dto.supplier.SupplierDto;
import com.fares.stock.management.domain.services.ImgurService;
import com.fares.stock.management.domain.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("supplierStrategy")
public class SaveSupplierPhoto implements Strategy<SupplierDto> {

    private final ImgurService imgurService;
    private final SupplierService supplierService;

    @Autowired
    public SaveSupplierPhoto(ImgurService imgurService, SupplierService supplierService) {
        this.imgurService = imgurService;
        this.supplierService = supplierService;
    }

    @Override
    public SupplierDto savePhoto(Integer id, InputStream photo, String title)  {
        SupplierDto supplier = supplierService.findById(id);
        String urlPhoto = imgurService.savePhoto(photo, title);
        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Error while saving the supplier Photo", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        supplier.setPhoto(urlPhoto);
        return supplierService.save(supplier);
    }


}
