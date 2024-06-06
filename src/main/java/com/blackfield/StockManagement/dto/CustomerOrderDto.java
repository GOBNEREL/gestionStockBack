package com.blackfield.StockManagement.dto;

import lombok.*;

import javax.persistence.Column;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerOrderDto {

    private Long id;
    private String code;
    private String customerName;
    private Integer unitPrice;
    private Integer totalPrice;
    private Integer quantity;
    private String article;
    private boolean status;
    private LocalDate commandDate;
}