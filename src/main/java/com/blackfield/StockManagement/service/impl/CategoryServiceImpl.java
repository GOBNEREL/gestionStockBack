package com.blackfield.StockManagement.service.impl;

import com.blackfield.StockManagement.criteria.CategoryCriteria;
import com.blackfield.StockManagement.domain.Category;
import com.blackfield.StockManagement.domain.enumeration.SourceLogs;
import com.blackfield.StockManagement.domain.enumeration.TypeLogs;
import com.blackfield.StockManagement.dto.CategoryDto;
import com.blackfield.StockManagement.exception.EntityNotFoundException;
import com.blackfield.StockManagement.exception.InvalidEntityException;
import com.blackfield.StockManagement.exception.InvalidOperationException;
import com.blackfield.StockManagement.mapper.CategoryMapper;
import com.blackfield.StockManagement.repository.CategoryRepository;
import com.blackfield.StockManagement.service.CategoryService;
import com.blackfield.StockManagement.specification.CategorySpecification;
import com.blackfield.StockManagement.util.MessageNotification;
import com.blackfield.StockManagement.util.MethodUtils;
import com.blackfield.StockManagement.util.ServiceUtil;
import com.blackfield.StockManagement.validator.CategoryValidator;
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
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryValidator categoryValidator;
    private final ServiceUtil serviceUtil;

    private void validateField(CategoryDto dto, String language) {
        List<String> errors = categoryValidator.validate(dto, language);
        if (!errors.isEmpty()) {
            log.error("Category not valid, {}", dto);
            throw new InvalidEntityException("fr".equals(language) ? "la categorie n'est pas valide" : "The Category is invalid", errors);
        }
    }

    @Override
    public MessageNotification createCategory(CategoryDto dto, String language) {
        validateField(dto, language);
        categoryRepository.save(categoryMapper.toEntity(dto));

        if (dto.getId() != null) {
            serviceUtil.addLogs(SourceLogs.GESTION_CATEGORY, TypeLogs.UPDATE, "la categorie" + dto.getCode() + " a été mis a jour avec succès", language);
            return new MessageNotification("1", MethodUtils.isFrench(language) ? "la category a été mis à jour avec succès" : "The category was updated successfully");
        }
        serviceUtil.addLogs(SourceLogs.GESTION_CATEGORY, TypeLogs.ENREGISTREMENT, "la categorie" + dto.getCode() + "a été enregistré avec succès", language);
        return new MessageNotification("1", MethodUtils.isFrench(language) ? "la category a été enregistré avec succès" : "The category was saved successfully");

    }

    @Override
    public List<CategoryDto> findCategoryByCriteria(CategoryCriteria criteria, String language) {
        Pageable p = MethodUtils.findAllByCriteria(criteria.getClassement(), criteria.getTypeClassement(), criteria.getNombreDeResultat(), criteria);
        return categoryMapper.fromEntities(
                categoryRepository.findAll(CategorySpecification.getSpecification(criteria), p).getContent());
    }

    @Override
    public CategoryDto findByCode(String code, String language) {
        if (Strings.isNullOrEmpty(code)) {
            throw new InvalidOperationException(MethodUtils.isFrench(language) ?
                    "Le code ne peut pas être nul" :
                    "the given code can not be must null");
        }
        return categoryRepository.findByCode(code)
                .map(categoryMapper::fromEntity).orElseThrow(
                        () -> new EntityNotFoundException(MethodUtils.isFrench(language) ? "La categorie n'a pas été trouvée"
                                : "The category was not found")
                );
    }

    @Override
    public CategoryDto findById(Long id, String language) {
        if (id == null) {
            throw new InvalidOperationException(MethodUtils.isFrench(language) ?
                    "L'Id ne peut pas être null" :
                    "the given Id can not be must null");
        }

        Optional<Category> ets = categoryRepository.findById(id);

        if (ets.isPresent()) {
            return categoryMapper.fromEntity(ets.get());
        } else {
            throw new EntityNotFoundException(MethodUtils.isFrench(language) ?
                    "Aucune categorie présent avec l'ID spécifié" :
                    "No category present with specify ID");
        }
    }
}
