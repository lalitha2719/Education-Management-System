package com.ems.restclients;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ems.entity.TokenValid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "AUTH-SERVICE", path = "/authapp")
public interface AuthFeign
{
    @RequestMapping(value = { "/validateAdmin" }, method = { RequestMethod.GET })
    ResponseEntity<TokenValid> getValidityForAdmin(@RequestHeader("Authorization") final String token);

    @GetMapping("/validateStudent")
    ResponseEntity<TokenValid> getValidityForStudent(@RequestHeader("Authorization") final String token);
}