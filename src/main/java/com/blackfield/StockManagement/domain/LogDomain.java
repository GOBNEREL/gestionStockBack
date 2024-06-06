package com.blackfield.StockManagement.domain;

import com.blackfield.StockManagement.domain.enumeration.SourceLogs;
import com.blackfield.StockManagement.domain.enumeration.TypeLogs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "logs")
public class LogDomain implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @LastModifiedDate
    @Column(name = "derniere_date_modification", nullable = false)
    private LocalDateTime derniereDateModification;
    @Column(name = "date_operation")
    private LocalDateTime dateoperation;
    @Column(name = "message")
    private String message;
    @Column(name = "type_logs")
    @Enumerated(EnumType.STRING)
    private TypeLogs typeLogs;
    @Column(name = "source_logs")
    @Enumerated(EnumType.STRING)
    private SourceLogs sourceLogs;
    @Column(name = "agent")
    private String agent;

    @PrePersist
    void persist() {
        dateCreation = LocalDateTime.now();
        derniereDateModification = LocalDateTime.now();
    }

    @PreUpdate
    void update() {
        derniereDateModification = LocalDateTime.now();
    }

    @PostPersist
    @PostUpdate
    void logs() {
        dateoperation = LocalDateTime.now();
    }

}

