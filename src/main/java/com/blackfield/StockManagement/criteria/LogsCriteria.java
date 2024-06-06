package com.blackfield.StockManagement.criteria;

import com.blackfield.StockManagement.domain.enumeration.SourceLogs;
import com.blackfield.StockManagement.domain.enumeration.TypeLogs;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class LogsCriteria {

    private SourceLogs sourceLogs;
    private TypeLogs typeLogs;
    private String agent;
    private String dateDebutLogs;
    private String dateFinLogs;
    private boolean order;

}