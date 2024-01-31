package com.tirocinio.authorizationserver.data.entities.users;


import com.tirocinio.authorizationserver.data.converters.TrimConverter;
import com.tirocinio.authorizationserver.data.entities.GenericEntity;
import com.tirocinio.authorizationserver.data.entities.Role;
import com.tirocinio.authorizationserver.data.entities.enums.Provider;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "USERS")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends GenericEntity
{
    @Email
    @Column(name = "EMAIL",nullable = false)
    @Convert(converter = TrimConverter.class)
    protected String email;

    @Column(name = "USERNAME",nullable = false)
    @Convert(converter = TrimConverter.class)
    @Length(min = 3,max = 20)
    protected String username;

    @Column(name = "PROVIDER",nullable = false)
    @Enumerated(value = EnumType.STRING)
    protected Provider provider;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE",joinColumns = @JoinColumn(name = "USER_ID"),inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    protected Set<Role> roles = new HashSet<>();
}
