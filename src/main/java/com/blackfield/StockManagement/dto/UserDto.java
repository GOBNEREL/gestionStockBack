package com.blackfield.StockManagement.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String nom;
    private String login;
    private String password;
    private String adresse;
    private String email;
    private String phone;
    private boolean activer;
    private List<RolesDto> roles;
}
