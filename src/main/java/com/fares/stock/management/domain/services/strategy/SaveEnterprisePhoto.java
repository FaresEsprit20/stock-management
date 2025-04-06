package com.fares.stock.management.domain.services.strategy;

import com.fares.stock.management.core.exception.ErrorCodes;
import com.fares.stock.management.core.exception.InvalidOperationException;
import com.fares.stock.management.domain.dto.enterprise.EnterpriseDto;
import com.fares.stock.management.domain.dto.supplier.SupplierDto;
import com.fares.stock.management.domain.services.EnterpriseService;
import com.fares.stock.management.domain.services.ImgurService;
import com.fares.stock.management.domain.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("enterpriseStrategy")
public class SaveEnterprisePhoto implements Strategy<EnterpriseDto> {

    private final ImgurService imgurService;
    private final EnterpriseService enterpriseService;

    @Autowired
    public SaveEnterprisePhoto(ImgurService imgurService, EnterpriseService enterpriseService) {
        this.imgurService = imgurService;
        this.enterpriseService = enterpriseService;
    }




    @Override
    public EnterpriseDto savePhoto(Integer id, InputStream photo, String title)  {
       EnterpriseDto enterpriseDto = enterpriseService.findById(id);
        String urlPhoto = imgurService.savePhoto(photo, title);
        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Error while saving the enterprise Photo", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        enterpriseDto.setPhoto(urlPhoto);
        return enterpriseService.save(enterpriseDto);
    }


}