package com.ems.entity;

public class SuccessResponse
{
    private String responseMessage;
    
    public String getResponseMessage() {
        return this.responseMessage;
    }
    
    public void setResponseMessage(final String responseMessage) {
        this.responseMessage = responseMessage;
    }
    
    public SuccessResponse() {
    }
    
    public SuccessResponse(final String responseMessage) {
        this.responseMessage = responseMessage;
    }
}