package com.yeahpeu.backend.common.auth.filter;

import com.yeahpeu.backend.common.auth.jwt.JwtProvider;
import com.yeahpeu.backend.common.auth.userdetails.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JWT 토큰 기반 인증을 처리하는 필터 (요청 인증용) Authorization 헤더의 Bearer 토큰을 검증하여 SecurityContext에 인증 정보를 저장
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // 1. 토큰 추출
            String token = extractToken(request);

            // 2. 토큰 검증 및 인증 처리
            if (token != null && jwtProvider.validateToken(token)) {
                // 3. 토큰에서 사용자 ID 추출
                Long memberId = jwtProvider.getMemberId(token);

                // 4. UserDetails 조회
                UserDetails userDetails = userDetailsService.loadUserById(memberId);

                // 5. Authentication 생성
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. SecurityContext에 저장
                // 그래야 controller에서 사용자 정보 접근 가능
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.debug("JWT 인증 성공: memberId={}, uri={}", memberId, request.getRequestURI());
            }
        } catch (UsernameNotFoundException e) {
            log.warn("존재하지 않는 사용자: uri={}, error={}", request.getRequestURI(), e.getMessage());
            SecurityContextHolder.clearContext();
        } catch (Exception e) {
            log.error("JWT 처리 중 예외: uri={}, error={}", request.getRequestURI(), e.getMessage());
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
