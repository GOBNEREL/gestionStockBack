package com.blackfield.StockManagement.service;

import com.blackfield.StockManagement.bean.LogsSearchBean;
import com.blackfield.StockManagement.dto.LogsDto;
import com.blackfield.StockManagement.exception.EntityNotFoundException;
import com.blackfield.StockManagement.exception.InvalidEntityException;
import com.blackfield.StockManagement.exception.InvalidIdException;

import java.util.List;

public interface LogsService {

    LogsDto saveLogs(LogsDto logsDto, String language) throws InvalidEntityException;

    List<LogsDto> findLogsByCriteria(LogsSearchBean searchBean);

    LogsDto findLogsById(Long logsId, String language) throws InvalidIdException, EntityNotFoundException;

}
