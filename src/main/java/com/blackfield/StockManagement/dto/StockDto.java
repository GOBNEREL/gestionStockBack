package com.blackfield.StockManagement.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockDto {

    private Long id;
    private String code;
    private String name;
    private Integer quantity;
    private boolean status;
}
