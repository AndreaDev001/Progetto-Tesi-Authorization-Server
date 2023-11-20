package com.tirocinio.authorizationserver.data.dao;


import com.tirocinio.authorizationserver.data.entities.LocalUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LocalUserDao extends JpaRepository<LocalUser, UUID> {
    @Query("select l from LocalUser l where l.username = :requiredUsername")
    Optional<LocalUser> getByUsername(@Param("requiredUsername") String username);
    @Query("select l from LocalUser l where l.name = :requiredName and l.surname = :requiredSurname")
    Page<LocalUser> getUsers(@Param("requiredName") String name, @Param("requiredSurname") String surname, Pageable pageable);
}
