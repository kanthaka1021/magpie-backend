package com.magpie.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import com.magpie.service.UserAuthenticationService;

@Configuration
public class OAuth2Configuration{
	private static final String RESOURCE_ID = "magpie-mobile-api";
	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			resources.resourceId(RESOURCE_ID);
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.headers().frameOptions().disable();
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			http.authorizeRequests()
			.antMatchers("/**").authenticated();
		}
	}

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
		@Autowired
		@Qualifier("authenticationManagerBean")
		private AuthenticationManager authenticationManager;

		@Autowired
		private UserAuthenticationService userDetailsService;

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints)
				throws Exception {
			endpoints
				.tokenStore(tokenStore())
				.authenticationManager(this.authenticationManager)
				.pathMapping("/oauth/token", "/user-service-oauth/token")
				.userDetailsService(userDetailsService)
				.reuseRefreshTokens(true);
		}
		
	    @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
	    	security.allowFormAuthenticationForClients();
        }
	    
		
		/* 
		 * client에 대한 접근 정보 설정
		 */
		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients
				.inMemory()
				.withClient("magpie-mobile")
						.authorizedGrantTypes("refresh_token", "password")
						.authorities("USER")
						.scopes("read", "write")
						.resourceIds(RESOURCE_ID)
						.secret("magpie-secret")
						.accessTokenValiditySeconds(3600*24*90)
						.refreshTokenValiditySeconds(3600*24*180);
		}

		@Bean
		@Primary
		public DefaultTokenServices tokenServices() {
			DefaultTokenServices tokenServices = new DefaultTokenServices();
			tokenServices.setSupportRefreshToken(true);
			tokenServices.setTokenStore(tokenStore());
			return tokenServices;
		}

		@Bean
		@Primary
		public TokenStore tokenStore() { 
			return new RedisTokenStore(jedisConnectionFactory()); 
		}
		
	    @Value("${redis.host}")
	    private String redisHostName;

	    @Value("${redis.port}")
	    private int redisPort;
	    
	    @Value("${redis.index}")
	    private int index;	    
	    
	    @Bean
	    public JedisConnectionFactory jedisConnectionFactory() {
	        JedisConnectionFactory factory = new JedisConnectionFactory();
	        factory.setHostName(redisHostName);
	        factory.setPort(redisPort);
	        factory.setDatabase(index);
	        factory.setUsePool(true);
	        return factory;	    	
	    }				
	}
}
