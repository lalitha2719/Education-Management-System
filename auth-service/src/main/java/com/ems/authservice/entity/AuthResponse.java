package com.ems.authservice.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthResponse {
    public AuthResponse(String uid, String name, boolean isValid) {
        super();
        this.uid = uid;
        this.name = name;
        this.isValid = isValid;
    }

    public AuthResponse() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    // Id for user
    private String uid;
    // Name of the user
    private String name;
    // Validity check
    private boolean isValid;

}
