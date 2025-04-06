package com.fares.stock.management.domain.services.strategy;

import com.fares.stock.management.core.exception.ErrorCodes;
import com.fares.stock.management.core.exception.InvalidOperationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class StrategyPhotoContext {

    private final BeanFactory beanFactory;
    private Strategy strategy;


    private String context;

    @Autowired
    public StrategyPhotoContext(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object savePhoto(String context, Integer id, InputStream photo, String title) {
        determineContext(context);
        return strategy.savePhoto(id, photo, title);
    }

    private void determineContext(String context) {
        final String beanName = context + "Strategy";
        switch (context) {
            case "product":
                strategy = beanFactory.getBean(beanName, SaveProductPhoto.class);
                break;
            case "customer" :
                strategy = beanFactory.getBean(beanName, SaveCustomerPhoto.class);
                break;
            case "supplier" :
                strategy = beanFactory.getBean(beanName, SaveSupplierPhoto.class);
                break;
            case "enterprise" :
                strategy = beanFactory.getBean(beanName, SaveEnterprisePhoto.class);
                break;
            case "user" :
                strategy = beanFactory.getBean(beanName, SaveUserPhoto.class);
                break;
            default: throw new InvalidOperationException("Unknown Context for saving the Photo ", ErrorCodes.UNKNOWN_CONTEXT);
        }
    }


    public void setContext(String context) {
        this.context = context;
    }


}
