package com.blackfield.StockManagement.service.impl;

import com.blackfield.StockManagement.criteria.ArticleCriteria;
import com.blackfield.StockManagement.criteria.CategoryCriteria;
import com.blackfield.StockManagement.domain.Article;
import com.blackfield.StockManagement.domain.Category;
import com.blackfield.StockManagement.domain.Stock;
import com.blackfield.StockManagement.domain.enumeration.SourceLogs;
import com.blackfield.StockManagement.domain.enumeration.TypeLogs;
import com.blackfield.StockManagement.dto.ArticleDto;
import com.blackfield.StockManagement.dto.CategoryDto;
import com.blackfield.StockManagement.dto.StockDto;
import com.blackfield.StockManagement.exception.EntityNotFoundException;
import com.blackfield.StockManagement.exception.InvalidEntityException;
import com.blackfield.StockManagement.exception.InvalidOperationException;
import com.blackfield.StockManagement.mapper.ArticleMapper;
import com.blackfield.StockManagement.mapper.CategoryMapper;
import com.blackfield.StockManagement.mapper.StockMapper;
import com.blackfield.StockManagement.repository.ArticleRepository;
import com.blackfield.StockManagement.repository.CategoryRepository;
import com.blackfield.StockManagement.repository.StockRepository;
import com.blackfield.StockManagement.service.ArticleService;
import com.blackfield.StockManagement.specification.ArticleSpecification;
import com.blackfield.StockManagement.specification.CategorySpecification;
import com.blackfield.StockManagement.util.MessageNotification;
import com.blackfield.StockManagement.util.MethodUtils;
import com.blackfield.StockManagement.util.ServiceUtil;
import com.blackfield.StockManagement.validator.ArticleValidator;
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
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final ArticleValidator articleValidator;
    private final ServiceUtil serviceUtil;

    private void validateField(ArticleDto dto, String language) {
        List<String> errors = articleValidator.validate(dto, language);
        if (!errors.isEmpty()) {
            log.error("Article not valid, {}", dto);
            throw new InvalidEntityException("fr".equals(language) ? "l'article n'est pas valide" : "The Article is invalid", errors);
        }
    }

    @Override
    public MessageNotification create(ArticleDto dto, String language) {
        validateField(dto, language);
        articleRepository.save(articleMapper.toEntity(dto));

        if (dto.getId() != null) {
            serviceUtil.addLogs(SourceLogs.GESTION_ARTICLE, TypeLogs.UPDATE, "l'article" + dto.getCode() + " a été mis a jour avec succès", language);
            return new MessageNotification("1", MethodUtils.isFrench(language) ? "l'article a été mis à jour avec succès" : "The article was updated successfully");
        }
        serviceUtil.addLogs(SourceLogs.GESTION_ARTICLE, TypeLogs.ENREGISTREMENT, "l'article" + dto.getCode() + "a été enregistré avec succès", language);
        return new MessageNotification("1", MethodUtils.isFrench(language) ? "l'article a été enregistré avec succès" : "The article was saved successfully");

    }

    @Override
    public List<ArticleDto> findByCriteria(ArticleCriteria criteria, String language) {
        Pageable p = MethodUtils.findAllByCriteria(criteria.getClassement(), criteria.getTypeClassement(), criteria.getNombreDeResultat(), criteria);
        return articleMapper.fromEntities(
                articleRepository.findAll(ArticleSpecification.getSpecification(criteria), p).getContent());
    }

    @Override
    public ArticleDto findByCode(String code, String language) {
        if (Strings.isNullOrEmpty(code)) {
            throw new InvalidOperationException(MethodUtils.isFrench(language) ?
                    "Le code ne peut pas être nul" :
                    "the given code can not be must null");
        }
        return articleRepository.findByCode(code)
                .map(articleMapper::fromEntity).orElseThrow(
                        () -> new EntityNotFoundException(MethodUtils.isFrench(language) ? "L'article n'a pas été trouvée"
                                : "The article was not found")
                );
    }

    @Override
    public ArticleDto findById(Long id, String language) {
        if (id == null) {
            throw new InvalidOperationException(MethodUtils.isFrench(language) ?
                    "L'Id ne peut pas être null" :
                    "the given Id can not be must null");
        }

        Optional<Article> ets = articleRepository.findById(id);

        if (ets.isPresent()) {
            return articleMapper.fromEntity(ets.get());
        } else {
            throw new EntityNotFoundException(MethodUtils.isFrench(language) ?
                    "Aucun article présent avec l'ID spécifié" :
                    "No article present with specify ID");
        }
    }
}
