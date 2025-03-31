package com.fares.stock.management.domain.repository.jpa;

import com.fares.stock.management.domain.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    Optional<Article> findArticleByCodeArticle(String codeArticle);

    List<Article> findAllByCategoryId(Integer idCategory);


}
