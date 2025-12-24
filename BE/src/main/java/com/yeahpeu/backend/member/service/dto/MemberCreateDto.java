package com.yeahpeu.backend.member.service.dto;

public record MemberCreateDto(
        Long id,
        String name,
        String email
) {
    public static MemberCreateDto of(Long id, String name, String email) {
        return new MemberCreateDto(id, name, email);
    }
}
