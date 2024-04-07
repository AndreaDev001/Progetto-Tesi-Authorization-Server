package com.tirocinio.authorizationserver.data.entities.users;


import com.tirocinio.authorizationserver.data.converters.TrimConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "LOCAL_USERS")
public class LocalUser extends User implements UserDetails
{

    @Column(name = "NAME",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    @Length(min = 3,max = 10)
    private String name;

    @Column(name = "SURNAME",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    @Length(min = 3,max = 10)
    private String surname;

    @Column(name = "PASSWORD",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    private String password;

    @Column(name = "LOCKED",nullable = false)
    private boolean locked;

    @Column(name = "EXPIRED",nullable = false)
    private boolean expired;

    @Column(name = "ENABLED",nullable = false)
    private boolean enabled;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
