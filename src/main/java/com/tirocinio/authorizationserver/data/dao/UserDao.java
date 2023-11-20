package com.tirocinio.authorizationserver.data.dao;


import com.tirocinio.authorizationserver.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDao extends JpaRepository<User, UUID>
{

}
