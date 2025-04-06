package com.fares.stock.management.domain.services.strategy;

import java.io.InputStream;

public interface Strategy<T> {

    T savePhoto(Integer id, InputStream photo, String title);

}
