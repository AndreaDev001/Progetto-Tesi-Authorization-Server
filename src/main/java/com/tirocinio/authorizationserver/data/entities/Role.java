package com.tirocinio.authorizationserver.data.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "ROLES")
@EntityListeners(value = AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role implements GrantedAuthority
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ROLE_ID")
    private UUID id;

    @Column(name = "NAME",nullable = false)
    private String name;

    @CreatedDate
    @Column(name = "CREATED_DATE",nullable = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",nullable = false)
    private LocalDate lastModifiedDate;

    @Override
    public String getAuthority() {
        return this.name;
    }
}
