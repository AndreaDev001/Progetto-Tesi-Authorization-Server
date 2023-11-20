package com.tirocinio.authorizationserver.data.dao;


import com.tirocinio.authorizationserver.data.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientDao extends JpaRepository<Client, UUID>
{
    @Query("select c from Client c where c.clientID = :requiredClientID")
    Optional<Client> getClientByID(@Param("requiredClientID") String clientID);
}
