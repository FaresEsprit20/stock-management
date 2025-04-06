package com.fares.stock.management.domain.services.strategy;

import com.fares.stock.management.core.exception.ErrorCodes;
import com.fares.stock.management.core.exception.InvalidOperationException;
import com.fares.stock.management.domain.dto.customer.CustomerDto;
import com.fares.stock.management.domain.services.CustomerService;
import com.fares.stock.management.domain.services.ImgurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("customerStrategy")
public class SaveCustomerPhoto implements Strategy<CustomerDto> {

    private final ImgurService imgurService;
    private final CustomerService customerService;

    @Autowired
    public SaveCustomerPhoto(ImgurService imgurService, CustomerService customerService) {
        this.imgurService = imgurService;
        this.customerService = customerService;
    }

    @Override
    public CustomerDto savePhoto(Integer id, InputStream photo, String title) {
        CustomerDto customer = customerService.findById(id);
        String urlPhoto = imgurService.savePhoto(photo, title);
        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Error while saving the customer Photo ",
                    ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        customer.setPhoto(urlPhoto);
        return customerService.save(customer);
    }


}
