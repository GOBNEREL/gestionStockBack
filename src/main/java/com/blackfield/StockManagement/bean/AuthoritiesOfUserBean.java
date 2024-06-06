package com.blackfield.StockManagement.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthoritiesOfUserBean {
    String login;
    List<String> authorityOfUser;
    List<String> authorityDoNotOfUser;
}
