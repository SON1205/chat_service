package com.yeahpeu.backend.common.exception;

import static com.yeahpeu.backend.common.exception.ExceptionCode.ACCOUNT_EXPIRED;
import static com.yeahpeu.backend.common.exception.ExceptionCode.CREDENTIALS_EXPIRED;
import static com.yeahpeu.backend.common.exception.ExceptionCode.INVALID_CREDENTIALS;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException e) {
        log.warn("인증 실패 - 잘못된 자격 증명: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .code(INVALID_CREDENTIALS.getCode())
                        .message(INVALID_CREDENTIALS.getMessage())
                        .build());
    }

//    @ExceptionHandler(DisabledException.class)
//    public ResponseEntity<ExceptionResponse> handleDisabledException(DisabledException e) {
//        log.warn("인증 실패 - 비활성화된 계정: {}", e.getMessage());
//        return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                .body(ExceptionResponse.builder()
//                        .code(ACCOUNT_DISABLED.getCode())
//                        .message(ACCOUNT_DISABLED.getMessage())
//                        .build());
//    }

//    @ExceptionHandler(LockedException.class)
//    public ResponseEntity<ExceptionResponse> handleLockedException(LockedException e) {
//        log.warn("인증 실패 - 잠긴 계정: {}", e.getMessage());
//        return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                .body(ExceptionResponse.builder()
//                        .code(ACCOUNT_LOCKED.getCode())
//                        .message(ACCOUNT_LOCKED.getMessage())
//                        .build());
//    }

    @ExceptionHandler(AccountExpiredException.class)
    public ResponseEntity<ExceptionResponse> handleAccountExpiredException(AccountExpiredException e) {
        log.warn("인증 실패 - 만료된 계정: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ExceptionResponse.builder()
                        .code(ACCOUNT_EXPIRED.getCode())
                        .message(ACCOUNT_EXPIRED.getMessage())
                        .build());
    }

    @ExceptionHandler(CredentialsExpiredException.class)
    public ResponseEntity<ExceptionResponse> handleCredentialsExpiredException(CredentialsExpiredException e) {
        log.warn("인증 실패 - 만료된 비밀번호: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ExceptionResponse.builder()
                        .code(CREDENTIALS_EXPIRED.getCode())
                        .message(CREDENTIALS_EXPIRED.getMessage())
                        .build());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionResponse> handleAuthenticationException(AuthenticationException e) {
        log.warn("인증 실패: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .code(INVALID_CREDENTIALS.getCode())
                        .message(INVALID_CREDENTIALS.getMessage())
                        .build());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyExistsException(AlreadyExistsException e) {
        log.error("errorMessage: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ExceptionResponse.builder()
                        .code(e.getCode())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionResponse> handleBaseException(BaseException e) {
        log.error("errorMessage: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .code(e.getCode())
                        .message(e.getMessage())
                        .build());
    }
}
