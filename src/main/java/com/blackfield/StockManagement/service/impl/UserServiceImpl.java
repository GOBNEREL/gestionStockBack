package com.blackfield.StockManagement.service.impl;

import com.blackfield.StockManagement.bean.AuthoritiesBean;
import com.blackfield.StockManagement.bean.AuthoritiesOfUserBean;
import com.blackfield.StockManagement.bean.ChangePasswordBean;
import com.blackfield.StockManagement.bean.UserSaveBean;
import com.blackfield.StockManagement.criteria.UtilisateurCriteria;
import com.blackfield.StockManagement.domain.RolesDomain;
import com.blackfield.StockManagement.domain.UsersDomain;
import com.blackfield.StockManagement.domain.enumeration.TypeLogs;
import com.blackfield.StockManagement.dto.LogsDto;
import com.blackfield.StockManagement.dto.UserDto;
import com.blackfield.StockManagement.exception.BadCredendialException;
import com.blackfield.StockManagement.exception.EntityNotFoundException;
import com.blackfield.StockManagement.exception.InvalidEntityException;
import com.blackfield.StockManagement.exception.InvalidOperationException;
import com.blackfield.StockManagement.mapper.UserMapper;
import com.blackfield.StockManagement.repository.RolesRepository;
import com.blackfield.StockManagement.repository.UserRepository;
import com.blackfield.StockManagement.service.LogsService;
import com.blackfield.StockManagement.service.UserService;
import com.blackfield.StockManagement.specification.UtilisateurSpecification;
import com.blackfield.StockManagement.util.MessageNotification;
import com.blackfield.StockManagement.util.MethodUtils;
import com.blackfield.StockManagement.util.ServiceUtil;
import com.blackfield.StockManagement.validator.UserValidator;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final UserValidator userValidator;
    private final UserMapper userMapper;
    private final ServiceUtil serviceUtil;
    private final LogsService logsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * This method returns the user's information from his login
     *Cette méthode renvoie les informations de l'utilisateur depuis son login
     *Donc recherche un utilisateur dans le système en utilisant le login spécifié
     */

    @Override
    public UsersDomain findByLogin(String login, String langue) {
        return userRepository.findByLogin(login).orElseThrow(() ->
                new BadCredendialException("L'utilisateur avec le login = " + login + " n'a pas été trouvé dans le système")
        );
    }

    @Override
    public UserDto SearchByLogin(String login, String language) {
        // Vérification du paramètre login
        if (Strings.isNullOrEmpty(login)) {
            throw new InvalidEntityException(MethodUtils.isFrench(language) ?
                    "Veuillez renseigner le login" : "Please fill in the login");
        }
        return userMapper.fromEntity(userRepository.findByLogin(login).orElseThrow(
                () -> {
                    throw new EntityNotFoundException(MethodUtils.isFrench(language) ?
                            "L'utilisateur avec le login = " + login + " n'a pas été trouvé dans le système"
                            : "The utilisateur with login = " + login + " was not found in the system");
                }
        ));
    }


    /**
     * Rechercher un utilisateur en fonction de son Id
     */

    @Override
    public UserDto  findById(Long id, String language) {
        // Vérification du paramètre de l'id
        if (id == null) {
            throw new InvalidEntityException(MethodUtils.isFrench(language) ?
                    "Veuillez renseigner l'identifiant de l'utilisateur" : "Please fill in the id user");
        }
        return userMapper.fromEntity(userRepository.findById(id).orElseThrow(
                () -> {
                    throw new EntityNotFoundException(MethodUtils.isFrench(language) ?
                            "L'utilisateur avec l'ID = " + id + " n'a pas été trouvé dans le système"
                            : "The utilisateur with the ID = " + id + " was not found in the system");
                }
        ));
    }

    /**
     * Cette methode perment de creer un utilisateur
     */

    @Override
    public MessageNotification save(UserSaveBean bean, String language) {

        // Transfert des valeurs du bean vers UtilisateurDto
        UserDto dto = setUserDto(bean);

        // Insertion des données supplémentaire lors de la création
        //Initialiser le mots de passe par defaut
        if (dto.getId() == null) {
            dto.setPassword(passwordEncoder.encode("12345"));
        }

        // Validation des données du Dto
        verifyElementOfUser(language, dto);

        // Ajout d'un log pour l'opération effectuée
        TypeLogs typeLogs = dto.getId() == null ? TypeLogs.ENREGISTREMENT : TypeLogs.UPDATE;
        String logMessage = dto.getId() == null
                ? "Enregistrement de l'utilisateur avec l'identifiant No " + dto.getId() + " | Login : " + dto.getLogin()
                : "Mise a jour de l'utilisateur avec l'identifiant No " + dto.getId();
//        addLogOfUser(typeLogs, logMessage, language);

        return new MessageNotification(MethodUtils.isFrench(language) ? "L'utilisateur a été créé avec succès."

                : "The utilisateur has been successfully created");
    }



    /**
     * Cette méthode prend un objet UserSaveBean et crée un objet UtilisateursDto
     * en copiant les valeurs appropriées des propriétés de UserSaveBean
     * @param bean The bean of User
     * @return Object userDto with the new value
     */
    private UserDto setUserDto(UserSaveBean bean) {
        return UserDto.builder()
                .login(bean.getLogin())
                .email(bean.getEmail())
                .activer(bean.isActivated())
                .phone(bean.getPhone())
                .adresse(bean.getAddress())
                .password("12345")
                .id(bean.getId())
                .nom(bean.getName())
                .build();
    }

    /**
     * This method add authorities of user
     *
     * @param bean     The bean contains authorities list
     * @param language The language used for returning a message.
     * @return the new authorities of user
     */
    @Override
    @Transactional
    public MessageNotification addAuthoritiesToUser(AuthoritiesBean bean, String language) {

        UsersDomain entity = findByLogin(bean.getLogin(),language);

        List<RolesDomain> authorities = rolesRepository.findByListName(bean.getRoles());

        if (entity.getRoles() != null && !entity.getRoles().isEmpty()) {
            authorities.forEach(authority -> {
                if (!entity.getRoles().contains(authority)) {
                    entity.getRoles().add(authority);
                }
            });
        } else {
            entity.setRoles(authorities);
        }

        userRepository.save(entity);

        return new MessageNotification(MethodUtils.isFrench(language) ? "Ajout de rôle effectué avec succès"
                : "Role successfully added");
    }
    /**
     * This method list authorities of user
     *
     * @param bean     The bean contains authorities list of user
     * @param language The language used for returning a message.
     * @return list authorities of user
     */

    @Override
    public AuthoritiesOfUserBean listAuthoritiesOfUser(AuthoritiesOfUserBean bean, String language) {
        UsersDomain entity = findByLogin(bean.getLogin(), language);

        List<RolesDomain> roles = rolesRepository.findAll();
        List<String> authorithiesDoNotUser = new ArrayList<>();
        List<String> authorithiesOfUser = new ArrayList<>();

        roles.forEach(authority -> {
            if (entity != null) {
                if (!entity.getRoles().contains(authority)) {
                    authorithiesDoNotUser.add(authority.getNom());
                } else {
                    authorithiesOfUser.add(authority.getNom());
                }
            }
        });

        bean.setAuthorityDoNotOfUser(authorithiesDoNotUser);
        bean.setAuthorityOfUser(authorithiesOfUser);

        return bean;
    }

    /**
     * This method removes a role list from a user
     *
     * @param bean     The bean contain the elements of user and store
     * @param language The language used for returning a message.
     * @return the user with les roles
     */
    @Override
    public MessageNotification deleteAuthoritiesToUser(AuthoritiesBean bean, String language) {
        UsersDomain entity = findByLogin(bean.getLogin(),language);

        List<RolesDomain> authorities = rolesRepository.findByListName(bean.getRoles());

        if (entity != null) {
            entity.getRoles().removeAll(authorities);
        }
        userRepository.save(entity);

        return new MessageNotification(MethodUtils.isFrench(language)
                ? authorities.size() + " role(s) ont été supprimé avec succès !!!"
                : authorities.size() + " Role(s) have been removed at successfully !!!");
    }

    /**
     * cette methode Verifie les elements de l'utilisateur
     * @param language The language used for returning a message.
     * @param dto      The dto of the userDto
     */
    private void verifyElementOfUser(String language, UserDto dto) {

        // Validation des champs du formulaire de UtilisateurDto
        List<String> errors = userValidator.validateField(dto, language);
        if (!errors.isEmpty()) {
            log.error("UserDto not valid, {}", dto);
            throw new InvalidEntityException(MethodUtils.isFrench(language) ? "L'utilisateur n'est pas valide"
                    : "The utilisateur is not valid", errors);
        }

        // Enregistrement de l'utilisateur
        userRepository.save(userMapper.toEntity(dto));

        // Ajout du rôle ROLE_USER lors de la création d'un utilisateur
        if (dto.getId() == null) {
//
            AuthoritiesBean bean = AuthoritiesBean.builder()
                    .login(dto.getLogin())
                    .roles(Collections.singletonList("ROLE_ENCADREUR")).build();

            addAuthoritiesToUser(bean, language);
        }
    }
    /**
     * cette méthode enregistre un journal d'activité
     * pour l'utilisateur actuellement connecté
     *
     * @param typeLogs The type of log to be added (e.g., CREATE, UPDATE, DELETE)
     * @param message  The message to be logged
     * @param language The language in which the message should be logged
     */

    private void addLogOfUser(TypeLogs typeLogs, String message, String language) {
        UserDto dto = serviceUtil.currentUser();
        LogsDto logsDto = LogsDto.builder()
                .typeLogs(typeLogs)
                .message(message)
                .agent(dto.getLogin())
                .build();
        logsService.saveLogs(logsDto, language);
    }
    /**
     * Cette methode compare le mots de passe de l'utilisateur conecter
     */
    @Override
    public boolean comparePasswordUserForAuthenticate(String rawPassword, String passwordEncrypt) {
        return passwordEncoder.matches(rawPassword, passwordEncrypt);
    }


    /**
     * cette methode permet de modifier un utilisateur
     * @param bean
     * @param language
     * @return
     */
    @Override
    public MessageNotification update(UserSaveBean bean, String language) {

        UserDto dto = findById(bean.getId(), language);

        // Modification des données de l'utilisateur
        updateUser(dto, bean);

        // Validation des données du Dto
        verifyElementOfUser(language, dto);

        return new MessageNotification(MethodUtils.isFrench(language) ? "L'utilisateur a été modifié avec succès"
                : "The utilisateur has been successfully updated");
    }

    private void updateUser(UserDto dto, UserSaveBean bean) {
        dto.setEmail(bean.getEmail());
        dto.setPhone(bean.getPhone());
        dto.setNom(bean.getName());
        dto.setAdresse(bean.getAddress());
//        dto.setLogin(bean.getLogin());
    }

    /**
     * Cette methode  permet de re
     * @param bean     The bean of ChangePasswordBean

     */
    private void validateEntityChangePassword(ChangePasswordBean bean, String language) {
        UserDto userDto = serviceUtil.currentUser();
        if (!passwordEncoder.matches(bean.getOldPassword(), userDto.getPassword())) {
            throw new InvalidOperationException(MethodUtils.isFrench(language) ? "Impossible de modifier le mot de passe car votre ancien mot de passe n'est pas correct"
                    : "Unable to change the password because your old password is not correct");
        }
    }

    /**
     * Cette methode permet de changer le password d'un utilisateur
     */
    @Override
    public MessageNotification changePassword(ChangePasswordBean bean, String language) {
        List<String> errors = userValidator.validateFieldChangePassword(bean, language);
        if (!errors.isEmpty()) {
            log.error("ChangePasswordBean not valid, {}", bean);
            throw new InvalidEntityException(MethodUtils.isFrench(language) ? "Les éléments pour le changement du mot de passe ne sont pas valide"
                    : "The elements for changing the password are not valid", errors);
        }
        validateEntityChangePassword(bean, language);
        //Recherche l'utilisateur corespondant au login specifie dans le bean
        UserDto userDto = SearchByLogin(bean.getLogin(), language);
        //Mets a jour le mot de passe de l'objet
        userDto.setPassword(passwordEncoder.encode(bean.getNewPassword()));
        userMapper.fromEntity(userRepository.save(userMapper.toEntity(userDto)));
        return new MessageNotification(MethodUtils.isFrench(language) ? "Votre mot de passe a été changé avec succès"
                : "Your password has been successfully changed");
    }

    /**
     * This method reset password of user
     *
     * @param login    The login of user
     * @param language The language used for returning a message.
     * @return User of new password
     */
    @Override
    public MessageNotification resetPassword(String login, String language) {
        if (Strings.isNullOrEmpty(login)) {
            throw new InvalidOperationException("Impossible de réinitaliser le mot de passe car aucun login n'a été renseigné");
        }
        UserDto userDto = SearchByLogin(login, language);
        userDto.setPassword(passwordEncoder.encode("12345"));
        userRepository.save(userMapper.toEntity(userDto));
        return new MessageNotification(MethodUtils.isFrench(language) ? "Le mot de passe a été réinitialisé avec succès"
                : "The password has been successfully reset");
    }


    /**
     * @param dto The dto of user
     */
    @Override
    public void saveForAuthenticate(UserDto dto) {
        userRepository.save(userMapper.toEntity(dto));
    }

    @Override
    public MessageNotification changeStatusOfUser(Long idUser, String language) {
        UserDto dto = findById(idUser, language);
        if (Boolean.FALSE.equals(dto.isActiver())) {
            dto.setActiver(true);
        } else {
            dto.setActiver(false);
        }
        saveForAuthenticate(dto);
        return new MessageNotification(MethodUtils.isFrench(language) ? "Le statut de l'utilisateur a été modifié avec succès"
                : "The user's status has been successfully changed");

    }

    @Override
    public List<UserDto> findUtilisateurByCriteria(UtilisateurCriteria utilisateurCriteria) {
        Pageable p = MethodUtils.findAllByCriteria(utilisateurCriteria.isOrder(), utilisateurCriteria.getOrderType(), utilisateurCriteria.getLimit(), utilisateurCriteria);

        List<UsersDomain> usersDomains = userRepository.findAll(UtilisateurSpecification.getSpecification(utilisateurCriteria),
                p).getContent();
        return userMapper.fromEntities(usersDomains);
    }
}