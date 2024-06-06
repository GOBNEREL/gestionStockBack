package com.blackfield.StockManagement.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private Long id;
    private String code;
    private String name;
    private String description;
    private boolean status;
}