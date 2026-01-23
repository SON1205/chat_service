package com.yeahpeu.backend.auth.v2.controller;

import com.yeahpeu.backend.auth.common.userdetails.CustomUserDetails;
import com.yeahpeu.backend.auth.v2.dto.request.LoginRequest;
import com.yeahpeu.backend.auth.v2.dto.request.LogoutRequest;
import com.yeahpeu.backend.auth.v2.dto.request.RefreshRequest;
import com.yeahpeu.backend.auth.v2.dto.response.LoginResponse;
import com.yeahpeu.backend.auth.v2.dto.response.RefreshResponse;
import com.yeahpeu.backend.auth.v2.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody LogoutRequest request) {
        authService.logout(userDetails.getMemberId(), request.deviceType());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logout-all")
    public ResponseEntity<Void> logoutAll(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        authService.logoutAll(userDetails.getMemberId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(@Valid @RequestBody RefreshRequest request) {
        RefreshResponse response = authService.refresh(request);
        return ResponseEntity.ok(response);
    }
}
