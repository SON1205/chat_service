package com.yeahpeu.backend.auth.v1.dto.response;

import lombok.Builder;

@Builder
public record LoginResponse(
        String accessToken,
        String tokenType,
        Long expiresIn
) {
    public static LoginResponse of(String accessToken, Long expiresIn) {
        return LoginResponse.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresIn(expiresIn)
                .build();
    }
}
