package io.caden.transformers.mx.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "mx_merchant_category")
public class MxMerchantCategory {
    @Id
    @Column(name = "merchant_category_code", unique = true)
    private Integer merchantCategoryCode;

    @Column(name = "merchant_category")
    private String merchantCategory;
}
