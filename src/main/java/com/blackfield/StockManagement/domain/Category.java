package com.blackfield.StockManagement.domain;

import com.blackfield.StockManagement.util.MethodUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "code", unique = true)
    private String code;

    @CreatedDate
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @LastModifiedDate
    @Column(name = "date_modification", nullable = false)
    private LocalDateTime dateModification;


    @Column(name = "status", columnDefinition = "tinyint(1) default 1", nullable = false)
    private boolean status;

    @PrePersist
    void persist() {
        dateCreation = LocalDateTime.now();
        dateModification = LocalDateTime.now();
    }

    @PreUpdate
    void update() {
        dateModification = LocalDateTime.now();
    }

    @PostPersist
    @PostUpdate
    void p() {
        code = code == null ? "CAT-" + MethodUtils.format(getId().intValue(), 4)
                : code;
    }
}