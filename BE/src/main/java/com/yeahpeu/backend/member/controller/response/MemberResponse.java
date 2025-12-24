package com.yeahpeu.backend.member.controller.response;

import com.yeahpeu.backend.member.service.dto.MemberDto;

public record MemberResponse(
        Long id,
        String name,
        String email
) {
    public static MemberResponse from(MemberDto memberDto) {
        return new MemberResponse(memberDto.id(), memberDto.name(), memberDto.email());
    }
}
