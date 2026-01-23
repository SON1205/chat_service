package com.yeahpeu.backend.auth.v2.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "refreshToken")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RefreshToken {
    @Id
    private String token;

    @Indexed
    private Long memberId;

    @Indexed
    private DeviceType deviceType;

    private String deviceInfo;

    @TimeToLive
    private Long ttl;

    public void updateToken(String newToken, Long newTtl) {
        this.token = newToken;
        this.ttl = newTtl;
    }
}
