package com.blackfield.StockManagement.validator;

import com.blackfield.StockManagement.bean.ChangePasswordBean;
import com.blackfield.StockManagement.domain.UsersDomain;
import com.blackfield.StockManagement.dto.UserDto;
import com.blackfield.StockManagement.exception.InvalidOperationException;
import com.blackfield.StockManagement.repository.UserRepository;
import com.blackfield.StockManagement.util.MethodUtils;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserValidator {
    private final UserRepository userRepository;

    public List<String> validateFieldChangePassword(ChangePasswordBean bean, String language) {
        List<String> errors = new ArrayList<>();

        if (bean == null) {
            errors.add(MethodUtils.isFrench(language) ? "Veuillez renseigner l'ancien mot de passe"
                    : "Please enter the old password");
            errors.add(MethodUtils.isFrench(language) ? "Veuillez renseigner le nouveau mot de passe"
                    : "Please enter the new password");
            errors.add(MethodUtils.isFrench(language) ? "Veuillez renseigner la confirmation du nouveau mot de passe"
                    : "Please fill in the new password confirmation");
            return errors;
        }

        if (Strings.isNullOrEmpty(bean.getOldPassword())) {
            errors.add(MethodUtils.isFrench(language) ? "Veuillez renseigner l'ancien mot de passe"
                    : "Please enter the old password");
        }
        if (Strings.isNullOrEmpty(bean.getNewPassword())) {
            errors.add(MethodUtils.isFrench(language) ? "Veuillez renseigner le nouveau mot de passe"
                    : "Please enter the new password");
        }
        if (Strings.isNullOrEmpty(bean.getConfirmPassword())) {
            errors.add(MethodUtils.isFrench(language) ? "Veuillez renseigner la confirmation du nouveau mot de passe"
                    : "Please fill in the new password confirmation");
        }
        if (!Strings.isNullOrEmpty(bean.getConfirmPassword()) && Strings.isNullOrEmpty(bean.getNewPassword())
                && bean.getConfirmPassword().equals(bean.getNewPassword())) {
            errors.add(MethodUtils.isFrench(language) ? "Le nouveau mot de passe ne cohincide pas avec le mot de passe de confirmation"
                    : "The new password does not match the confirmation password");
        }
        return errors;
    }
    public List<String> validateField(UserDto dto, String language) {

        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add(MethodUtils.isFrench(language) ? "Veuillez renseigner votre nom"
                    : "Please fill in your nom");
//            errors.add(MethodUtils.isFrench(language) ? "Veuillez renseigner votre login"
//                    : "Please fill in your login");
            errors.add(MethodUtils.isFrench(language) ? "Veuillez renseigner votre mot de passe"
                    : "Please enter your password");
            errors.add(MethodUtils.isFrench(language) ? "Veuillez renseigner votre numéro de téléphone"
                    : "Please enter your phone number");

            return errors;
        }
        if (Strings.isNullOrEmpty(dto.getNom())) {
            errors.add(MethodUtils.isFrench(language) ? "Veuillez renseigner votre nom"
                    : "Please fill in your nom");
        }
//
//        if (Strings.isNullOrEmpty(dto.getLogin())) {
//            errors.add(MethodUtils.isFrench(language) ? "Veuillez renseigner votre login"
//                    : "Please fill in your login");
//        }
        if (Strings.isNullOrEmpty(dto.getPassword())) {
            errors.add(MethodUtils.isFrench(language) ? "Veuillez renseigner votre mot de passe"
                    : "Please enter your password");
        }

        if (Strings.isNullOrEmpty(dto.getPhone())) {
            errors.add(MethodUtils.isFrench(language) ? "Veuillez renseigner votre numéro de téléphone"
                    : "Please enter your phone number");
        }



        return errors;
    }




    public void validateEntity(UserDto dto, String language) {

        Optional<UsersDomain> user = userRepository.findByLogin(dto.getLogin());
        if (user.isPresent() && !user.get().getId().equals(dto.getId())) {
            throw new InvalidOperationException(MethodUtils.isFrench(language) ? "Ce login est déjà utilisé"
                    : "This login is already in use");
        }
        user = userRepository.findByPhone(dto.getPhone());
        if (user.isPresent() && !user.get().getId().equals(dto.getId())) {
            throw new InvalidOperationException(MethodUtils.isFrench(language) ? "Ce numéro de téléphone est déjà utilisé"
                    : "This phone number is already in use");
        }
    }
}