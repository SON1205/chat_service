package com.yeahpeu.backend.member.controller.request;

public record MemberCreateRequest(
        String name,
        String email,
        String password
) {
}
