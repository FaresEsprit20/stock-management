package com.fares.stock.management.domain.repository.jpa;

import com.fares.stock.management.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findArticleByCodeProduct(String codeProduct);

    List<Product> findAllByCategoryId(Integer idCategory);


}
