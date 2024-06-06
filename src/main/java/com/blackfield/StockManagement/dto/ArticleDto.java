package com.blackfield.StockManagement.dto;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDto {

    private Long id;
    private String code;
    private String name;
    private Integer price;
    private String category;
    private String description;
    private boolean status;
}