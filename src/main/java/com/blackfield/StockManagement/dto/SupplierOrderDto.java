package com.blackfield.StockManagement.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierOrderDto {

    private Long id;
    private String code;
    private String supplierName;
    private Integer unitPrice;
    private Integer totalPrice;
    private Integer quantity;
    private String article;
    private boolean status;
    private LocalDate commandDate;
}