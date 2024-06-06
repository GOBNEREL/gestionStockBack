package com.blackfield.StockManagement.controller.api;

import com.blackfield.StockManagement.bean.AuthoritiesBean;
import com.blackfield.StockManagement.bean.AuthoritiesOfUserBean;
import com.blackfield.StockManagement.bean.ChangePasswordBean;
import com.blackfield.StockManagement.bean.UserSaveBean;
import com.blackfield.StockManagement.criteria.UtilisateurCriteria;
import com.blackfield.StockManagement.dto.UserDto;
import com.blackfield.StockManagement.util.Constants;
import com.blackfield.StockManagement.util.MessageNotification;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("utilisateur-api")
public interface UtilisateurApi {

    @ApiOperation(value = "Créer un nouvel utilisateur",
            notes = "Cette méthode permet de créer un nouvel utilisateur dans le système", response = MessageNotification.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'utilisateur a été créé avec succès"),
            @ApiResponse(code = 400, message = "L'objet n'est pas valide"),
            @ApiResponse(code = 403, message = "L'opération n'est pas valide")
    })
    @PostMapping(value = Constants.APP_API + "/users/saveUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    MessageNotification saveUser(@RequestBody UserSaveBean bean, @RequestParam(value = "language", required = false) String language);

    @ApiOperation(value = "Modifier les inofrmations de l'utiliateur", response = MessageNotification.class)
    @PatchMapping(value = Constants.APP_API + "/users/update",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    MessageNotification update(@RequestBody UserSaveBean bean, @RequestParam(value = "language") String language);

    @ApiOperation(value = "Ajoute les rôles à l'utiliasteur", response = MessageNotification.class)
    @PostMapping(value = Constants.APP_API + "/users/addAuthoritiesToUser",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    MessageNotification addAuthoritiesOfUser(@RequestBody AuthoritiesBean bean, @RequestParam(value = "language") String language);

    @ApiOperation(value = "Supprime les rôles à l'utiliasteur", response = MessageNotification.class)
    @DeleteMapping(value = Constants.APP_API + "/users/deleteAuthoritiesToUser",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    MessageNotification deleteAuthoritiesToUser(@RequestBody AuthoritiesBean bean, @RequestParam(value = "language") String language);

    @ApiOperation(value = "Reinitialiser le mot de passe d'un utilisateur",
            notes = "Cette méthode permet de reinitialiser le mot de passe d'un utilisateur", response = MessageNotification.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le mot de passe a été reinitialisé avec succès"),
            @ApiResponse(code = 400, message = "Le login n'est pas renseigné")
    })
    @GetMapping(value = Constants.APP_API +"/users/resetPassword/{login}/{language}", produces = MediaType.APPLICATION_JSON_VALUE)
    MessageNotification resetPassword(@PathVariable("login") String login, @PathVariable("language") String language);

    @ApiOperation(value = "Liste des rôles de l'utilisateur ainsi que les rôles qu'il n'a pas", response = AuthoritiesOfUserBean.class)
    @PostMapping(value = Constants.APP_API + "/users/listAuthoritiesOfUser",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    AuthoritiesOfUserBean listAuthoritiesOfUser(@RequestBody AuthoritiesOfUserBean bean, @RequestParam(value = "language", required = false) String language);

    @ApiOperation(value = "Retourner les informations a partir de l'id", notes = "Cette méthode permet de retourner les information a partir de l'id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Informations du stagiaire retournées avec succès"), @ApiResponse(code = 404, message = " c'est n'a pas ete trouver dans la liste des utilisateurs")})
    @GetMapping(value = Constants.APP_API + "/users/findUserById", produces = MediaType.APPLICATION_JSON_VALUE)
    UserDto findById(@RequestParam("id") Long id, @RequestParam(value = "language", required = false) String language);

    @ApiOperation(value = "Changer le mot de passe",
            notes = "Cette méthode permet de changer le mot de passe de l'utilisateur connecté", response = MessageNotification.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le mot de passe a été changé avec succès"),
            @ApiResponse(code = 400, message = "L'objet n'est pas valide")
    })
    @PostMapping(value = Constants.APP_API +"/users/changesPassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    MessageNotification changePassword(@RequestBody ChangePasswordBean bean, @RequestParam(value = "language", required = false) String language);

    @ApiOperation(value = "Change le statut de l'utilisateur",
            notes = "Cette méthode permet de changer le statut de l'utillisateur à partir de son identifiant", response = MessageNotification.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le statut de l'utilisateur a été changé avec succès"),
            @ApiResponse(code = 404, message = "L'utilisateur n'a pas été trouvé")
    })
    @GetMapping(value = Constants.APP_API + "/users/changeStatusOfUser", produces = MediaType.APPLICATION_JSON_VALUE)
    MessageNotification changeStatusOfUser(@PathVariable("idUser") Long idUser, @PathVariable("language") String language);


    @ApiOperation(value = "Lister les utilisateurs du système",
            notes = "Cette méthode permet de lister les utilisateurs du système", responseContainer = "List<UtilisateursDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Liste vide ou une liste d'utilisateur retournée")
    })
    @PostMapping(value = Constants.APP_API + "/users/findUtilisateurByCriteria", produces = MediaType.APPLICATION_JSON_VALUE)
    List<UserDto> findUtilisateurByCriteria(@RequestBody UtilisateurCriteria criteria);


    @ApiOperation(value = "Retourne les information de l'utilisateur à partir de son login",
            notes = "Cette méthode permet de retourner les information de l'utilisateur à partir de son login", response = UserDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Informations de l'utilisateur retournées avec succès"),
            @ApiResponse(code = 404, message = "L'utilisateur n'a pas été trouvé")
    })
    @GetMapping(value = Constants.APP_API +"/users/SearchByLogin/{login}/{language}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserDto SearchByLogin(@PathVariable("login") String login, @PathVariable("language") String language);

    @ApiOperation(value = "Lister les utilisateurs du système",
            notes = "Cette méthode permet de lister les utilisateurs du système", responseContainer = "List<UtilisateursDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Liste vide ou une liste d'utilisateur retournée")
    })
    @GetMapping(value = Constants.APP_API + "/users/getCurrentUser", produces = MediaType.APPLICATION_JSON_VALUE)
    UserDto getCurrentUser();


}

