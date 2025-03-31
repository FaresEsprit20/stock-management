package com.fares.stock.management.domain.repository.jpa;

import com.fares.stock.management.domain.entities.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Integer> {

    @Query("select sum(m.quantity) from StockMovement m where m.product.id = :idProduct")
    BigDecimal stockReelProduct(@Param("idProduct") Integer idArticle);

    List<StockMovement> findAllByProductId(Integer idProduct);

}