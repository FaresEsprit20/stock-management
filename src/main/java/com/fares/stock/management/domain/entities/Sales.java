package com.fares.stock.management.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "sales")
public class Sales extends AbstractEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "sale_date")
    private Instant saleDate;

    @Column(name = "comment")
    private String comment;

    @Column(name = "enterprise_id")
    private Integer idEnterprise;

    @OneToMany(mappedBy = "sale")
    private List<SaleLine> saleLines;

    public Sales() {}

    public Sales(String code, Instant saleDate, String comment, Integer idEnterprise, List<SaleLine> saleLines) {
        this.code = code;
        this.saleDate = saleDate;
        this.comment = comment;
        this.idEnterprise = idEnterprise;
        this.saleLines = saleLines;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Instant saleDate) {
        this.saleDate = saleDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getIdEnterprise() {
        return idEnterprise;
    }

    public void setIdEnterprise(Integer idEnterprise) {
        this.idEnterprise = idEnterprise;
    }

    public List<SaleLine> getSaleLines() {
        return saleLines;
    }

    public void setSaleLines(List<SaleLine> saleLines) {
        this.saleLines = saleLines;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Sales sales = (Sales) o;
        return Objects.equals(code, sales.code) && Objects.equals(saleDate, sales.saleDate) && Objects.equals(comment, sales.comment) && Objects.equals(idEnterprise, sales.idEnterprise) && Objects.equals(saleLines, sales.saleLines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), code, saleDate, comment, idEnterprise, saleLines);
    }

    @Override
    public String toString() {
        return "Sales{" +
                "code='" + code + '\'' +
                ", saleDate=" + saleDate +
                ", comment='" + comment + '\'' +
                ", idEnterprise=" + idEnterprise +
                ", saleLines=" + saleLines +
                '}';
    }



}
