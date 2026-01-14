package com.yeahpeu.backend.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    // Member
    ALREADY_EXIST_EMAIL("ALREADY_EXIST", "이미 존재하는 이메일입니다."),
    MEMBER_NOT_FOUND("MEMBER_NOT_FOUND", "회원을 찾을 수 없습니다."),

    // Auth
    INVALID_CREDENTIALS("INVALID_CREDENTIALS", "이메일 또는 비밀번호가 올바르지 않습니다."),
    INVALID_TOKEN("INVALID_TOKEN", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN("EXPIRED_TOKEN", "만료된 토큰입니다."),
    INVALID_REFRESH_TOKEN("INVALID_REFRESH_TOKEN", "유효하지 않은 Refresh Token입니다."),
    EXPIRED_REFRESH_TOKEN("EXPIRED_REFRESH_TOKEN", "만료된 Refresh Token입니다."),
    REFRESH_TOKEN_NOT_FOUND("REFRESH_TOKEN_NOT_FOUND", "Refresh Token을 찾을 수 없습니다."),
    UNAUTHORIZED("UNAUTHORIZED", "인증이 필요합니다."),
    ACCESS_DENIED("ACCESS_DENIED", "접근 권한이 없습니다."),
    ;

    private final String code;
    private final String message;
}
