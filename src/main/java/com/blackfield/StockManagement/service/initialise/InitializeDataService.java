package com.blackfield.StockManagement.service.initialise;

import com.blackfield.StockManagement.domain.RolesDomain;
import com.blackfield.StockManagement.domain.UsersDomain;
import com.blackfield.StockManagement.repository.RolesRepository;
import com.blackfield.StockManagement.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InitializeDataService {

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        saveRole();
        saveUtilisateur();
    }

    private void saveRole() {
        List<String> rolesListName = new ArrayList<>(Collections.singletonList("ROLE_ENCADREUR"));
        rolesListName.add("ROLE_ADMIN");
        for (String name : rolesListName) {
            if (rolesRepository.findByListName(Collections.singletonList(name)).isEmpty()) {
                RolesDomain role = new RolesDomain();
                role.setNom(name);
                rolesRepository.save(role);
            }
        }
    }

    private void saveUtilisateur() {
        UsersDomain utilisateur = new UsersDomain();

        if (userRepository.findByLogin("admin").isEmpty()) {
            utilisateur = new UsersDomain();
            utilisateur.setLogin("admin");
            utilisateur.setNom("admin");
            utilisateur.setPhone("682834283");
            utilisateur.setEmail("admin@skysoft.com");
            setOtherInformationOfUser(utilisateur);
        } else {
            UsersDomain utilisateur1 = userRepository.findByLogin("admin").get();
            setOtherInformationOfUser(utilisateur1);
        }
    }

    private void setOtherInformationOfUser(UsersDomain utilisateur) {
        if (userRepository.findRolesByLogin(utilisateur.getLogin()).isEmpty()) {
            utilisateur.setAdresse("Douala");
            utilisateur.setPassword(passwordEncoder.encode("12345"));
            utilisateur.setActiver(true);
            List<String> rolesListName = new ArrayList<>(Collections.singletonList("ROLE_ADMIN"));
            utilisateur.setRoles(rolesRepository.findByListName(rolesListName));
            userRepository.save(utilisateur);
        }
    }

}