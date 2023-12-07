package com.ems.authservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// Model class for the business details
@Getter
@Setter
@NoArgsConstructor
public class MessageResponse {
    public MessageResponse(String message, LocalDateTime date) {
        super();
        this.message = message;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Response message
     */
    String message;
    /**
     * Response date
     */
    LocalDateTime date;


}
