package com.yeahpeu.backend.auth.v1.service;

import com.yeahpeu.backend.auth.common.domain.AccessTokenBlacklist;
import com.yeahpeu.backend.auth.common.jwt.JwtProvider;
import com.yeahpeu.backend.auth.common.repository.AccessTokenBlacklistRepository;
import com.yeahpeu.backend.auth.common.userdetails.CustomUserDetails;
import com.yeahpeu.backend.auth.v1.dto.request.LoginRequest;
import com.yeahpeu.backend.auth.v1.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final AccessTokenBlacklistRepository accessTokenBlacklistRepository;

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String accessToken = jwtProvider.generateAccessToken(userDetails.getMemberId(), userDetails.getRole());

        log.info("V1 로그인 성공: email={}", userDetails.getUsername());
        return LoginResponse.of(accessToken, jwtProvider.getAccessExpiration());
    }

    @Transactional
    public void logout(String accessToken) {
        if (jwtProvider.validateToken(accessToken)) {
            Long memberId = jwtProvider.getMemberId(accessToken);
            long remainingTtl = jwtProvider.getRemainingExpiration(accessToken);

            accessTokenBlacklistRepository.save(
                    AccessTokenBlacklist.builder()
                            .token(accessToken)
                            .memberId(memberId)
                            .ttl(remainingTtl)
                            .build()
            );

            SecurityContextHolder.clearContext();
            log.info("V1 로그아웃 성공: memberId={}", memberId);
        }
    }
}
