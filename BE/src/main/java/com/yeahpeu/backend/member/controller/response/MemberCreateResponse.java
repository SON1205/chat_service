package com.yeahpeu.backend.member.controller.response;

public record MemberCreateResponse(
        Long id,
        String name,
        String email
) {
    public static MemberCreateResponse of(Long id, String name, String email) {
        return new MemberCreateResponse(id, name, email);
    }
}
