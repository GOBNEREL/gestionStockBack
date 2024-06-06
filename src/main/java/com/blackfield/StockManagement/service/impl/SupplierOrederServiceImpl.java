package com.blackfield.StockManagement.service.impl;

import com.blackfield.StockManagement.criteria.StockCriteria;
import com.blackfield.StockManagement.criteria.SupplierOrderCriteria;
import com.blackfield.StockManagement.domain.SupplierOrder;
import com.blackfield.StockManagement.domain.enumeration.SourceLogs;
import com.blackfield.StockManagement.domain.enumeration.TypeLogs;
import com.blackfield.StockManagement.dto.StockDto;
import com.blackfield.StockManagement.dto.SupplierOrderDto;
import com.blackfield.StockManagement.exception.EntityNotFoundException;
import com.blackfield.StockManagement.exception.InvalidEntityException;
import com.blackfield.StockManagement.exception.InvalidOperationException;
import com.blackfield.StockManagement.mapper.StockMapper;
import com.blackfield.StockManagement.mapper.SupplierOrderMapper;
import com.blackfield.StockManagement.repository.StockRepository;
import com.blackfield.StockManagement.repository.SupplierOrderRepository;
import com.blackfield.StockManagement.service.StockService;
import com.blackfield.StockManagement.service.SupplierOrderService;
import com.blackfield.StockManagement.specification.SupplierOrderSpecification;
import com.blackfield.StockManagement.util.MessageNotification;
import com.blackfield.StockManagement.util.MethodUtils;
import com.blackfield.StockManagement.util.ServiceUtil;
import com.blackfield.StockManagement.validator.SupplierOrderValidator;
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
public class SupplierOrederServiceImpl implements SupplierOrderService {

    private final SupplierOrderRepository supplierOrderRepository;
    private final StockRepository stockRepository;
    private final SupplierOrderMapper supplierOrderMapper;
    private final StockMapper stockMapper;
    private final StockService stockService;
    private final SupplierOrderValidator supplierOrderValidator;
    private final ServiceUtil serviceUtil;

    private void validateField(SupplierOrderDto dto, String language) {
        List<String> errors = supplierOrderValidator.validate(dto, language);
        if (!errors.isEmpty()) {
            log.error("Supplier order not valid, {}", dto);
            throw new InvalidEntityException("fr".equals(language) ? "la commande fournisseurn'est pas valide" : "The supplier order is invalid", errors);
        }
    }

    @Override
    public MessageNotification create(SupplierOrderDto dto, String language) {
        validateField(dto, language);
        Integer total = dto.getQuantity() * dto.getUnitPrice();
        dto.setTotalPrice(total);
        supplierOrderRepository.save(supplierOrderMapper.toEntity(dto));

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
                Integer quantity = stockDto.getQuantity() + dto.getQuantity();
                stockDto.setQuantity(quantity);
                stockRepository.save(stockMapper.toEntity(stockDto));
            }
        }

        if (dto.getId() != null) {
            serviceUtil.addLogs(SourceLogs.GESTION_SUPPLIER_ORDER, TypeLogs.UPDATE, "la commande fournisseur" + dto.getCode() + " a été mis a jour avec succès", language);
            return new MessageNotification("1", MethodUtils.isFrench(language) ? "la commande fournisseur a été mis à jour avec succès" : "The supplier order  was updated successfully");
        }
        serviceUtil.addLogs(SourceLogs.GESTION_SUPPLIER_ORDER, TypeLogs.ENREGISTREMENT, "la commande fournisseur" + dto.getCode() + "a été enregistré avec succès", language);
        return new MessageNotification("1", MethodUtils.isFrench(language) ? "la commande fournisseur a été enregistré avec succès" : "The supplier order  was saved successfully");

    }

    @Override
    public List<SupplierOrderDto> findByCriteria(SupplierOrderCriteria criteria, String language) {
        Pageable p = MethodUtils.findAllByCriteria(criteria.getClassement(), criteria.getTypeClassement(), criteria.getNombreDeResultat(), criteria);
        return supplierOrderMapper.fromEntities(
                supplierOrderRepository.findAll(SupplierOrderSpecification.getSpecification(criteria), p).getContent());
    }

    @Override
    public SupplierOrderDto findByCode(String code, String language) {
        if (Strings.isNullOrEmpty(code)) {
            throw new InvalidOperationException(MethodUtils.isFrench(language) ?
                    "Le code ne peut pas être nul" :
                    "the given code can not be must null");
        }
        return supplierOrderRepository.findByCode(code)
                .map(supplierOrderMapper::fromEntity).orElseThrow(
                        () -> new EntityNotFoundException(MethodUtils.isFrench(language) ? "La commande fournisseur n'a pas été trouvée"
                                : "The supplier order was not found")
                );
    }

    @Override
    public SupplierOrderDto findById(Long id, String language) {
        if (id == null) {
            throw new InvalidOperationException(MethodUtils.isFrench(language) ?
                    "L'Id ne peut pas être null" :
                    "the given Id can not be must null");
        }

        Optional<SupplierOrder> ets = supplierOrderRepository.findById(id);

        if (ets.isPresent()) {
            return supplierOrderMapper.fromEntity(ets.get());
        } else {
            throw new EntityNotFoundException(MethodUtils.isFrench(language) ?
                    "Aucune commande fournisseur présent avec l'ID spécifié" :
                    "No supplier order present with specify ID");
        }
    }
}