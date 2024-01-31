package com.tirocinio.authorizationserver.data.entities;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ROLES")
public class Role extends GenericEntity implements GrantedAuthority
{

    @Column(name = "NAME",nullable = false,unique = true)
    private String name;

    @Override
    public String getAuthority() {
        return this.name;
    }
}
