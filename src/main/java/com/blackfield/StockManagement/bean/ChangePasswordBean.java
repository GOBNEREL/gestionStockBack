package com.blackfield.StockManagement.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordBean {

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
    private String login;
}


