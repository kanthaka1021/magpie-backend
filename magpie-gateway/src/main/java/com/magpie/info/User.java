package com.magpie.info;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {

	private Long userId;

	private String email;

	private String name;

	private String role;

	private Long groupId;

	private String password;

	private String phoneNum;

	private String availDirection;

	private String availDomain;
	
}
