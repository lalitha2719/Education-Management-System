package com.ems.authservice.dao;

import com.ems.authservice.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


// JPA Repository to interacts with database
@Repository
public interface UserDAO extends JpaRepository<UserData, String> {

}
