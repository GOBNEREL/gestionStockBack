package com.blackfield.StockManagement.criteria;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UtilisateurCriteria {
    private String name;
    private String login;
    private String phone;
    private String email;
    private Boolean active;
    private String orderType;
    private boolean order;
    private Integer limit;
}
