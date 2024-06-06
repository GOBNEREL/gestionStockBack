package com.blackfield.StockManagement.controller.api;

import com.blackfield.StockManagement.bean.AuthenticationRequestBean;
import com.blackfield.StockManagement.bean.AuthenticationResponseBean;
import com.blackfield.StockManagement.bean.ChangePasswordBean;
import com.blackfield.StockManagement.dto.UserDto;
import com.blackfield.StockManagement.util.Constants;
import com.blackfield.StockManagement.util.MessageNotification;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Api("authenticate-api")
public interface AuthenticateApi {


    @ApiOperation(value = "Première authentification",
            notes = "Cette méthode permet de s'authentifier à l'application", response = AuthenticationResponseBean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'utilisateur a été authentifié avec succès"),
            @ApiResponse(code = 400, message = "L'objet n'est pas valide"),
            @ApiResponse(code = 404, message = "Aucun utilisateur n'existe dans le système avec ce login"),
    })
    @PostMapping(value = Constants.APP_API + "/authenticate/loginAndPassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    AuthenticationResponseBean authenticateByLoginAndPassword(@RequestBody AuthenticationRequestBean request);

    @ApiOperation(value = "Deconnexion à la plateforme",
            notes = "Cette méthode permet de se déconencter à l'application", response = void.class)
    @GetMapping(value = Constants.APP_API + "/logout")
    void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication);


    @ApiOperation(value = "Changer le mot de passe",
            notes = "Cette méthode permet de changer le mot de passe de l'utilisateur connecté", response = MessageNotification.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le mot de passe a été changé avec succès"),
            @ApiResponse(code = 400, message = "L'objet n'est pas valide")
    })
    @PostMapping(value = Constants.APP_API + "/users/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    MessageNotification changePassword(@RequestBody ChangePasswordBean bean, @RequestParam(value = "language") String language);


    @ApiOperation(value = "Retourne l'utilisateur connecté",
            notes = "Cette méthode permet de retourner les informations de l'utilisateur connecté", response = UserDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Informations de l'utilisateur retournées avec succès"),
            @ApiResponse(code = 404, message = "L'utilisateur n'a pas été trouvé")
    })
    @GetMapping(value = Constants.APP_API + "/users/currentUser", produces = MediaType.APPLICATION_JSON_VALUE)
    UserDto currentUser();
}
