package com.magpie.service;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import com.magpie.info.AuthenticationRevocation;

import javax.servlet.http.HttpServletRequest;

@Service
public class TokenAuthenticationService {
	@Autowired
	private TokenStore tokenStore;
	
    public AuthenticationRevocation revokeToken(HttpServletRequest request) {
    	String token = this.fileterToken(request.getHeader("Authorization"));
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
        if (accessToken == null) {
            return new AuthenticationRevocation(false, token);
        }
        if (accessToken.getRefreshToken() != null) {
            tokenStore.removeRefreshToken(accessToken.getRefreshToken());
        }
        tokenStore.removeAccessToken(accessToken);
        return new AuthenticationRevocation(true, token);
    }
    
    private String fileterToken(String token){
    	return Strings.nullToEmpty(token).replace("Bearer ", "");
    }

}
