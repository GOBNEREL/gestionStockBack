package com.blackfield.StockManagement.util;

import com.blackfield.StockManagement.domain.UsersDomain;
import com.blackfield.StockManagement.domain.enumeration.SourceLogs;
import com.blackfield.StockManagement.domain.enumeration.TypeLogs;
import com.blackfield.StockManagement.dto.LogsDto;
import com.blackfield.StockManagement.dto.RolesDto;
import com.blackfield.StockManagement.dto.UserDto;
import com.blackfield.StockManagement.mapper.UserMapper;
import com.blackfield.StockManagement.repository.UserRepository;
import com.blackfield.StockManagement.service.LogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceUtil {
    private static String CHAR_NUMERIQUE = "0123456789";
    private static char sep1 = '-', sep2 = '/', sep3 = ';', prefixe1 = '+';
    private final LogsService logsService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void addLogs(SourceLogs sourceLogs, TypeLogs typeLogs, String libelle, String language) {
        UserDto userDto = currentUser();
        LogsDto logsDto = LogsDto.builder()
                .sourceLogs(sourceLogs)
                .typeLogs(typeLogs)
                .message(libelle)
                .agent(userDto.getLogin())
                .build();
        logsService.saveLogs(logsDto, language);
    }

    public UserDto currentUser() {
        Optional<UsersDomain> currentUser = userRepository.findByLogin(SecurityUtils.getCurrentUserLogin());
        return currentUser.map(userMapper::fromEntity).orElse(null);
    }

    public List<String> getCurrentUserRoles() {
        UserDto user = currentUser();
        List<String> roles = new ArrayList<>();
        for (RolesDto rolesDto : user.getRoles()) {
            roles.add(rolesDto.getNom());
        }
        return roles;
    }

    public Boolean isCurrentUserAdmin() {
        return getCurrentUserRoles().contains("ROLE_ADMIN");
    }
}