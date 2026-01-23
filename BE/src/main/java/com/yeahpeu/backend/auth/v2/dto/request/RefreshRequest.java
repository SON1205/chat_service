package com.yeahpeu.backend.auth.v2.dto.request;

import com.yeahpeu.backend.auth.v2.domain.DeviceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RefreshRequest(
        @NotBlank(message = "Refresh Token은 필수입니다.")
        String refreshToken,

        @NotNull(message = "기기 타입은 필수입니다.")
        DeviceType deviceType
) {
}
