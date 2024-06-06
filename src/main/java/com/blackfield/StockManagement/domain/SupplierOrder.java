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
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "supplierOrder")
public class SupplierOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "supplierName")
    private String supplierName;

    @Column(name = "code")
    private String code;

    @Column(name = "article")
    private String article;

    @Column(name = "unitPrice")
    private Integer unitPrice;

    @Column(name = "totalPrice")
    private Integer totalPrice;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "commandDate")
    private LocalDate commandDate;

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
        code = code == null ? "COD-" + MethodUtils.format(getId().intValue(), 4)
                : code;
    }
}
