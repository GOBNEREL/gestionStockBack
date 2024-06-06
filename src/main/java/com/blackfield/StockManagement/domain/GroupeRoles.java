package com.blackfield.StockManagement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "groupes_roles")
public class GroupeRoles  extends AbstractEntity implements Serializable {
//    @ManyToOne
//    @JoinColumn(name = "groupes", referencedColumnName = "id")
//    private GroupesAuthority groupesAuthority;

    @ManyToOne
    @JoinColumn(name = "roles", referencedColumnName = "id")
    private RolesDomain roles;

}