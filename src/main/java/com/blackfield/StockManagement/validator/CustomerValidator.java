package com.blackfield.StockManagement.validator;

import com.blackfield.StockManagement.dto.ArticleDto;
import com.blackfield.StockManagement.dto.CustomerDto;
import com.blackfield.StockManagement.repository.ArticleRepository;
import com.blackfield.StockManagement.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerValidator {

    private final CustomerRepository customerRepository;
    public List<String> validate(CustomerDto dto, String language) {
        List<String> errors = new ArrayList<>();
        if (dto == null) {
            errors.add("fr".equals(language) ?"Veuillez remplir toutes les informations nécessaires !!!": "Please fill in all necessary information !!!");
            return errors;
        } else {
            if (dto.getName() == null) {
                errors.add("fr".equals(language) ?"Veuillez saisir le nom de telephone du client !!!": "Please enter the customer name !!!");
            }
            if (dto.getPhoneNumber() == null) {
                errors.add("fr".equals(language) ?"Veuillez saisir le numero de telephone du client !!!": "Please enter the customer phone number !!!");
            }
        }
        return errors;
    }
}
