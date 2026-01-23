package com.yeahpeu.backend.auth.v2.dto.request;

import com.yeahpeu.backend.auth.v2.domain.DeviceType;
import jakarta.validation.constraints.NotNull;

public record LogoutRequest(
        @NotNull(message = "기기 타입은 필수입니다.")
        DeviceType deviceType
) {
}
