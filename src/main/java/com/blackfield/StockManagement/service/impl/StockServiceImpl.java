package com.blackfield.StockManagement.service.impl;

import com.blackfield.StockManagement.criteria.StockCriteria;
import com.blackfield.StockManagement.domain.Stock;

import com.blackfield.StockManagement.dto.StockDto;
import com.blackfield.StockManagement.exception.EntityNotFoundException;
import com.blackfield.StockManagement.exception.InvalidOperationException;

import com.blackfield.StockManagement.mapper.StockMapper;
import com.blackfield.StockManagement.repository.StockRepository;
import com.blackfield.StockManagement.service.StockService;

import com.blackfield.StockManagement.specification.StockSpecification;
import com.blackfield.StockManagement.util.MethodUtils;
import com.blackfield.StockManagement.util.ServiceUtil;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final StockMapper stockMapper;
    private final ServiceUtil serviceUtil;


    @Override
    public List<StockDto> findByCriteria(StockCriteria criteria, String language) {
        Pageable p = MethodUtils.findAllByCriteria(criteria.getClassement(), criteria.getTypeClassement(), criteria.getNombreDeResultat(), criteria);
        return stockMapper.fromEntities(
                stockRepository.findAll(StockSpecification.getSpecification(criteria), p).getContent());
    }

    @Override
    public StockDto findByCode(String code, String language) {
        if (Strings.isNullOrEmpty(code)) {
            throw new InvalidOperationException(MethodUtils.isFrench(language) ?
                    "Le code ne peut pas être nul" :
                    "the given code can not be must null");
        }
        return stockRepository.findByCode(code)
                .map(stockMapper::fromEntity).orElseThrow(
                        () -> new EntityNotFoundException(MethodUtils.isFrench(language) ? "L'article n'a pas été trouvée"
                                : "The article was not found")
                );
    }

    @Override
    public StockDto findById(Long id, String language) {
        if (id == null) {
            throw new InvalidOperationException(MethodUtils.isFrench(language) ?
                    "L'Id ne peut pas être null" :
                    "the given Id can not be must null");
        }

        Optional<Stock> ets = stockRepository.findById(id);

        if (ets.isPresent()) {
            return stockMapper.fromEntity(ets.get());
        } else {
            throw new EntityNotFoundException(MethodUtils.isFrench(language) ?
                    "Aucun stock présent avec l'ID spécifié" :
                    "No stock present with specify ID");
        }
    }
}
