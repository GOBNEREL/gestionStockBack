package com.blackfield.StockManagement.controller;

import com.blackfield.StockManagement.bean.AuthoritiesBean;
import com.blackfield.StockManagement.bean.AuthoritiesOfUserBean;
import com.blackfield.StockManagement.bean.ChangePasswordBean;
import com.blackfield.StockManagement.bean.UserSaveBean;
import com.blackfield.StockManagement.controller.api.UtilisateurApi;
import com.blackfield.StockManagement.criteria.UtilisateurCriteria;
import com.blackfield.StockManagement.dto.UserDto;
import com.blackfield.StockManagement.service.UserService;
import com.blackfield.StockManagement.util.MessageNotification;
import com.blackfield.StockManagement.util.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UtilisateurController implements UtilisateurApi {
    private final UserService userService;
    private final ServiceUtil serviceUtil;

    @Override
    public MessageNotification saveUser(UserSaveBean bean, String language) {
        return userService.save(bean, language);
    }
    @Override
    public MessageNotification update(UserSaveBean bean, String language) {
        return userService.update(bean, language);
    }
    @Override
    public AuthoritiesOfUserBean listAuthoritiesOfUser(AuthoritiesOfUserBean bean, String language) {
        return userService.listAuthoritiesOfUser(bean, language);
    }

    @Override
    public MessageNotification addAuthoritiesOfUser(AuthoritiesBean bean, String language) {
        return userService.addAuthoritiesToUser(bean, language);
    }

    @Override
    public MessageNotification deleteAuthoritiesToUser(AuthoritiesBean bean, String language) {
        return userService.deleteAuthoritiesToUser(bean, language);
    }

    @Override
    public UserDto findById(Long id, String language){
        return userService.findById(id, language);
    }
    @Override
    public UserDto SearchByLogin(String login, String language) {

        return userService.SearchByLogin(login, language);
    }


    @Override
    public MessageNotification changeStatusOfUser(Long idUser, String language) {
        return userService.changeStatusOfUser(idUser, language);
    }

    @Override
    public List<UserDto> findUtilisateurByCriteria(UtilisateurCriteria criteria) {
        return userService.findUtilisateurByCriteria(criteria);
    }
    @Override
    public MessageNotification changePassword(ChangePasswordBean bean, String language) {
        return userService.changePassword(bean, language);
    }
    @Override
    public MessageNotification resetPassword(String login, String language) {
        return userService.resetPassword(login, language);
    }

    @Override
    public UserDto getCurrentUser() {
        return serviceUtil.currentUser();
    }
}