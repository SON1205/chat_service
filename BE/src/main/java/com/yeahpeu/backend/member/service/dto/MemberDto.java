package com.yeahpeu.backend.member.service.dto;

import com.yeahpeu.backend.member.domain.Member;

public record MemberDto(
        Long id,
        String name,
        String email
) {
    public static MemberDto from(Member member) {
        return new MemberDto(member.getId(), member.getName(), member.getEmail());
    }
}
