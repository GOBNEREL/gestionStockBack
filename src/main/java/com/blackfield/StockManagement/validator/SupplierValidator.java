package com.blackfield.StockManagement.validator;

import com.blackfield.StockManagement.dto.SupplierDto;
import com.blackfield.StockManagement.repository.CustomerRepository;
import com.blackfield.StockManagement.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SupplierValidator {

    private final SupplierRepository supplierRepository;
    public List<String> validate(SupplierDto dto, String language) {
        List<String> errors = new ArrayList<>();
        if (dto == null) {
            errors.add("fr".equals(language) ?"Veuillez remplir toutes les informations nécessaires !!!": "Please fill in all necessary information !!!");
            return errors;
        } else {
            if (dto.getName() == null) {
                errors.add("fr".equals(language) ?"Veuillez saisir le nom du fournisseur !!!": "Please enter the supplier name !!!");
            }
            if (dto.getPhoneNumber() == null) {
                errors.add("fr".equals(language) ?"Veuillez saisir le numero de telephone du fournisseur !!!": "Please enter the supplier phone number !!!");
            }
            if (dto.getDescription() == null) {
                errors.add("fr".equals(language) ?"Veuillez saisir la description du fournisseur !!!": "Please enter the supplier description !!!");
            }
        }
        return errors;
    }
}
