package com.yeahpeu.backend.auth.v2.dto.request;

import com.yeahpeu.backend.auth.v2.domain.DeviceType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "유효한 이메일 형식이어야 합니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수입니다.")
        String password,

        @NotNull(message = "기기 타입은 필수입니다.")
        DeviceType deviceType,

        String deviceInfo
) {
}
