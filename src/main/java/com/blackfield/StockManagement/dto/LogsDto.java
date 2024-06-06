package com.blackfield.StockManagement.dto;

import com.blackfield.StockManagement.domain.enumeration.SourceLogs;
import com.blackfield.StockManagement.domain.enumeration.TypeLogs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogsDto {

    private Long id;

    private LocalDateTime dateoperation;

    private String message;

    private TypeLogs typeLogs;

    private SourceLogs sourceLogs;

    private String agent;

}
