package com.blackfield.StockManagement.repository;

import com.blackfield.StockManagement.domain.RolesDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RolesRepository extends JpaRepository<RolesDomain, Long> {
    @Query(value = "select * from roles where nom in (:names)", nativeQuery = true)
    List<RolesDomain> findByListName(@Param("names") List<String> names);

    @Query(value = "select * from roles where nom in (:names)", nativeQuery = true)
    RolesDomain findByName(@Param("names") String names);

    RolesDomain findByNom(String nom);
}