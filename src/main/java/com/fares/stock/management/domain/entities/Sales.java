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

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
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



}
