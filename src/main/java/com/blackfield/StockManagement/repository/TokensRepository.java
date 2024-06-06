package com.blackfield.StockManagement.repository;

import com.blackfield.StockManagement.domain.TokensDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokensRepository extends JpaRepository<TokensDomain, Long> {

    @Query(value = " select t from TokensDomain t inner join UsersDomain u on t.utilisateur.id = u.id where u.id = :id and (t.expirer = false or t.revoquer = false)")
    List<TokensDomain> findAllValidTokenByUser(@Param("id") Long id);

    Optional<TokensDomain> findByToken(String token);
}
