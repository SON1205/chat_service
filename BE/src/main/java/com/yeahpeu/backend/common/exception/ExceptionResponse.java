package com.yeahpeu.backend.common.exception;

import lombok.Builder;

@Builder
public record ExceptionResponse(
        String code,
        String message
) {
}
