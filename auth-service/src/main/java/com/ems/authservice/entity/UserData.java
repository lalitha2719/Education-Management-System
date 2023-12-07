package com.ems.authservice.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// Model class for the business details
@Entity(name = "user")
@Table
@Data
@ToString
public class UserData {

    // Id for user
    @Id
    @Column(name = "userid", length = 20)
    private String userid;
    // Password for user
    @Column(name = "upassword", length = 20)
    private String upassword;
    /**
     * Name for user
     */
    @Column(name = "uname", length = 20)
    private String uname;

    @Column(name = "admin")
    private boolean admin;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public UserData(String userid, String upassword, String uname, String authToken, boolean admin) {
        super();
        this.userid = userid;
        this.upassword = upassword;
        this.uname = uname;
        this.authToken = authToken;
        this.admin = admin;
    }

    public UserData(String userid, String upassword, String uname, String authToken) {
        super();
        this.userid = userid;
        this.upassword = upassword;
        this.uname = uname;
        this.authToken = authToken;
    }

    public UserData(String userid, String authToken) {
        super();
        this.userid = userid;
        this.authToken = authToken;
    }

    public UserData(String userid, String uname, boolean admin) {
        super();
        this.userid = userid;
        this.uname = uname;
        this.admin = admin;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public UserData() {

    }

    public String getUpassword() {
        return upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    // Generated authentication token for the user
    private String authToken;


}

