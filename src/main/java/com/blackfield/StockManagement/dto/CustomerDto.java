package com.blackfield.StockManagement.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {

    private Long id;
    private String code;
    private String name;
    private String phoneNumber;
    private boolean status;
}
