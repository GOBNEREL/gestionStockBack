package com.blackfield.StockManagement.validator;


import com.blackfield.StockManagement.dto.ArticleDto;
import com.blackfield.StockManagement.dto.CategoryDto;
import com.blackfield.StockManagement.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryValidator {

    private final CategoryRepository categoryRepository;
    public List<String> validate(CategoryDto dto, String language) {
        List<String> errors = new ArrayList<>();
        if (dto == null) {
            errors.add("fr".equals(language) ?"Veuillez remplir toutes les informations nécessaires !!!": "Please fill in all necessary information !!!");
            return errors;
        } else {
            if (dto.getName() == null) {
                errors.add("fr".equals(language) ?"Veuillez saisir le nom de l'établissement !!!": "Please enter the name of the category !!!");
            }
        }
        return errors;
    }
}
