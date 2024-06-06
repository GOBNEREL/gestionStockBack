package com.blackfield.StockManagement.validator;

import com.blackfield.StockManagement.domain.enumeration.SourceLogs;
import com.blackfield.StockManagement.domain.enumeration.TypeLogs;
import com.blackfield.StockManagement.dto.LogsDto;
import com.blackfield.StockManagement.util.MethodUtils;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

public class LogsValidator {

    private LogsValidator() {
    }

    public static List<String> validateLogs(LogsDto logsDto, String language) {
        List<String> errors = new ArrayList<>();

        if (logsDto == null) {
            errors.add(MethodUtils.isFrench(language)
                    ? "Veuillez renseigner tout les  champs obligatoires !!!"
                    : "Please fill in all the required fields !!!");
            return errors;
        }

        validateAgent(logsDto.getAgent(), errors, language);
        validateMessage(logsDto.getMessage(), errors, language);
        validateSourceLogs(logsDto.getSourceLogs(), errors, language);
        validateTypeLogs(logsDto.getTypeLogs(), errors, language);

        return errors;
    }

    private static void validateAgent(String agent, List<String> errors, String language) {
        if (Strings.isNullOrEmpty(agent)) {
            errors.add(MethodUtils.isFrench(language)
                    ? "Veuillez renseigner l\'agent du log !!!"
                    : "Please fill in the agent of the log !!!");
        }
    }

    private static void validateMessage(String message, List<String> errors, String language) {
        if (Strings.isNullOrEmpty(message)) {
            errors.add(MethodUtils.isFrench(language)
                    ? "Veuillez renseigner le message du log !!!"
                    : "Please fill in the message of the log !!!");
        }
    }

    private static void validateSourceLogs(SourceLogs sourceLogs, List<String> errors, String language) {
        if (sourceLogs == null) {
            errors.add(MethodUtils.isFrench(language)
                    ? "Veuillez renseigner la source du log !!!"
                    : "Please fill in the source of the log !!!");
        }
    }

    private static void validateTypeLogs(TypeLogs typeLogs, List<String> errors, String language) {
        if (typeLogs == null) {
            errors.add(MethodUtils.isFrench(language)
                    ? "Veuillez renseigner le type du log !!!"
                    : "Please fill in the type of log !!!");
        }
    }


}