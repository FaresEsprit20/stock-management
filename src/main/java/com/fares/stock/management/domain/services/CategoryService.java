package com.fares.stock.management.domain.services;

import com.fares.stock.management.domain.dto.category.CategoryDto;
import java.util.List;

public interface CategoryService {

    CategoryDto save(CategoryDto dto);

    CategoryDto findById(Integer id);

    CategoryDto findByCode(String code);

    List<CategoryDto> findAll();

    void delete(Integer id);

}
