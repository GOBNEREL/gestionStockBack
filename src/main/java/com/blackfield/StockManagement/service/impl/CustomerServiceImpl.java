package com.blackfield.StockManagement.service.impl;

import com.blackfield.StockManagement.criteria.CustomerCriteria;
import com.blackfield.StockManagement.domain.Customer;
import com.blackfield.StockManagement.domain.enumeration.SourceLogs;
import com.blackfield.StockManagement.domain.enumeration.TypeLogs;

import com.blackfield.StockManagement.dto.CustomerDto;
import com.blackfield.StockManagement.exception.EntityNotFoundException;
import com.blackfield.StockManagement.exception.InvalidEntityException;
import com.blackfield.StockManagement.exception.InvalidOperationException;
import com.blackfield.StockManagement.mapper.CustomerMapper;
import com.blackfield.StockManagement.repository.CustomerRepository;
import com.blackfield.StockManagement.service.CustomerService;
import com.blackfield.StockManagement.specification.CustomerSpecification;
import com.blackfield.StockManagement.util.MessageNotification;
import com.blackfield.StockManagement.util.MethodUtils;
import com.blackfield.StockManagement.util.ServiceUtil;
import com.blackfield.StockManagement.validator.CustomerValidator;
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
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerValidator customerValidator;
    private final ServiceUtil serviceUtil;

    private void validateField(CustomerDto dto, String language) {
        List<String> errors = customerValidator.validate(dto, language);
        if (!errors.isEmpty()) {
            log.error("Customer not valid, {}", dto);
            throw new InvalidEntityException("fr".equals(language) ? "le client n'est pas valide" : "The Customer is invalid", errors);
        }
    }

    @Override
    public MessageNotification create(CustomerDto dto, String language) {
        validateField(dto, language);
        customerRepository.save(customerMapper.toEntity(dto));

        if (dto.getId() != null) {
            serviceUtil.addLogs(SourceLogs.GESTION_CUSTOMER, TypeLogs.UPDATE, "le client" + dto.getCode() + " a été mis a jour avec succès", language);
            return new MessageNotification("1", MethodUtils.isFrench(language) ? "le client a été mis à jour avec succès" : "The customer was updated successfully");
        }
        serviceUtil.addLogs(SourceLogs.GESTION_CUSTOMER, TypeLogs.ENREGISTREMENT, "le client" + dto.getCode() + "a été enregistré avec succès", language);
        return new MessageNotification("1", MethodUtils.isFrench(language) ? "le client a été enregistré avec succès" : "The customer was saved successfully");

    }

    @Override
    public List<CustomerDto> findByCriteria(CustomerCriteria criteria, String language) {
        Pageable p = MethodUtils.findAllByCriteria(criteria.getClassement(), criteria.getTypeClassement(), criteria.getNombreDeResultat(), criteria);
        return customerMapper.fromEntities(
                customerRepository.findAll(CustomerSpecification.getSpecification(criteria), p).getContent());
    }

    @Override
    public CustomerDto findByCode(String code, String language) {
        if (Strings.isNullOrEmpty(code)) {
            throw new InvalidOperationException(MethodUtils.isFrench(language) ?
                    "Le code ne peut pas être nul" :
                    "the given code can not be must null");
        }
        return customerRepository.findByCode(code)
                .map(customerMapper::fromEntity).orElseThrow(
                        () -> new EntityNotFoundException(MethodUtils.isFrench(language) ? "Le client n'a pas été trouvée"
                                : "The customer was not found")
                );
    }

    @Override
    public CustomerDto findById(Long id, String language) {
        if (id == null) {
            throw new InvalidOperationException(MethodUtils.isFrench(language) ?
                    "L'Id ne peut pas être null" :
                    "the given Id can not be must null");
        }

        Optional<Customer> ets = customerRepository.findById(id);

        if (ets.isPresent()) {
            return customerMapper.fromEntity(ets.get());
        } else {
            throw new EntityNotFoundException(MethodUtils.isFrench(language) ?
                    "Aucun client présent avec l'ID spécifié" :
                    "No customer present with specify ID");
        }
    }
}
