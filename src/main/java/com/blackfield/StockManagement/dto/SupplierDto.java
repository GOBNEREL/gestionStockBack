package com.blackfield.StockManagement.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierDto {

    private Long id;
    private String code;
    private String name;
    private String phoneNumber;
    private String description;
    private boolean status;
}
