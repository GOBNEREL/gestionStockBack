package com.blackfield.StockManagement.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserSaveBean {

    private Long id;
    private String name;
    private String login;
    private String password;
    private String address;
    private boolean activated;
    private String email;
    private String phone;
}
