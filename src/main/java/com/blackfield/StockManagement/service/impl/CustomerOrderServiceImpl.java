package com.blackfield.StockManagement.service.impl;

import com.blackfield.StockManagement.criteria.CustomerOrderCriteria;
import com.blackfield.StockManagement.criteria.StockCriteria;
import com.blackfield.StockManagement.domain.CustomerOrder;
import com.blackfield.StockManagement.domain.enumeration.SourceLogs;
import com.blackfield.StockManagement.domain.enumeration.TypeLogs;
import com.blackfield.StockManagement.dto.CustomerOrderDto;
import com.blackfield.StockManagement.dto.StockDto;
import com.blackfield.StockManagement.exception.EntityNotFoundException;
import com.blackfield.StockManagement.exception.InvalidEntityException;
import com.blackfield.StockManagement.exception.InvalidOperationException;
import com.blackfield.StockManagement.mapper.CustomerOrderMapper;
import com.blackfield.StockManagement.mapper.StockMapper;
import com.blackfield.StockManagement.repository.CustomerOrderRepository;
import com.blackfield.StockManagement.repository.StockRepository;

import com.blackfield.StockManagement.service.CustomerOrderService;
import com.blackfield.StockManagement.service.StockService;
import com.blackfield.StockManagement.specification.CustomerOrderSpecification;
import com.blackfield.StockManagement.util.MessageNotification;
import com.blackfield.StockManagement.util.MethodUtils;
import com.blackfield.StockManagement.util.ServiceUtil;
import com.blackfield.StockManagement.validator.CustomerOrderValidator;
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
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final StockRepository stockRepository;
    private final StockService stockService;
    private final CustomerOrderMapper customerOrderMapper;
    private final StockMapper stockMapper;
    private final CustomerOrderValidator customerOrderValidator;
    private final ServiceUtil serviceUtil;

    private void validateField(CustomerOrderDto dto, String language) {
        List<String> errors = customerOrderValidator.validate(dto, language);
        if (!errors.isEmpty()) {
            log.error("Customer order not valid, {}", dto);
            throw new InvalidEntityException("fr".equals(language) ? "la commande client n'est pas valide" : "The customer order is invalid", errors);
        }
    }

    @Override
    public MessageNotification create(CustomerOrderDto dto, String language) {
        validateField(dto, language);
        Integer total = dto.getQuantity() * dto.getUnitPrice();
        dto.setTotalPrice(total);
        customerOrderRepository.save(customerOrderMapper.toEntity(dto));

        StockCriteria stockCriteria = new StockCriteria();
        stockCriteria.setClassement(true);
        stockCriteria.setClassement(true);
        stockCriteria.setName(dto.getArticle());
        List<StockDto> stockDtos = stockService.findByCriteria(stockCriteria, language);

        if (stockDtos.isEmpty()) {
            StockDto stockDto = new StockDto();
            stockDto.setName(dto.getArticle());
            stockDto.setQuantity(dto.getQuantity());
            stockRepository.save(stockMapper.toEntity(stockDto));
        }else {
            for (StockDto stockDto : stockDtos) {
                Integer quantity = stockDto.getQuantity() - dto.getQuantity();
                if (quantity > 0) {
                    stockDto.setQuantity(quantity);
                    stockRepository.save(stockMapper.toEntity(stockDto));
                }else {
                    throw new InvalidEntityException("fr".equals(language) ? "le stock insuffisant" : "Not enought stock");
                }
            }
        }

        if (dto.getId() != null) {
            serviceUtil.addLogs(SourceLogs.GESTION_CUSTOMER_ORDER, TypeLogs.UPDATE, "la commande client" + dto.getCode() + " a été mis a jour avec succès", language);
            return new MessageNotification("1", MethodUtils.isFrench(language) ? "la commande client a été mis à jour avec succès" : "The customer order  was updated successfully");
        }
        serviceUtil.addLogs(SourceLogs.GESTION_CUSTOMER_ORDER, TypeLogs.ENREGISTREMENT, "la commande client" + dto.getCode() + "a été enregistré avec succès", language);
        return new MessageNotification("1", MethodUtils.isFrench(language) ? "la commande client a été enregistré avec succès" : "The customer order  was saved successfully");

    }

    @Override
    public List<CustomerOrderDto> findByCriteria(CustomerOrderCriteria criteria, String language) {
        Pageable p = MethodUtils.findAllByCriteria(criteria.getClassement(), criteria.getTypeClassement(), criteria.getNombreDeResultat(), criteria);
        return customerOrderMapper.fromEntities(
                customerOrderRepository.findAll(CustomerOrderSpecification.getSpecification(criteria), p).getContent());
    }

    @Override
    public CustomerOrderDto findByCode(String code, String language) {
        if (Strings.isNullOrEmpty(code)) {
            throw new InvalidOperationException(MethodUtils.isFrench(language) ?
                    "Le code ne peut pas être nul" :
                    "the given code can not be must null");
        }
        return customerOrderRepository.findByCode(code)
                .map(customerOrderMapper::fromEntity).orElseThrow(
                        () -> new EntityNotFoundException(MethodUtils.isFrench(language) ? "La commande client n'a pas été trouvée"
                                : "The customer order was not found")
                );
    }

    @Override
    public CustomerOrderDto findById(Long id, String language) {
        if (id == null) {
            throw new InvalidOperationException(MethodUtils.isFrench(language) ?
                    "L'Id ne peut pas être null" :
                    "the given Id can not be must null");
        }

        Optional<CustomerOrder> ets = customerOrderRepository.findById(id);

        if (ets.isPresent()) {
            return customerOrderMapper.fromEntity(ets.get());
        } else {
            throw new EntityNotFoundException(MethodUtils.isFrench(language) ?
                    "Aucune commande client présent avec l'ID spécifié" :
                    "No customer order present with specify ID");
        }
    }
}
