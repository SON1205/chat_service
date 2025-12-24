package com.yeahpeu.backend.member.service.command;

public record MemberCreateCommand(
        String name,
        String email,
        String password
) {

    public static MemberCreateCommand of(String name, String email, String password) {
        return new MemberCreateCommand(name, email, password);
    }
}
