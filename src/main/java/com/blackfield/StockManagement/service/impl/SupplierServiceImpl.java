package com.blackfield.StockManagement.service.impl;

import com.blackfield.StockManagement.criteria.SupplierCriteria;
import com.blackfield.StockManagement.domain.Supplier;
import com.blackfield.StockManagement.domain.enumeration.SourceLogs;
import com.blackfield.StockManagement.domain.enumeration.TypeLogs;
import com.blackfield.StockManagement.dto.SupplierDto;
import com.blackfield.StockManagement.exception.EntityNotFoundException;
import com.blackfield.StockManagement.exception.InvalidEntityException;
import com.blackfield.StockManagement.exception.InvalidOperationException;
import com.blackfield.StockManagement.mapper.SupplierMapper;
import com.blackfield.StockManagement.repository.SupplierRepository;
import com.blackfield.StockManagement.service.SupplierService;
import com.blackfield.StockManagement.specification.SupplierSpecification;
import com.blackfield.StockManagement.util.MessageNotification;
import com.blackfield.StockManagement.util.MethodUtils;
import com.blackfield.StockManagement.util.ServiceUtil;
import com.blackfield.StockManagement.validator.SupplierValidator;
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
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final SupplierValidator supplierValidator;
    private final ServiceUtil serviceUtil;

    private void validateField(SupplierDto dto, String language) {
        List<String> errors = supplierValidator.validate(dto, language);
        if (!errors.isEmpty()) {
            log.error("Supplier not valid, {}", dto);
            throw new InvalidEntityException("fr".equals(language) ? "le fournisseur n'est pas valide" : "The Supplier is invalid", errors);
        }
    }

    @Override
    public MessageNotification create(SupplierDto dto, String language) {
        validateField(dto, language);
        supplierRepository.save(supplierMapper.toEntity(dto));

        if (dto.getId() != null) {
            serviceUtil.addLogs(SourceLogs.GESTION_SUPPLIER, TypeLogs.UPDATE, "le fournisseur" + dto.getCode() + " a été mis a jour avec succès", language);
            return new MessageNotification("1", MethodUtils.isFrench(language) ? "le fournisseur a été mis à jour avec succès" : "The Supplier was updated successfully");
        }
        serviceUtil.addLogs(SourceLogs.GESTION_SUPPLIER, TypeLogs.ENREGISTREMENT, "le fournisseur" + dto.getCode() + "a été enregistré avec succès", language);
        return new MessageNotification("1", MethodUtils.isFrench(language) ? "le fournisseur a été enregistré avec succès" : "The Supplier was saved successfully");

    }

    @Override
    public List<SupplierDto> findByCriteria(SupplierCriteria criteria, String language) {
        Pageable p = MethodUtils.findAllByCriteria(criteria.getClassement(), criteria.getTypeClassement(), criteria.getNombreDeResultat(), criteria);
        return supplierMapper.fromEntities(
                supplierRepository.findAll(SupplierSpecification.getSpecification(criteria), p).getContent());
    }

    @Override
    public SupplierDto findByCode(String code, String language) {
        if (Strings.isNullOrEmpty(code)) {
            throw new InvalidOperationException(MethodUtils.isFrench(language) ?
                    "Le code ne peut pas être nul" :
                    "the given code can not be must null");
        }
        return supplierRepository.findByCode(code)
                .map(supplierMapper::fromEntity).orElseThrow(
                        () -> new EntityNotFoundException(MethodUtils.isFrench(language) ? "Le fournisseur n'a pas été trouvée"
                                : "The supplier was not found")
                );
    }

    @Override
    public SupplierDto findById(Long id, String language) {
        if (id == null) {
            throw new InvalidOperationException(MethodUtils.isFrench(language) ?
                    "L'Id ne peut pas être null" :
                    "the given Id can not be must null");
        }

        Optional<Supplier> ets = supplierRepository.findById(id);

        if (ets.isPresent()) {
            return supplierMapper.fromEntity(ets.get());
        } else {
            throw new EntityNotFoundException(MethodUtils.isFrench(language) ?
                    "Aucun fournisseur présent avec l'ID spécifié" :
                    "No supplier present with specify ID");
        }
    }
}
