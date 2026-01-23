package com.yeahpeu.backend.auth.v2.service;

import com.yeahpeu.backend.auth.common.jwt.JwtProvider;
import com.yeahpeu.backend.auth.common.userdetails.CustomUserDetails;
import com.yeahpeu.backend.auth.v2.domain.DeviceType;
import com.yeahpeu.backend.auth.v2.domain.RefreshToken;
import com.yeahpeu.backend.auth.v2.dto.request.LoginRequest;
import com.yeahpeu.backend.auth.v2.dto.request.RefreshRequest;
import com.yeahpeu.backend.auth.v2.dto.response.LoginResponse;
import com.yeahpeu.backend.auth.v2.dto.response.RefreshResponse;
import com.yeahpeu.backend.auth.v2.repository.RefreshTokenRepository;
import com.yeahpeu.backend.common.exception.BaseException;
import com.yeahpeu.backend.common.exception.ExceptionCode;
import com.yeahpeu.backend.member.domain.Member;
import com.yeahpeu.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("authServiceV2")
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String accessToken = jwtProvider.generateAccessToken(userDetails.getMemberId(), userDetails.getRole());
        String refreshToken = jwtProvider.generateRefreshToken(userDetails.getMemberId());

        refreshTokenRepository.findByMemberIdAndDeviceType(userDetails.getMemberId(), request.deviceType())
                .ifPresent(refreshTokenRepository::delete);

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .token(refreshToken)
                        .memberId(userDetails.getMemberId())
                        .deviceType(request.deviceType())
                        .deviceInfo(request.deviceInfo())
                        .ttl(jwtProvider.getRefreshExpiration() / 1000)
                        .build()
        );

        log.info("V2 로그인 성공: email={}, deviceType={}", userDetails.getUsername(), request.deviceType());
        return LoginResponse.of(accessToken, refreshToken, jwtProvider.getAccessExpiration());
    }

    @Transactional
    public void logout(Long memberId, DeviceType deviceType) {
        refreshTokenRepository.deleteByMemberIdAndDeviceType(memberId, deviceType);
        SecurityContextHolder.clearContext();
        log.info("V2 로그아웃 성공: memberId={}, deviceType={}", memberId, deviceType);
    }

    @Transactional
    public void logoutAll(Long memberId) {
        refreshTokenRepository.deleteByMemberId(memberId);
        SecurityContextHolder.clearContext();
        log.info("V2 전체 로그아웃 성공: memberId={}", memberId);
    }

    @Transactional
    public RefreshResponse refresh(RefreshRequest request) {
        String oldRefreshToken = request.refreshToken();

        if (!jwtProvider.validateToken(oldRefreshToken)) {
            throw new BaseException(ExceptionCode.INVALID_REFRESH_TOKEN);
        }

        RefreshToken refreshToken = refreshTokenRepository.findById(oldRefreshToken)
                .orElseThrow(() -> new BaseException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND));

        if (refreshToken.getDeviceType() != request.deviceType()) {
            throw new BaseException(ExceptionCode.INVALID_REFRESH_TOKEN);
        }

        Long memberId = refreshToken.getMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(ExceptionCode.MEMBER_NOT_FOUND));

        String newAccessToken = jwtProvider.generateAccessToken(
                member.getId(),
                member.getRole().name()
        );

        String newRefreshToken = jwtProvider.generateRefreshToken(member.getId());

        refreshTokenRepository.delete(refreshToken);

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .token(newRefreshToken)
                        .memberId(member.getId())
                        .deviceType(refreshToken.getDeviceType())
                        .deviceInfo(refreshToken.getDeviceInfo())
                        .ttl(jwtProvider.getRefreshExpiration() / 1000)
                        .build()
        );

        log.info("V2 토큰 재발급 성공: memberId={}, deviceType={}", memberId, refreshToken.getDeviceType());
        return RefreshResponse.of(newAccessToken, newRefreshToken, jwtProvider.getAccessExpiration());
    }
}
