package com.ems.authservice.controller;

import com.ems.authservice.entity.LoginData;
import com.ems.authservice.dao.UserDAO;
import com.ems.authservice.entity.AuthResponse;
import com.ems.authservice.entity.UserData;
import com.ems.authservice.service.CustomerDetailsService;
import com.ems.authservice.service.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(produces = "application/json", value = "Creating and validating the Jwt token")
public class AuthController {

    @Autowired
    private JwtUtil jwtutil;
    @Autowired
    private CustomerDetailsService custdetailservice;
    @Autowired
    private UserDAO userservice;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @CrossOrigin
    @ApiOperation(value = "Register Student", response = ResponseEntity.class)
    @PostMapping(value = "/registerStudent")
    public ResponseEntity<Object> registerUser(@RequestBody UserData userData) {
        LOGGER.info("At register");
        final UserData userData1 = custdetailservice.getUserName(userData.getUserid());
        LOGGER.info("user details {}", userData1);
        if (userData1 == null) {
            UserData registeredUser = custdetailservice.registerUser(userData);
            return new ResponseEntity<>(new UserData(registeredUser.getUserid(),
                    registeredUser.getUname(), registeredUser.isAdmin()), HttpStatus.CREATED);
        } else {
            LOGGER.info("At Register");
            LOGGER.error("Username already taken");
            return new ResponseEntity<>(new UserData("Username already taken", null, false),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @ApiOperation(value = "Verify credentials and generate JWT Token", response = ResponseEntity.class)
    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestBody LoginData loginData) {
        //Generates token for login
        final UserDetails userdetails = custdetailservice.loadUserByUsername(loginData.getUserId());
        String uid = "";
        String generateToken = "";
        if (userdetails.getPassword().equals(loginData.getPassword())) {
            uid = loginData.getUserId();
            generateToken = jwtutil.generateToken(userdetails);

            return new ResponseEntity<>(new UserData(uid, generateToken), HttpStatus.OK);
        } else {
            LOGGER.info("At Login : ");
            LOGGER.error("Not Accesible");
            return new ResponseEntity<>("Not Accesible", HttpStatus.FORBIDDEN);
        }
    }

    @CrossOrigin
    @ApiOperation(value = "Validate JWT Token", response = ResponseEntity.class)
    @GetMapping(value = "/validateStudent")
    public ResponseEntity<Object> getValidityForStudent(@RequestHeader("Authorization") final String token) {
        //Returns response after Validating received token
        String token1 = token.substring(7);
        AuthResponse res = new AuthResponse();
        if (Boolean.TRUE.equals(jwtutil.validateToken(token1))) {

            String uid = jwtutil.extractUsername(token1);
            boolean isAdmin = custdetailservice.isAdmin(uid);
            LOGGER.info("isAdmin {}", isAdmin);
            if (!isAdmin) {
                res.setUid(uid);
                res.setValid(true);
                res.setName(userservice.findById(jwtutil.extractUsername(token1)).get().getUname());
            } else {
                res.setValid(false);
                LOGGER.info("Student ");
                LOGGER.error("No Access");
            }
        } else {
            res.setValid(false);
            LOGGER.info("At Validity : ");
            LOGGER.error("Token Has Expired");
        }
        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @CrossOrigin
    @ApiOperation(value = "Validate JWT Token", response = ResponseEntity.class)
    @GetMapping(value = "/validateAdmin")
    public ResponseEntity<Object> getValidityForAdmin(@RequestHeader("Authorization") final String token) {
        //Returns response after Validating received token
        String token1 = token.substring(7);
        AuthResponse res = new AuthResponse();
        if (Boolean.TRUE.equals(jwtutil.validateToken(token1))) {
            String uid = jwtutil.extractUsername(token1);
            boolean isAdmin = custdetailservice.isAdmin(uid);
            if (isAdmin) {
                res.setUid(uid);
                res.setValid(true);
                res.setName(userservice.findById(jwtutil.extractUsername(token1)).get().getUname());
            } else {
                res.setValid(false);
                LOGGER.info("Admin ");
                LOGGER.error("No Access");
            }

        } else {
            res.setValid(false);
            LOGGER.info("At Validity : ");
            LOGGER.error("Token Has Expired");
        }
        return new ResponseEntity<>(res, HttpStatus.OK);

    }
}
