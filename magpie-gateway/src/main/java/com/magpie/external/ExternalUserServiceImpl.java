package com.magpie.external;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@Component("ExternalUserServiceImpl")
public class ExternalUserServiceImpl implements ExternalUserService {
	private static final Logger log = LoggerFactory.getLogger(ExternalUserServiceImpl.class);
	
	@Value("${external.auth.uri}")
	private String innerAuthUri;
	
    @Autowired
    private RestTemplate restTemplate;
    
	@Override
	public Optional<String> findPasswordByAccount(String account) {
		try {
			ResponseEntity<String>  res = restTemplate.exchange(innerAuthUri, HttpMethod.GET, null, String.class, account);
			return Optional.of(res.getBody());
		} catch (Exception e) {
			log.error("Fail authentication - " + ExceptionUtils.getFullStackTrace(e));
			return Optional.empty();
		}
	}
}
