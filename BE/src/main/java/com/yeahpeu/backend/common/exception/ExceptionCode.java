package com.yeahpeu.backend.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    ALREADY_EXIST_EMAIL("ALREADY_EXIST", "이미 존재하는 이메일입니다."),
    ;

    private final String code;
    private final String message;
}
