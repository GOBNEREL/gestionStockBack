package com.blackfield.StockManagement.service;

import com.blackfield.StockManagement.bean.AuthoritiesBean;
import com.blackfield.StockManagement.bean.AuthoritiesOfUserBean;
import com.blackfield.StockManagement.bean.ChangePasswordBean;
import com.blackfield.StockManagement.bean.UserSaveBean;
import com.blackfield.StockManagement.criteria.UtilisateurCriteria;
import com.blackfield.StockManagement.domain.UsersDomain;
import com.blackfield.StockManagement.dto.UserDto;
import com.blackfield.StockManagement.util.MessageNotification;

import java.util.List;

public interface UserService {

    MessageNotification deleteAuthoritiesToUser(AuthoritiesBean bean, String language);
    AuthoritiesOfUserBean listAuthoritiesOfUser(AuthoritiesOfUserBean bean, String language);
    UsersDomain findByLogin(String login, String language);
    UserDto findById(Long id, String language);
    MessageNotification update(UserSaveBean bean, String language);
    MessageNotification addAuthoritiesToUser(AuthoritiesBean bean, String language);
    MessageNotification save(UserSaveBean bean, String language);
    public UserDto SearchByLogin(String login, String language);
    public void saveForAuthenticate(UserDto dto);
    MessageNotification changeStatusOfUser(Long idUser, String language);
    MessageNotification changePassword(ChangePasswordBean bean, String language);
    MessageNotification resetPassword(String login, String language);
    public boolean comparePasswordUserForAuthenticate(String rawPassword, String passwordEncrypt);
    List<UserDto> findUtilisateurByCriteria(UtilisateurCriteria utilisateurCriteria);
}

