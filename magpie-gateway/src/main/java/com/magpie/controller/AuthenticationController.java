package com.magpie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.magpie.service.TokenAuthenticationService;
import com.magpie.info.AuthenticationRevocation;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user-service-oauth")
@CrossOrigin
public class AuthenticationController {
	@Autowired
	private TokenAuthenticationService service;
	
    @RequestMapping(value = "/revoke",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    public @ResponseBody
    AuthenticationRevocation revoke(HttpServletRequest request) {
        return this.service.revokeToken(request);
    }

}
