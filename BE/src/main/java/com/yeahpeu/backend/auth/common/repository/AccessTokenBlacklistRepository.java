package com.yeahpeu.backend.auth.common.repository;

import com.yeahpeu.backend.auth.common.domain.AccessTokenBlacklist;
import org.springframework.data.repository.CrudRepository;

public interface AccessTokenBlacklistRepository extends CrudRepository<AccessTokenBlacklist, String> {
}
