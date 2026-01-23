package com.yeahpeu.backend.auth.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "accessTokenBlacklist")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccessTokenBlacklist {
    @Id
    private String token;

    private Long memberId;

    @TimeToLive
    private Long ttl;
}
