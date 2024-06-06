package com.blackfield.StockManagement.bean;

import com.blackfield.StockManagement.domain.enumeration.SourceLogs;
import com.blackfield.StockManagement.domain.enumeration.TypeLogs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogsSearchBean {
    private SourceLogs sourceLogs;
    private TypeLogs typeLogs;
    private String agent;
    private String dateDebutLogs;
    private String dateFinLogs;
    private boolean order;
    private int resultMax;
}

