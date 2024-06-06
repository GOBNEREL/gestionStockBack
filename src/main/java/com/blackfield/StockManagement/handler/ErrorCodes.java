package com.blackfield.StockManagement.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorCodes {

    private String httpCode;

    private String message;

    private List<String> errors;
}
