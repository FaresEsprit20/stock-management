package com.fares.stock.management.domain.services.strategy;

import com.fares.stock.management.core.exception.ErrorCodes;
import com.fares.stock.management.core.exception.InvalidOperationException;
import com.fares.stock.management.domain.dto.user.UserDto;
import com.fares.stock.management.domain.services.ImgurService;
import com.fares.stock.management.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("userStrategy")
public class SaveUserPhoto implements Strategy<UserDto> {

    private final ImgurService imgurService;
    private final UserService userService;

    @Autowired
    public SaveUserPhoto(ImgurService imgurService, UserService userService) {
        this.imgurService = imgurService;
        this.userService = userService;
    }


    @Override
    public UserDto savePhoto(Integer id, InputStream photo, String titre) {
        UserDto user = userService.findById(id);
        String urlPhoto = imgurService.savePhoto(photo, titre);
        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Error while saving the user Photo ",
                    ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        user.setPhoto(urlPhoto);
        return userService.save(user);
    }


}
