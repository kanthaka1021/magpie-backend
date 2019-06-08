/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.magpie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.magpie.external.ExternalUserService;
import java.util.*;


@Service
public class UserAuthenticationService implements UserDetailsService {
	
	@Autowired
	@Qualifier("ExternalUserServiceImpl")
	private ExternalUserService externalService;
	
	public static final String ROLE_ADMIN = "ADMIN";
	
	public static final String ROLE_USER = "USER";

	static class SimpleUserDetails implements UserDetails {
		
		private String username;
		private String password;
		private boolean enabled = true;
		
		private Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

		public SimpleUserDetails(String username, String pw, String... extraRoles) {
			this.username = username;
			this.password = pw;
			// 롤 지정
			Set<String> roles = new HashSet<String>();
			roles.addAll(Arrays.<String>asList(null == extraRoles ? new String[0] : extraRoles));
			for (String r : roles) {
				authorities.add(new SimpleGrantedAuthority(role(r)));
			}
		}

		public String toString() {
			return "{enabled:" + isEnabled() + ", username:'" + getUsername() + "', password:'" + getPassword() + "'}";
		}

		@Override
		public boolean isEnabled() {
			return this.enabled;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return this.enabled;
		}

		@Override
		public boolean isAccountNonLocked() {
			return this.enabled;
		}

		@Override
		public boolean isAccountNonExpired() {
			return this.enabled;
		}

		@Override
		public String getUsername() {
			return this.username;
		}

		@Override
		public String getPassword() {
			return this.password;
		}

		private String role(String i) {
			return "ROLE_" + i;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return this.authorities;
		}
	}

	@Override
	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
		return this.getUserDetailAccount(account);
	}
	
	private SimpleUserDetails getUserDetailAccount(String account){
		Optional<String> password = this.externalService.findPasswordByAccount(account);
		if(password.isPresent()) {
			return new SimpleUserDetails(account, password.get(), ROLE_USER);
		}
		throw new UsernameNotFoundException("No user found for username " + account);
	}
}
