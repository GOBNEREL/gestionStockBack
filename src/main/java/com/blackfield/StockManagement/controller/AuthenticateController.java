package com.blackfield.StockManagement.controller;

import com.blackfield.StockManagement.bean.AuthenticationRequestBean;
import com.blackfield.StockManagement.bean.AuthenticationResponseBean;
import com.blackfield.StockManagement.bean.ChangePasswordBean;
import com.blackfield.StockManagement.controller.api.AuthenticateApi;
import com.blackfield.StockManagement.dto.UserDto;
import com.blackfield.StockManagement.service.AuthentificationService;
import com.blackfield.StockManagement.service.logout.LogoutService;
import com.blackfield.StockManagement.util.MessageNotification;
import com.blackfield.StockManagement.util.ServiceUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequiredArgsConstructor
public class AuthenticateController implements AuthenticateApi {

    private final AuthentificationService authentificationService;
    private final LogoutService logoutService;
    private final ServiceUtil serviceUtil;

    @Override
    public AuthenticationResponseBean authenticateByLoginAndPassword(AuthenticationRequestBean request) {
        return authentificationService.authenticateByLoginAndPassword(request);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logoutService.logout(request, response, authentication);
    }

    @Override
    public MessageNotification changePassword(ChangePasswordBean bean, String language) {
        return null;
    }

    @Override
    public UserDto currentUser() {
        return null;
    }


}