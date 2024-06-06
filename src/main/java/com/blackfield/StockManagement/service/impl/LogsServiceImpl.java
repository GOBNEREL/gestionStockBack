package com.blackfield.StockManagement.service.impl;

import com.blackfield.StockManagement.bean.LogsSearchBean;
import com.blackfield.StockManagement.criteria.LogsCriteria;
import com.blackfield.StockManagement.domain.LogDomain;
import com.blackfield.StockManagement.dto.LogsDto;
import com.blackfield.StockManagement.exception.EntityNotFoundException;
import com.blackfield.StockManagement.exception.InvalidEntityException;
import com.blackfield.StockManagement.exception.InvalidIdException;
import com.blackfield.StockManagement.mapper.LogsMapper;
import com.blackfield.StockManagement.repository.LogsRepository;
import com.blackfield.StockManagement.repository.UserRepository;
import com.blackfield.StockManagement.service.LogsService;
import com.blackfield.StockManagement.specification.LogsSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.blackfield.StockManagement.validator.LogsValidator.validateLogs;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class LogsServiceImpl implements LogsService {

    private final LogsRepository logsRepository;
    private final LogsMapper logsMapper;
    private final UserRepository userRepository;


    @Override
    public LogsDto saveLogs(LogsDto logsDto, String language) throws InvalidEntityException {
        List<String> errors = validateLogs(logsDto, language);
        if (!errors.isEmpty()) {
            log.error("LogsDto not valid, {}", logsDto);
            throw new InvalidEntityException("fr".equals(language)
                    ? "Le log n'est pas valide !!!"
                    : "The log is not valid !!!", errors);
        }
        return logsMapper.fromEntity(logsRepository.save(logsMapper.toEntity(logsDto)));
    }


    @Override
    public List<LogsDto> findLogsByCriteria(LogsSearchBean searchBean) {
        LogsCriteria criteria = LogsCriteria.builder()
                .sourceLogs(searchBean.getSourceLogs())
                .typeLogs(searchBean.getTypeLogs())
                .agent(searchBean.getAgent())
                .dateDebutLogs(searchBean.getDateDebutLogs())
                .dateFinLogs(searchBean.getDateFinLogs())
                .order(searchBean.isOrder())
                .build();
        Pageable pageable = searchBean.getResultMax() == 0 ? Pageable.unpaged() : PageRequest.of(0, searchBean.getResultMax());
        List<LogDomain> logsList = logsRepository.findAll(LogsSpecification.getSpecification(criteria), pageable).toList();
        return logsList.stream()
                .map(logsMapper::fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    public LogsDto findLogsById(Long logsId, String language) throws InvalidIdException, EntityNotFoundException {
        if (logsId == null) {
            log.error("Logs ID is null !!!");
            throw new InvalidIdException("fr".equals(language)
                    ? "L'ID du log est null !!!"
                    : "The log ID is null !!!");
        }
        return logsRepository.findById(logsId)
                .map(logsMapper::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("fr".equals(language)
                        ? "Aucun log avec ID = " + logsId + " n'a été trouvé !!!"
                        : "No log with ID = " + logsId + " has been found !!!"));
    }

}


