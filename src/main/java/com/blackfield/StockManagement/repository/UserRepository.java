package com.blackfield.StockManagement.repository;

import com.blackfield.StockManagement.domain.RolesDomain;
import com.blackfield.StockManagement.domain.UsersDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UsersDomain, Long>, JpaSpecificationExecutor<UsersDomain> {

    Optional<UsersDomain> findByLogin(String login);

    Optional<UsersDomain> findByEmail(String email);
    Optional<UsersDomain> findByPhone(String phone);

    Optional<UsersDomain> findOneWithAuthoritiesByLogin(String login);

    @Query(value = "SELECT u.roles from UsersDomain as u where u.login = :login")
    List<RolesDomain> findRolesByLogin(@Param("login") String login);

    @Modifying
    @Query("UPDATE UsersDomain m SET m.activer = CASE m.activer WHEN true THEN false ELSE true END WHERE m.id = :id")
    void changeStatut(@Param("id") Long id);
}