package com.blackfield.StockManagement.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleCriteria {
    private String code;
    private String name;
    private Integer price;
    private String category;
    private Boolean status;
    private Boolean classement;
    private String typeClassement;
    private Integer nombreDeResultat;
}