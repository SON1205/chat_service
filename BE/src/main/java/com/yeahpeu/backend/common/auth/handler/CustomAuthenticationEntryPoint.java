package com.yeahpeu.backend.common.auth.handler;

import static com.yeahpeu.backend.common.exception.ExceptionCode.UNAUTHORIZED;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * 인증되지 않은 사용자가 보호된 리소스에 접근할 때 호출되는 핸들러 (401) 정의를 하지 않으면 spring의 기본 오류페이지를 보여줌
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.warn("인증 실패 - URI: {}, Message: {}", request.getRequestURI(), authException.getMessage());
        sendErrorResponse(request, response);
    }

    private void sendErrorResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setResponseHeader(response);
        writeResponseBody(request, response);
    }

    private void setResponseHeader(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
    }

    private void writeResponseBody(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", UNAUTHORIZED.getCode());
        errorResponse.put("message", UNAUTHORIZED.getMessage());
        errorResponse.put("path", request.getRequestURI());

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
