package com.ems.authservice.service;

import com.ems.authservice.dao.UserDAO;
import com.ems.authservice.entity.UserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

// Service class
@Service
@Slf4j
public class CustomerDetailsService implements UserDetailsService {
    @Autowired
    private UserDAO userdao;

    /*
     * @param String
     * @return User
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String uid) {

        try {
            UserData custuser = userdao.findById(uid).orElse(null);
            if (custuser != null) {
                custuser.getUname();
            }
            return new User(custuser.getUserid(), custuser.getUpassword(), new ArrayList<>());
        } catch (Exception e) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }
    }

    public Boolean isAdmin(String uid) {

        UserData userData = userdao.findById(uid).orElse(null);
        if (userData != null && userData.isAdmin()) {
            return true;
        }
        return false;
    }

    public UserData registerUser(UserData userData) {
        return userdao.save(userData);
    }

    public UserData getUserName(String uid) {
        log.info("in getUserName()");
        return userdao.findById(uid).orElse(null);
    }
}
