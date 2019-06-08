package com.magpie.external;

import java.util.Optional;

public interface ExternalUserService {
	public Optional<String> findPasswordByAccount(String email);
}
