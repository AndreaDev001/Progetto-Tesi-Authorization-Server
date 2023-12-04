package com.tirocinio.authorizationserver.data.dao;


import com.tirocinio.authorizationserver.data.entities.users.GoogleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GoogleUserDao extends JpaRepository<GoogleUser, UUID>
{
    @Query("select g from GoogleUser g where g.externalID = :requiredID")
    Optional<GoogleUser> getUserByExternalID(@Param("requiredID") String externalID);
}
