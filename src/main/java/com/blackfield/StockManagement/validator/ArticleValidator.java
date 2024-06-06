package com.blackfield.StockManagement.validator;

import com.blackfield.StockManagement.dto.ArticleDto;
import com.blackfield.StockManagement.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleValidator {

    private final ArticleRepository articleRepository;
    public List<String> validate(ArticleDto dto, String language) {
        List<String> errors = new ArrayList<>();
        if (dto == null) {
            errors.add("fr".equals(language) ?"Veuillez remplir toutes les informations nécessaires !!!": "Please fill in all necessary information !!!");
            return errors;
        } else {
            if (dto.getName() == null) {
                errors.add("fr".equals(language) ?"Veuillez saisir le nom de l'article !!!": "Please enter the article name !!!");
            }
            if (dto.getCategory() == null) {
                errors.add("fr".equals(language) ?"Veuillez saisir la categorie de l'article !!!": "Please enter the article category !!!");
            }
            if (dto.getPrice() == null) {
                errors.add("fr".equals(language) ?"Veuillez saisir le prix de l'article !!!": "Please enter the article price !!!");
            }
        }
        return errors;
    }
}
