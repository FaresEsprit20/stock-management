package com.fares.stock.management.domain.dto.sales;

import com.fares.stock.management.domain.dto.sale_line.SaleLineDto;
import com.fares.stock.management.domain.entities.Sales;

import java.time.Instant;
import java.util.List;

public class SalesDto {

    private String code;
    private Instant saleDate;
    private String comment;
    private Integer idEnterprise;
    private List<SaleLineDto> saleLines;

    // No-args constructor
    public SalesDto() {
    }

    // All-args constructor
    public SalesDto(String code, Instant saleDate, String comment, Integer idEnterprise, List<SaleLineDto> saleLines) {
        this.code = code;
        this.saleDate = saleDate;
        this.comment = comment;
        this.idEnterprise = idEnterprise;
        this.saleLines = saleLines;
    }

    // Getters
    public String getCode() {
        return code;
    }

    public Instant getSaleDate() {
        return saleDate;
    }

    public String getComment() {
        return comment;
    }

    public Integer getIdEnterprise() {
        return idEnterprise;
    }

    public List<SaleLineDto> getSaleLines() {
        return saleLines;
    }

    // Setters
    public void setCode(String code) {
        this.code = code;
    }

    public void setSaleDate(Instant saleDate) {
        this.saleDate = saleDate;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setIdEnterprise(Integer idEnterprise) {
        this.idEnterprise = idEnterprise;
    }

    public void setSaleLines(List<SaleLineDto> saleLines) {
        this.saleLines = saleLines;
    }

    // Static conversion methods
    public static SalesDto fromEntity(Sales sales) {
        if (sales == null) {
            return null;
        }
        List<SaleLineDto> saleLineDtos = sales.getSaleLines() != null ?
                sales.getSaleLines().stream().map(SaleLineDto::fromEntity).toList() : null;
        return new SalesDto(
                sales.getCode(),
                sales.getSaleDate(),
                sales.getComment(),
                sales.getIdEnterprise(),
                saleLineDtos
        );
    }

    public static Sales toEntity(SalesDto salesDto) {
        if (salesDto == null) {
            return null;
        }
        Sales sales = new Sales();
        sales.setCode(salesDto.getCode());
        sales.setSaleDate(salesDto.getSaleDate());
        sales.setComment(salesDto.getComment());
        sales.setIdEnterprise(salesDto.getIdEnterprise());
        sales.setSaleLines(salesDto.getSaleLines() != null ?
                salesDto.getSaleLines().stream().map(SaleLineDto::toEntity).toList() : null);
        return sales;
    }
}
