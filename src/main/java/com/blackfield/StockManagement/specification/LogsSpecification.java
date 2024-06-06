package com.blackfield.StockManagement.specification;

import com.blackfield.StockManagement.criteria.LogsCriteria;
import com.blackfield.StockManagement.domain.LogDomain;
import com.blackfield.StockManagement.domain.enumeration.SourceLogs;
import com.blackfield.StockManagement.domain.enumeration.TypeLogs;
import com.blackfield.StockManagement.util.MethodUtils;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.blackfield.StockManagement.util.Constants.*;


public class LogsSpecification {

    private LogsSpecification() {
    }

    @Transient
    private static Specification<LogDomain> withTypeLogs(TypeLogs typeLogs) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(TYPE_LOGS), typeLogs);
    }

    @Transient
    private static Specification<LogDomain> withSourceLogs(SourceLogs sourceLogs) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(SOURCE_LOGS), sourceLogs);
    }

    @Transient
    private static Specification<LogDomain> withAgent(String agent) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(AGENT), agent);
    }

    @Transient
    private static Specification<LogDomain> withLogsDateBefore(String dateDebutLogs) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            LocalDateTime datetimeBefore = MethodUtils.convertorDateTime00H00(dateDebutLogs);
            return criteriaBuilder.greaterThanOrEqualTo(root.get(DATE_LOGS), datetimeBefore);
        };
    }

    @Transient
    private static Specification<LogDomain> withLogsDateAfter(String dateFinLogs) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            LocalDateTime datetimeAfter = MethodUtils.convertorDateTime00H00(dateFinLogs);
            return criteriaBuilder.lessThanOrEqualTo(root.get(DATE_LOGS), datetimeAfter);
        };
    }


    @Transient
    private static Specification<LogDomain> orderByIdDesc() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.orderBy(criteriaBuilder.desc(root.get(LOGS_ID))).getRestriction();
    }

    @Transient
    private static Specification<LogDomain> orderByIdAsc() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.orderBy(criteriaBuilder.asc(root.get(LOGS_ID))).getRestriction();
    }

    public static Specification<LogDomain> getSpecification(LogsCriteria logsCriteria) {
        Specification<LogDomain> logsSpecification = (root, query, criteriabluilder) -> null;

        if (logsCriteria.isOrder()) {
            logsSpecification = Objects.requireNonNull(Specification.where(logsSpecification)).and(LogsSpecification.orderByIdDesc());
        }

        if (!logsCriteria.isOrder()) {
            logsSpecification = Objects.requireNonNull(Specification.where(logsSpecification)).and(LogsSpecification.orderByIdAsc());
        }

        if (logsCriteria.getAgent() != null) {
            logsSpecification = Objects.requireNonNull(Specification.where(logsSpecification)).and(LogsSpecification.withAgent(logsCriteria.getAgent()));
        }

        if (logsCriteria.getSourceLogs() != null) {
            logsSpecification = Objects.requireNonNull(Specification.where(logsSpecification)).and(LogsSpecification.withSourceLogs(logsCriteria.getSourceLogs()));
        }

        if (logsCriteria.getTypeLogs() != null) {
            logsSpecification = Objects.requireNonNull(Specification.where(logsSpecification)).and(LogsSpecification.withTypeLogs(logsCriteria.getTypeLogs()));
        }

        if (logsCriteria.getDateDebutLogs() != null) {
            logsSpecification = Objects.requireNonNull(Specification.where(logsSpecification)).and(LogsSpecification.withLogsDateBefore(logsCriteria.getDateDebutLogs()));
        }

        if (logsCriteria.getDateFinLogs() != null) {
            logsSpecification = Objects.requireNonNull(Specification.where(logsSpecification)).and(LogsSpecification.withLogsDateAfter(logsCriteria.getDateFinLogs()));
        }

        return logsSpecification;
    }

}