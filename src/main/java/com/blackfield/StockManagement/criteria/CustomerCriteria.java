package com.blackfield.StockManagement.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerCriteria {
    private String code;
    private String name;
    private String phoneNumber;
    private Boolean status;
    private Boolean classement;
    private String typeClassement;
    private Integer nombreDeResultat;
}
