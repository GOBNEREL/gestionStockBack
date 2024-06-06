package com.blackfield.StockManagement.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierOrderCriteria {
    private String code;
    private String supplierName;
    private String article;
    private Integer totalPrice;
    private LocalDate commandDate;
    private Boolean status;
    private Boolean classement;
    private String typeClassement;
    private Integer nombreDeResultat;
}