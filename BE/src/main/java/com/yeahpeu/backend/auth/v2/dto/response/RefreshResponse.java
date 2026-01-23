package com.yeahpeu.backend.auth.v2.dto.response;

import lombok.Builder;

@Builder
public record RefreshResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        Long expiresIn
) {
    public static RefreshResponse of(String accessToken, String refreshToken, Long expiresIn) {
        return RefreshResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(expiresIn)
                .build();
    }
}
