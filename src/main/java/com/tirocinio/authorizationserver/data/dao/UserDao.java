package com.tirocinio.authorizationserver.data.dao;


import com.tirocinio.authorizationserver.data.entities.users.User;
import com.tirocinio.authorizationserver.data.entities.enums.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDao extends JpaRepository<User, UUID>
{
    @Query("select u from User u where u.provider = :requiredProvider")
    Page<User> getUsers(@Param("requiredProvider") Provider provider, Pageable pageable);
}
