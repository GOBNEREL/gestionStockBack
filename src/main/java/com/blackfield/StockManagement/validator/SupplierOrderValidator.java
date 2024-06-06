package com.blackfield.StockManagement.validator;


import com.blackfield.StockManagement.dto.SupplierOrderDto;
import com.blackfield.StockManagement.repository.SupplierOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SupplierOrderValidator {

    private final SupplierOrderRepository supplierOrderRepository;
    public List<String> validate(SupplierOrderDto dto, String language) {
        List<String> errors = new ArrayList<>();
        if (dto == null) {
            errors.add("fr".equals(language) ?"Veuillez remplir toutes les informations nécessaires !!!": "Please fill in all necessary information !!!");
            return errors;
        } else {
            if (dto.getSupplierName() == null) {
                errors.add("fr".equals(language) ?"Veuillez saisir le nom du fournisseur !!!": "Please enter the supplier name !!!");
            }
            if (dto.getArticle() == null) {
                errors.add("fr".equals(language) ?"Veuillez saisir le nom de l'article !!!": "Please enter the article name !!!");
            }
            if (dto.getUnitPrice() == null) {
                errors.add("fr".equals(language) ?"Veuillez saisir le prix unitaire !!!": "Please enter the unit price !!!");
            }
            if (dto.getQuantity() == null) {
                errors.add("fr".equals(language) ?"Veuillez saisir la quantiter !!!": "Please enter the quantity !!!");
            }
        }
        return errors;
    }
}
